package co.edu.javeriana.products.infraestructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface Repositories<T> {

    Optional<List<T>> findByAll();
    Optional<T> findById(String id);
    Optional<List<T>> findByText(String text);
    CompletableFuture<String> create(T data);
    CompletableFuture<String> update(T data);
    CompletableFuture<String> delete(T data);

}
