package co.edu.javeriana.products.infraestructure.controllers;

import co.edu.javeriana.products.domain.Product;
import co.edu.javeriana.products.application.ProductCommandService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductAddCommandController {

    private final ProductCommandService service;

    @PostMapping("/products")
    public CompletableFuture<String> handle(@RequestBody Product data) {
        return service.createProduct(data);
    }

}
