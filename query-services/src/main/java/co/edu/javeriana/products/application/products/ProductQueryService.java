package co.edu.javeriana.products.application.products;

import co.edu.javeriana.products.application.dtos.products.ResponseProduct;
import co.edu.javeriana.products.application.dtos.products.Response;
import co.edu.javeriana.products.application.dtos.products.ResponseProductDetail;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductQueryService {

    CompletableFuture<co.edu.javeriana.products.application.dtos.rpc.products.Response> getByProductById(List<String> ids);
    CompletableFuture<Response> getAllProducts(Pageable paging);
    CompletableFuture<ResponseProduct> getProductsByText(String text, Pageable paging);
    CompletableFuture<ResponseProductDetail> getProductsDetail(String code);
}
