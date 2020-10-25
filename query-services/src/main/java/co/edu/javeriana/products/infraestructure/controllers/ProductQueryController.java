package co.edu.javeriana.products.infraestructure.controllers;

import co.edu.javeriana.products.application.dtos.products.Response;
import co.edu.javeriana.products.application.dtos.products.ResponseProduct;
import co.edu.javeriana.products.application.dtos.products.ResponseProductDetail;
import co.edu.javeriana.products.application.products.ProductQueryService;

import co.edu.javeriana.products.domain.Status;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<CompletableFuture<Response>> all(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "5") int size) throws ExecutionException, InterruptedException {

        Pageable paging = PageRequest.of(page, size);

        CompletableFuture<Response> rs = service.getAllProducts(paging);

        if (rs.get().getStatus().getCode().equalsIgnoreCase(Status.SUCCESS.name()))
            return new ResponseEntity<>(rs, HttpStatus.OK);

        if (rs.get().getStatus().getCode().equalsIgnoreCase(Status.EMPTY.name()))
            return new ResponseEntity<>(rs, HttpStatus.NOT_FOUND);

        if (rs.get().getStatus().getCode().equalsIgnoreCase(Status.ERROR.name()))
            return new ResponseEntity<>(rs, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(rs, HttpStatus.ACCEPTED);
    }

    @PostMapping("/products/text")
    public ResponseEntity<CompletableFuture<ResponseProduct>> text(@RequestParam(name = "text") String text,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "5") int size) throws ExecutionException, InterruptedException {
        Pageable paging = PageRequest.of(page, size);

        CompletableFuture<ResponseProduct> rs = service.getProductsByText(text, paging);

        if (rs.get().getStatus().getCode().equalsIgnoreCase(Status.SUCCESS.name()))
            return new ResponseEntity<>(rs, HttpStatus.OK);

        if (rs.get().getStatus().getCode().equalsIgnoreCase(Status.EMPTY.name()))
            return new ResponseEntity<>(rs, HttpStatus.NOT_FOUND);

        if (rs.get().getStatus().getCode().equalsIgnoreCase(Status.ERROR.name()))
            return new ResponseEntity<>(rs, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(rs, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/details")
    public ResponseEntity<CompletableFuture<ResponseProductDetail>> details(@RequestParam(name = "code") String code) throws ExecutionException, InterruptedException {
        CompletableFuture<ResponseProductDetail> rs = service.getProductsDetail(code);

        if (rs.get().getStatus().getCode().equalsIgnoreCase(Status.SUCCESS.name()))
            return new ResponseEntity<>(rs, HttpStatus.OK);

        if (rs.get().getStatus().getCode().equalsIgnoreCase(Status.EMPTY.name()))
            return new ResponseEntity<>(rs, HttpStatus.NOT_FOUND);

        if (rs.get().getStatus().getCode().equalsIgnoreCase(Status.ERROR.name()))
            return new ResponseEntity<>(rs, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }
}
