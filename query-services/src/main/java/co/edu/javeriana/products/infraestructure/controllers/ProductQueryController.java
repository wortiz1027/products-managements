package co.edu.javeriana.products.infraestructure.controllers;

import co.edu.javeriana.products.application.dtos.products.Response;
import co.edu.javeriana.products.application.dtos.products.ResponseProduct;
import co.edu.javeriana.products.application.products.ProductQueryService;

import co.edu.javeriana.products.domain.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductQueryController {

    private final ProductQueryService service;

    @GetMapping("/products")
    public ResponseEntity<CompletableFuture<Response>> all() throws ExecutionException, InterruptedException {
        CompletableFuture<Response> rs = service.getAllProducts();

        if (rs.get().getStatus().getCode().equalsIgnoreCase(Status.SUCCESS.name()))
            return new ResponseEntity<>(rs, HttpStatus.OK);

        if (rs.get().getStatus().getCode().equalsIgnoreCase(Status.EMPTY.name()))
            return new ResponseEntity<>(rs, HttpStatus.NOT_FOUND);

        if (rs.get().getStatus().getCode().equalsIgnoreCase(Status.ERROR.name()))
            return new ResponseEntity<>(rs, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(rs, HttpStatus.ACCEPTED);
    }

    @PostMapping("/products/text")
    public ResponseEntity<CompletableFuture<ResponseProduct>> text(@RequestParam(name = "text") String text) throws ExecutionException, InterruptedException {
        CompletableFuture<ResponseProduct> rs = service.getProductsByText(text);

        if (rs.get().getStatus().getCode().equalsIgnoreCase(Status.SUCCESS.name()))
            return new ResponseEntity<>(rs, HttpStatus.OK);

        if (rs.get().getStatus().getCode().equalsIgnoreCase(Status.EMPTY.name()))
            return new ResponseEntity<>(rs, HttpStatus.NOT_FOUND);

        if (rs.get().getStatus().getCode().equalsIgnoreCase(Status.ERROR.name()))
            return new ResponseEntity<>(rs, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(rs, HttpStatus.ACCEPTED);
    }

}
