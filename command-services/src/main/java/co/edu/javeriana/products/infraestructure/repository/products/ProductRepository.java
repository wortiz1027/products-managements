package co.edu.javeriana.products.infraestructure.repository.products;

import co.edu.javeriana.products.domain.Product;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ProductRepository {

    Optional<Product> findById(String id);
    CompletableFuture<String> create(Product product);
    CompletableFuture<String> update(Product product);
    CompletableFuture<String> delete(Product product);

}