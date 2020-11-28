package co.edu.javeriana.products.application.products;

import co.edu.javeriana.products.application.dtos.products.ResponseProduct;
import co.edu.javeriana.products.application.dtos.products.Response;
import co.edu.javeriana.products.application.dtos.products.ResponseProductDetail;
import co.edu.javeriana.products.application.dtos.products.ResponseVendors;
import co.edu.javeriana.products.domain.Product;
import co.edu.javeriana.products.domain.Status;
import co.edu.javeriana.products.domain.Vendor;
import co.edu.javeriana.products.domain.VendorTypes;
import co.edu.javeriana.products.events.dtos.Image;
import co.edu.javeriana.products.events.dtos.Request;
import co.edu.javeriana.products.infraestructure.repository.Repositories;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductQueryServiceImpl.class);

    @Value("${events.rpc.images.exchange}")
    private String rpcImagesExchange;

    @Value("${events.rpc.images.routing-key}")
    private String rpcImagesRoutingKey;

    @Value("${rest.vendors.uri}")
    private String vendorUri;

    @Value("${rest.vendors.services-id}")
    private String servicesId;

    private final AsyncRabbitTemplate template;
    private final Repositories<Product> repository;

    private final DiscoveryClient discoveryClient;

    private final RestTemplate restTemplate;

    @Override
    public CompletableFuture<co.edu.javeriana.products.application.dtos.rpc.products.Response> getByProductById(List<String> ids) {
        co.edu.javeriana.products.application.dtos.rpc.products.Response response = new co.edu.javeriana.products.application.dtos.rpc.products.Response();
        co.edu.javeriana.products.application.dtos.Status status = new co.edu.javeriana.products.application.dtos.Status();
        try {
            List<Product> products = new ArrayList<>();
            for (String id : ids) {
                Optional<Product> product = this.repository.findById(id);
                products.add(product.get());
            }

            if (products.isEmpty()) {
                status.setCode(Status.EMPTY.name());
                status.setDescription("There is not information for products");
                response.setStatus(status);
                return CompletableFuture.completedFuture(response);
            }

            callImages(Optional.of(products));
            getVendors(Optional.of(products));

            status.setCode(Status.SUCCESS.name());
            status.setDescription("There is informations for products");
            response.setStatus(status);
            response.setProducts(products);

            return CompletableFuture.completedFuture(response);
        } catch(Exception e) {
            status.setCode(Status.ERROR.name());
            status.setDescription(String.format("There is an error getting products informations: %s", e.getMessage()));
            response.setStatus(status);
            return CompletableFuture.completedFuture(response);
        }
    }

    @Override
    public CompletableFuture<Response> getAllProducts(Pageable paging) {
        Response response = new Response();
        co.edu.javeriana.products.application.dtos.Status status = new co.edu.javeriana.products.application.dtos.Status();
        try {
            Optional<Page<Product>> products = this.repository.findByAll(paging);

            if (!products.isPresent()) {
                status.setCode(Status.EMPTY.name());
                status.setDescription("There are not rows availables");
                response.setStatus(status);
                return CompletableFuture.completedFuture(response);
            }

            List<Product> prd = products.get().getContent();

            callImages(Optional.of(prd));
            getVendors(Optional.of(prd));

            Map<String, Object> data = new HashMap<>();
            data.put("products", prd);
            data.put("currentPage", products.get().getNumber());
            data.put("totalItems", products.get().getTotalElements());
            data.put("totalPages", products.get().getTotalPages());

            status.setCode(Status.SUCCESS.name());
            status.setDescription("There are rows availables");
            response.setStatus(status);
            response.setData(data);

            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            status.setCode(Status.ERROR.name());
            status.setDescription(String.format("There is an error getting products type: %s", e.getMessage()));
            response.setStatus(status);
            return CompletableFuture.completedFuture(response);
        }
    }

    @Override
    public CompletableFuture<ResponseProduct> getProductsByText(String text, Pageable paging) {
        ResponseProduct response = new ResponseProduct();
        co.edu.javeriana.products.application.dtos.Status status = new co.edu.javeriana.products.application.dtos.Status();
        try {
            Optional<Page<Product>> products = this.repository.findByText(text, paging);

            if (!products.isPresent()) {
                status.setCode(Status.EMPTY.name());
                status.setDescription("There are not rows availables");
                response.setStatus(status);
                return CompletableFuture.completedFuture(response);
            }

            List<Product> prd = products.get().getContent();

            callImages(Optional.of(prd));
            getVendors(Optional.of(prd));

            Map<String, Object> data = new HashMap<>();
            data.put("products", prd);
            data.put("currentPage", products.get().getNumber());
            data.put("totalItems", products.get().getTotalElements());
            data.put("totalPages", products.get().getTotalPages());

            status.setCode(Status.SUCCESS.name());
            status.setDescription("There are rows availables");
            response.setStatus(status);
            response.setData(data);

            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            e.printStackTrace();
            status.setCode(co.edu.javeriana.products.domain.Status.ERROR.name());
            status.setDescription(String.format("Error getting rows: %s", e.getMessage()));
            response.setStatus(status);

            return CompletableFuture.completedFuture(response);
        }
    }

    @Override
    public CompletableFuture<ResponseProductDetail> getProductsDetail(String code) {
        ResponseProductDetail response = new ResponseProductDetail();
        co.edu.javeriana.products.application.dtos.Status status = new co.edu.javeriana.products.application.dtos.Status();
        try {
            Optional<Product> product = this.repository.findByCode(code);

            if (!product.isPresent()) {
                status.setCode(Status.EMPTY.name());
                status.setDescription(String.format("There is not information for product with code: %s", code));
                response.setStatus(status);
                return CompletableFuture.completedFuture(response);
            }

            List<Product> products = new ArrayList<>();
            products.add(product.get());

            callImages(Optional.of(products));
            getVendors(Optional.of(products));

            status.setCode(Status.SUCCESS.name());
            status.setDescription(String.format("There is informations for product with code: %s", code));
            response.setStatus(status);
            response.setProduct(product.get());

            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            status.setCode(co.edu.javeriana.products.domain.Status.ERROR.name());
            status.setDescription(String.format("Error getting rows: %s", e.getMessage()));
            response.setStatus(status);

            return CompletableFuture.completedFuture(response);
        }
    }

    private void callImages(Optional<List<Product>> products) {

        String ids = "";

        for (Product row : products.get()) {
            ids = ids.concat(row.getImage().getId()).concat(",");
        }
        LOG.info("ID_PRODUCTS: {}", ids);
        LOG.info(ids.substring(0, ids.length() - 1));

        Request request = new Request();
        request.setIds(ids.substring(0, ids.length() - 1));

        AsyncRabbitTemplate.RabbitConverterFuture<co.edu.javeriana.products.events.dtos.Response> future =
                this.template.convertSendAndReceiveAsType(
                        rpcImagesExchange,
                        rpcImagesRoutingKey,
                        request,
                        new ParameterizedTypeReference<>() {});

        try {
            co.edu.javeriana.products.events.dtos.Response res = future.get();
            LOG.info("Message received: {}", res);

            for (Product row : products.get()) {
                for (Image i : res.getImages()) {
                    if (row.getImage().getId().equalsIgnoreCase(i.getId())) {
                        row.getImage().setUrl(i.getUrl());
                    }
                }
            }

        } catch (InterruptedException | ExecutionException e) {
            LOG.error("Cannot get response.", e);
        }
    }

    private void getVendors(Optional<List<Product>> products) {
        List<ServiceInstance> instances = discoveryClient.getInstances(servicesId);

        ServiceInstance serviceInstance = instances.get(0);

        String url = serviceInstance.getUri().toString();

        url = url + vendorUri;

        String request = "";

        for (Product row : products.get()) {
            request = request.concat(row.getVendor().getIdProvider()).concat(",");
        }

        ResponseEntity<ResponseVendors> response = null;

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("ids", request)
                .build();

        try{
            response = restTemplate.exchange(builder.toUriString(),
                                             HttpMethod.POST,
                                             getHeaders(),
                                             ResponseVendors.class);
        }catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }

        for (Product row : products.get()) {
            for (Vendor vendor : response.getBody().getVendors()) {
                if (row.getVendor().getIdProvider().equalsIgnoreCase(vendor.getIdProvider())) {
                    row.setVendor(vendor);
                }
            }
        }

        System.out.println(response.getBody());
    }

    private static HttpEntity<?> getHeaders() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }

 }