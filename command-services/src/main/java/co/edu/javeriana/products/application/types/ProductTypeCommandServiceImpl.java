package co.edu.javeriana.products.application.types;

import co.edu.javeriana.products.domain.ProductType;
import co.edu.javeriana.products.domain.Response;
import co.edu.javeriana.products.domain.Status;
import co.edu.javeriana.products.infraestructure.repository.types.ProductTypeRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ProductTypeCommandServiceImpl implements ProductTypeCommandService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductTypeCommandServiceImpl.class);

    private final ProductTypeRepository repository;

    @Override
    public CompletableFuture<Response> createProductType(ProductType type) {
        Response response = new Response();
        try {
            String status = this.repository.create(type).get();
            response.setStatus(status);

            if (!status.equalsIgnoreCase(Status.CREATED.name())) {
                response.setDescription(String.format("The product type with id: {%s} has an error", type.getId()));
                return CompletableFuture.completedFuture(response);
            }

            response.setDescription(String.format("The product type with id: {%s} has been created", type.getId()));
            type.setStatus(Status.CREATED.name());

            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            response.setStatus(Status.ERROR.name());
            response.setDescription(String.format("Exception creating row {%s} has been release: {%s}", type.getId(), e.getMessage()));
            return CompletableFuture.completedFuture(response);
        }
    }

    @Override
    public CompletableFuture<Response> updateProductType(ProductType type) {
        Response response = new Response();
        try {
            String status = this.repository.update(type).get();
            response.setStatus(status);

            if (!status.equalsIgnoreCase(Status.UPDATED.name())) {
                response.setDescription(String.format("The product type with id: {%s} has an error: {%s}", type.getId(), status));
                return CompletableFuture.completedFuture(response);
            }

            response.setDescription(String.format("The product type with id: {%s} has been updated", type.getId()));
            type.setStatus(status);

            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            response.setStatus(Status.ERROR.name());
            response.setDescription(String.format("Exception updating row {%s} has been release: {%s}", type.getId(), e.getMessage()));
            return CompletableFuture.completedFuture(response);
        }
    }

    @Override
    public CompletableFuture<Response> deleteProductType(ProductType type) {
        Response response = new Response();
        try {
            String status = this.repository.delete(type).get();
            response.setStatus(status);

            if (!status.equalsIgnoreCase(Status.DELETED.name())) {
                response.setDescription(String.format("The product with id: {%s} has an error", type.getId()));
                return CompletableFuture.completedFuture(response);
            }

            response.setDescription(String.format("The product with id: {%s} has been deleted", type.getId()));
            type.setStatus(Status.DELETED.name());

            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            response.setStatus(Status.ERROR.name());
            response.setDescription(String.format("Exception deleting row {%s} has been release: {%s}", type.getId(), e.getMessage()));
            return CompletableFuture.completedFuture(response);
        }
    }

}
