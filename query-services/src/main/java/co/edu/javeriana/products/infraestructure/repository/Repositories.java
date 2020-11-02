package co.edu.javeriana.products.infraestructure.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface Repositories<T> {

    Optional<Page<T>> findByAll(Pageable paging);
    Optional<T> findById(String id);
    Optional<T> findByCode(String code);
    Optional<Page<T>> findByText(String text, Pageable paging);
    CompletableFuture<String> create(T data);
    CompletableFuture<String> update(T data);
    CompletableFuture<String> delete(T data);

}
