package co.edu.javeriana.products.application;

import co.edu.javeriana.products.domain.Product;

import java.util.concurrent.CompletableFuture;

public interface ProductCommandService {

    CompletableFuture<String> createProduct(Product product);
    //String createProduct(ProductDto productDto);

}
