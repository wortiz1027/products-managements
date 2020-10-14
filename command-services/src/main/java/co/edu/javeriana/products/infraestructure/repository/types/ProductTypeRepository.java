package co.edu.javeriana.products.infraestructure.repository.types;

import co.edu.javeriana.products.domain.ProductType;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ProductTypeRepository {

    Optional<ProductType> findById(String id);
    CompletableFuture<String> create(ProductType type);
    CompletableFuture<String> update(ProductType type);
    CompletableFuture<String> delete(ProductType type);

}
