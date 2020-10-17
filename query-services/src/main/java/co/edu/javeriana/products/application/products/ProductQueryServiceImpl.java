package co.edu.javeriana.products.application.products;

import co.edu.javeriana.products.application.dtos.products.ResponseProduct;
import co.edu.javeriana.products.application.dtos.products.Response;
import co.edu.javeriana.products.domain.Product;
import co.edu.javeriana.products.domain.Status;
import co.edu.javeriana.products.infraestructure.repository.Repositories;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {

    private final Repositories<Product> repository;

    @Override
    public CompletableFuture<Response> getAllProducts() {
        Response response = new Response();
        co.edu.javeriana.products.application.dtos.Status status = new co.edu.javeriana.products.application.dtos.Status();
        try {
            Optional<List<Product>> products = this.repository.findByAll();
            if (!products.isPresent()) {
                status.setCode(Status.EMPTY.name());
                status.setDescription("There are not rows availables");
                response.setStatus(status);
                return CompletableFuture.completedFuture(response);
            }

            // recorrer la lista para extraer los id de imagenes

            // publicar esa lista en una cola

            status.setCode(Status.SUCCESS.name());
            status.setDescription("There are rows availables");
            response.setStatus(status);
            response.setProducts(products.get());
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            status.setCode(Status.ERROR.name());
            status.setDescription(String.format("There is an error getting products type: %s", e.getMessage()));
            response.setStatus(status);
            return CompletableFuture.completedFuture(response);
        }
    }

    @Override
    public CompletableFuture<ResponseProduct> getProductsByText(String text) {
        ResponseProduct response = new ResponseProduct();
        co.edu.javeriana.products.application.dtos.Status status = new co.edu.javeriana.products.application.dtos.Status();
        try {
            Optional<List<Product>> products = this.repository.findByText(text);

            if (!products.isPresent()) {
                status.setCode(Status.EMPTY.name());
                status.setDescription("There are not rows availables");
                response.setStatus(status);
                return CompletableFuture.completedFuture(response);
            }

            // recorrer la lista para extraer los id de imagenes

            // publicar esa lista en una cola

            status.setCode(Status.SUCCESS.name());
            status.setDescription("");
            response.setStatus(status);
            response.setProduct(products.get());

            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            status.setCode(co.edu.javeriana.products.domain.Status.ERROR.name());
            status.setDescription(String.format("Error getting rows: %s", e.getMessage()));
            response.setStatus(status);

            return CompletableFuture.completedFuture(response);
        }
    }

}