package co.edu.javeriana.products.infraestructure.controllers;

import co.edu.javeriana.products.application.dtos.types.Response;
import co.edu.javeriana.products.application.dtos.types.ResponseType;
import co.edu.javeriana.products.application.types.ProductTypeQueryService;
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
public class ProductTypeQueryController {

    private final ProductTypeQueryService service;

    @PostMapping("/products/types")
    public ResponseEntity<CompletableFuture<Response>> allTypes(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "5") int size) throws ExecutionException, InterruptedException {
        Pageable paging = PageRequest.of(page, size);

        CompletableFuture<Response> rs = service.getAllTypes(paging);

        if (rs.get().getStatus().getCode().equalsIgnoreCase(Status.SUCCESS.name()))
            return new ResponseEntity<>(rs, HttpStatus.OK);

        if (rs.get().getStatus().getCode().equalsIgnoreCase(Status.EMPTY.name()))
            return new ResponseEntity<>(rs, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/products/types/{id}")
    public ResponseEntity<CompletableFuture<ResponseType>> typesById(@PathVariable("id") String id) throws ExecutionException, InterruptedException {

        if (id.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        CompletableFuture<ResponseType> rs = service.getTypeById(id);

        if (rs.get().getStatus().getCode().equalsIgnoreCase(Status.SUCCESS.name()))
            return new ResponseEntity<>(rs, HttpStatus.OK);

        if (rs.get().getStatus().getCode().equalsIgnoreCase(Status.EMPTY.name()))
            return new ResponseEntity<>(rs, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(rs, HttpStatus.NO_CONTENT);
    }
}
