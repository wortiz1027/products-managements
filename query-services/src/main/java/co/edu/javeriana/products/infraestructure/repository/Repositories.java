package co.edu.javeriana.products.infraestructure.repository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface Repositories<T> {

    Optional<T> findById(String id);
    CompletableFuture<String> create(T data);
    CompletableFuture<String> update(T data);
    CompletableFuture<String> delete(T data);

}
