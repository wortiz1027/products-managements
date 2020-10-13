package co.edu.javeriana.products.infraestructure.repository;

import co.edu.javeriana.products.domain.Product;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ProductTypeRepository implements ProductRepository {

    @Override
    public Optional<Product> findById(String id) {
        return Optional.empty();
    }

    @Override
    public CompletableFuture<String> create(Product product) {
        return null;
    }

    @Override
    public CompletableFuture<String> update(Product product) {
        return null;
    }

    @Override
    public CompletableFuture<String> delete(Product product) {
        return null;
    }

}
