package co.edu.javeriana.products.infraestructure.controllers.types;

import co.edu.javeriana.products.application.types.ProductTypeCommandService;
import co.edu.javeriana.products.domain.ProductType;
import co.edu.javeriana.products.domain.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductTypeAddCommandController {

    private final ProductTypeCommandService service;

    @PostMapping("/products/types")
    public CompletableFuture<Response> handle(@RequestBody ProductType data) {
        return service.createProductType(data);
    }

}
