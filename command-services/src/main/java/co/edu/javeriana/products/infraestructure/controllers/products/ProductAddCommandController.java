package co.edu.javeriana.products.infraestructure.controllers.products;

import co.edu.javeriana.products.domain.Product;
import co.edu.javeriana.products.application.products.ProductCommandService;
import co.edu.javeriana.products.domain.ProductType;
import co.edu.javeriana.products.domain.Response;
import co.edu.javeriana.products.domain.Status;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductAddCommandController {

    private final ProductCommandService service;

    @PostMapping("/products")
    public ResponseEntity<CompletableFuture<Response>> handle(@RequestBody Product data) throws ExecutionException, InterruptedException {

        if (data == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        CompletableFuture<Response> rs = service.createProduct(data);

        if (rs.get().getStatus().equalsIgnoreCase(Status.CREATED.name()))
            return new ResponseEntity<>(rs, HttpStatus.CREATED);

        if (rs.get().getStatus().equalsIgnoreCase(Status.ERROR.name()))
            return new ResponseEntity<>(rs, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(rs, HttpStatus.CONFLICT);
    }

}
