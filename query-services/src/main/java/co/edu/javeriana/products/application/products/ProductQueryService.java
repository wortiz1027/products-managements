package co.edu.javeriana.products.application.products;

import co.edu.javeriana.products.application.dtos.products.ResponseProduct;
import co.edu.javeriana.products.application.dtos.products.Response;

import java.util.concurrent.CompletableFuture;

public interface ProductQueryService {

    CompletableFuture<Response> getAllProducts();
    CompletableFuture<ResponseProduct> getProductsByText(String text);

}
