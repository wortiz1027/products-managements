package co.edu.javeriana.products.application.products;

import co.edu.javeriana.products.application.dtos.products.ResponseProduct;
import co.edu.javeriana.products.application.dtos.products.Response;
import co.edu.javeriana.products.domain.Product;
import co.edu.javeriana.products.domain.Status;
import co.edu.javeriana.products.events.dtos.Image;
import co.edu.javeriana.products.events.dtos.Request;
import co.edu.javeriana.products.infraestructure.repository.Repositories;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    public CompletableFuture<Response> getAllProducts() {
        Response response = new Response();
        co.edu.javeriana.products.application.dtos.Status status = new co.edu.javeriana.products.application.dtos.Status();
        try {
            Optional<List<Product>> products = this.repository.findByAll();
            if (!products.isPresent()) {
                status.setCode(Status.EMPTY.name());
                status.setDescription("There are not rows availables");
                response.setStatus(status);
                return CompletableFuture.completedFuture(response);
            }

            callImages(products);

            status.setCode(Status.SUCCESS.name());
            status.setDescription("There are rows availables");
            response.setStatus(status);
            response.setProducts(products.get());
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            status.setCode(Status.ERROR.name());
            status.setDescription(String.format("There is an error getting products type: %s", e.getMessage()));
            response.setStatus(status);
            return CompletableFuture.completedFuture(response);
        }
    }

    @Override
    public CompletableFuture<ResponseProduct> getProductsByText(String text) {
        ResponseProduct response = new ResponseProduct();
        co.edu.javeriana.products.application.dtos.Status status = new co.edu.javeriana.products.application.dtos.Status();
        try {
            Optional<List<Product>> products = this.repository.findByText(text);

            if (!products.isPresent()) {
                status.setCode(Status.EMPTY.name());
                status.setDescription("There are not rows availables");
                response.setStatus(status);
                return CompletableFuture.completedFuture(response);
            }

            callImages(products);

            status.setCode(Status.SUCCESS.name());
            status.setDescription("There are rows availables");
            response.setStatus(status);
            response.setProduct(products.get());

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