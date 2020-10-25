package co.edu.javeriana.products.application.products;

import co.edu.javeriana.products.application.dtos.products.ResponseProduct;
import co.edu.javeriana.products.application.dtos.products.Response;
import co.edu.javeriana.products.application.dtos.products.ResponseProductDetail;
import co.edu.javeriana.products.domain.Product;
import co.edu.javeriana.products.domain.Status;
import co.edu.javeriana.products.events.dtos.Image;
import co.edu.javeriana.products.events.dtos.Request;
import co.edu.javeriana.products.infraestructure.repository.Repositories;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductQueryServiceImpl.class);

    @Value("${events.rpc.exchange}")
    private String rpcExchange;

    @Value("${events.rpc.routing-key}")
    private String rpcRoutingKey;

    private final AsyncRabbitTemplate template;
    private final Repositories<Product> repository;

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

            Map<String, Object> rs = new HashMap<>();
            rs.put("products", products.get().getContent());
            rs.put("currentPage", products.get().getNumber());
            rs.put("totalItems", products.get().getTotalElements());
            rs.put("totalPages", products.get().getTotalPages());

            status.setCode(Status.SUCCESS.name());
            status.setDescription("There are rows availables");
            response.setStatus(status);
            response.setProducts(prd);

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

            Map<String, Object> rs = new HashMap<>();
            rs.put("products", products.get().getContent());
            rs.put("currentPage", products.get().getNumber());
            rs.put("totalItems", products.get().getTotalElements());
            rs.put("totalPages", products.get().getTotalPages());

            status.setCode(Status.SUCCESS.name());
            status.setDescription("There are rows availables");
            response.setStatus(status);
            response.setProduct(prd);

            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
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
            Optional<Product> product = this.repository.findById(code);

            if (!product.isPresent()) {
                status.setCode(Status.EMPTY.name());
                status.setDescription(String.format("There is not information for product with code: %s", code));
                response.setStatus(status);
                return CompletableFuture.completedFuture(response);
            }

            List<Product> products = new ArrayList<>();
            products.add(product.get());

            callImages(Optional.of(products));

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

        LOG.info(ids.substring(0, ids.length() - 1));

        Request request = new Request();
        request.setIds(ids.substring(0, ids.length() - 1));

        AsyncRabbitTemplate.RabbitConverterFuture<co.edu.javeriana.products.events.dtos.Response> future =
                this.template.convertSendAndReceiveAsType(
                        rpcExchange,
                        rpcRoutingKey,
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

 }