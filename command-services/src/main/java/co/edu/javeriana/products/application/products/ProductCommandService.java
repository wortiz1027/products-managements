package co.edu.javeriana.products.application.products;

import co.edu.javeriana.products.domain.Product;
import co.edu.javeriana.products.domain.Response;

import java.util.concurrent.CompletableFuture;

public interface ProductCommandService {

    CompletableFuture<Response> createProduct(Product product);
    CompletableFuture<Response> updateProduct(Product product);
    CompletableFuture<Response> deleteProduct(Product product);

}
