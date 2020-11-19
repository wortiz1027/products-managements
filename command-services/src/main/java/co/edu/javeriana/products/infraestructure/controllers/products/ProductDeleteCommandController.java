package co.edu.javeriana.products.infraestructure.controllers.products;

import co.edu.javeriana.products.application.products.ProductCommandService;
import co.edu.javeriana.products.domain.Product;
import co.edu.javeriana.products.domain.Response;
import co.edu.javeriana.products.domain.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Api(value="Eliminacion de productos ofrecidos por toures balon")
public class ProductDeleteCommandController {

    private final ProductCommandService service;

    @ApiOperation(value = "Eliminacion de productos en el sistema", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Eliminacion exitosa del producto"),
            @ApiResponse(code = 400, message = "Error en los datos de entrada no se envio informacion del producto"),
            @ApiResponse(code = 500, message = "Error interno en el servidor, contacte y reporte con el administrador")
    })
    @DeleteMapping("/products")
    public ResponseEntity<CompletableFuture<Response>> handle(@RequestBody Product data) throws ExecutionException, InterruptedException {
        if (data == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        CompletableFuture<Response> rs = service.deleteProduct(data);

        if (rs.get().getStatus().equalsIgnoreCase(Status.DELETED.name()))
            return new ResponseEntity<>(rs, HttpStatus.OK);

        if (rs.get().getStatus().equalsIgnoreCase(Status.ERROR.name()))
            return new ResponseEntity<>(rs, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(rs, HttpStatus.CONFLICT);
    }

}
