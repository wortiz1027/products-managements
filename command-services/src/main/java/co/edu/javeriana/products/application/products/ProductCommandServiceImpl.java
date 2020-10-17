package co.edu.javeriana.products.application.products;

import co.edu.javeriana.products.domain.Product;

import co.edu.javeriana.products.domain.Response;
import co.edu.javeriana.products.domain.Status;
import co.edu.javeriana.products.infraestructure.repository.products.ProductRepository;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ProductCommandServiceImpl implements ProductCommandService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductCommandServiceImpl.class);

    @Value("${events.amqp.exchange}")
    String productExchange;

    @Value("${events.amqp.routing-key}")
    String productRoutingKey;

    private final ProductRepository repository;
    private final AmqpTemplate template;

    @Override
    public CompletableFuture<Response> createProduct(Product product){
        Response response = new Response();
        try {
            String status = this.repository.create(product).get();
            response.setStatus(status);

            if (!status.equalsIgnoreCase(Status.CREATED.name())) {
                response.setDescription(String.format("The product with id: {%s} has an error", product.getProductCode()));
                return CompletableFuture.completedFuture(response);
            }

            product.setStatus(Status.CREATED.name());
            this.template.convertAndSend(productExchange, productRoutingKey, product);
            response.setDescription(String.format("The product with id: {%s} has been created", product.getProductCode()));
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            response.setStatus(Status.ERROR.name());
            response.setDescription(String.format("Exception creating row has been release: {%s}", e.getMessage()));
            return CompletableFuture.completedFuture(response);
        }
    }

    @Override
    public CompletableFuture<Response> updateProduct(Product product) {
        Response response = new Response();
        try {
            String status = this.repository.update(product).get();
            response.setStatus(status);

            if (!status.equalsIgnoreCase(Status.UPDATED.name())) {
                response.setDescription(String.format("The product with id: {%s} has an error", product.getProductCode()));
                return CompletableFuture.completedFuture(response);
            }

            response.setDescription(String.format("The product with id: {%s} has been updated", product.getProductCode()));
            product.setStatus(Status.UPDATED.name());
            this.template.convertAndSend(productExchange, productRoutingKey, product);

            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            response.setStatus(Status.ERROR.name());
            response.setDescription(String.format("Exception updating row has been release: {%s}", e.getMessage()));
            return CompletableFuture.completedFuture(response);
        }
    }

    @Override
    public CompletableFuture<Response> deleteProduct(Product product) {
        Response response = new Response();
        try {
            String status = this.repository.delete(product).get();
            response.setStatus(status);

            if (!status.equalsIgnoreCase(Status.DELETED.name())) {
                response.setDescription(String.format("The product with id: {%s} has an error", product.getProductCode()));
                return CompletableFuture.completedFuture(response);
            }

            response.setDescription(String.format("The product with id: {%s} has been deleted", product.getProductCode()));
            product.setStatus(Status.DELETED.name());
            this.template.convertAndSend(productExchange, productRoutingKey, product);

            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            response.setStatus(Status.ERROR.name());
            response.setDescription(String.format("Exception deleting row has been release: {%s}", e.getMessage()));
            return CompletableFuture.completedFuture(response);
        }
    }
}
