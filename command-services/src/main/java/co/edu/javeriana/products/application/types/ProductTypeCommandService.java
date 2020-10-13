package co.edu.javeriana.products.application.types;

import co.edu.javeriana.products.domain.ProductType;
import co.edu.javeriana.products.domain.Response;

import java.util.concurrent.CompletableFuture;

public interface ProductTypeCommandService {

    CompletableFuture<Response> createProductType(ProductType product);
    CompletableFuture<Response> updateProductType(ProductType product);
    CompletableFuture<Response> deleteProductType(ProductType product);

}
