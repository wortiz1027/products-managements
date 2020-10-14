package co.edu.javeriana.products.application.types;

import co.edu.javeriana.products.domain.ProductType;
import co.edu.javeriana.products.domain.Response;

import java.util.concurrent.CompletableFuture;

public interface ProductTypeCommandService {

    CompletableFuture<Response> createProductType(ProductType type);
    CompletableFuture<Response> updateProductType(ProductType type);
    CompletableFuture<Response> deleteProductType(ProductType type);

}
