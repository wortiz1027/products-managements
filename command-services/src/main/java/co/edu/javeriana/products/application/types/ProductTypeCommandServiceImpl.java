package co.edu.javeriana.products.application.types;

import co.edu.javeriana.products.domain.ProductType;
import co.edu.javeriana.products.domain.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ProductTypeCommandServiceImpl implements ProductTypeCommandService {

    @Override
    public CompletableFuture<Response> createProductType(ProductType product) {
        return null;
    }

    @Override
    public CompletableFuture<Response> updateProductType(ProductType product) {
        return null;
    }

    @Override
    public CompletableFuture<Response> deleteProductType(ProductType product) {
        return null;
    }

}
