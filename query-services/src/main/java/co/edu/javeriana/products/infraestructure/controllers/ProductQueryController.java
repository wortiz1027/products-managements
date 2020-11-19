package co.edu.javeriana.products.infraestructure.controllers;

import co.edu.javeriana.products.application.dtos.products.Response;
import co.edu.javeriana.products.application.dtos.products.ResponseProduct;
import co.edu.javeriana.products.application.dtos.products.ResponseProductDetail;
import co.edu.javeriana.products.application.products.ProductQueryService;

import co.edu.javeriana.products.domain.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value="Consultas de productos ofrecidos por toures balon")
public class ProductQueryController {

    private final ProductQueryService service;

    @ApiOperation(value = "Consulta de los productos registrados en el sistema", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Consulta exitosa de los productos"),
            @ApiResponse(code = 404, message = "Error no se encontro informacion de los productos"),
            @ApiResponse(code = 500, message = "Error interno en el servidor, contacte y reporte con el administrador")
    })
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

    @ApiOperation(value = "Consulta por texto de productos registrados en el sistema", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Consulta exitosa de los productos"),
            @ApiResponse(code = 404, message = "Error no se encontro informacion de los productos"),
            @ApiResponse(code = 500, message = "Error interno en el servidor, contacte y reporte con el administrador")
    })
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

    @ApiOperation(value = "Consulta del producto por codigo registrados en el sistema", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Consulta exitosa del producto"),
            @ApiResponse(code = 404, message = "Error no se encontro informacion del producto"),
            @ApiResponse(code = 500, message = "Error interno en el servidor, contacte y reporte con el administrador")
    })
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
