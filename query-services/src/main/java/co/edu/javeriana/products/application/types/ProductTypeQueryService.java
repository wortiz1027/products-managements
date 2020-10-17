package co.edu.javeriana.products.application.types;

import co.edu.javeriana.products.application.dtos.types.Response;
import co.edu.javeriana.products.application.dtos.types.ResponseType;

import java.util.concurrent.CompletableFuture;

public interface ProductTypeQueryService {

    CompletableFuture<Response> getAllTypes();
    CompletableFuture<ResponseType> getTypeById(String id);

}
