package co.edu.javeriana.products.infraestructure.controllers.types;

import co.edu.javeriana.products.application.types.ProductTypeCommandService;
import co.edu.javeriana.products.domain.ProductType;
import co.edu.javeriana.products.domain.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductTypeUpdateCommandController {

    private final ProductTypeCommandService service;

    @PutMapping("/products/types")
    public CompletableFuture<Response> handle(@RequestBody ProductType data) {
        return service.createProductType(data);
    }

}
