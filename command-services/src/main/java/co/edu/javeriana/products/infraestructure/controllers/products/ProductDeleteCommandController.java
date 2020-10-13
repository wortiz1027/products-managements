package co.edu.javeriana.products.infraestructure.controllers.products;

import co.edu.javeriana.products.application.products.ProductCommandService;
import co.edu.javeriana.products.domain.Product;
import co.edu.javeriana.products.domain.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductDeleteCommandController {

    private final ProductCommandService service;

    @DeleteMapping("/products")
    public CompletableFuture<Response> handle(@RequestBody Product data) {
        return service.deleteProduct(data);
    }

}
