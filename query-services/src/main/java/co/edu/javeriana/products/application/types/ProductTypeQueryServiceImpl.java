package co.edu.javeriana.products.application.types;

import co.edu.javeriana.products.application.dtos.Status;
import co.edu.javeriana.products.application.dtos.types.Response;
import co.edu.javeriana.products.application.dtos.types.ResponseType;
import co.edu.javeriana.products.domain.Product;
import co.edu.javeriana.products.domain.ProductType;
import co.edu.javeriana.products.infraestructure.repository.Repositories;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ProductTypeQueryServiceImpl implements ProductTypeQueryService {

    private final Repositories<ProductType> types;

    @Override
    public CompletableFuture<Response> getAllTypes(Pageable paging) {
        Response response = new Response();
        Status status = new Status();
        try {
            Optional<Page<ProductType>> types = this.types.findByAll(paging);

            if (types.isEmpty()) {
                status.setCode(co.edu.javeriana.products.domain.Status.EMPTY.name());
                status.setDescription("No rows found!");
                response.setStatus(status);
                return CompletableFuture.completedFuture(response);
            }

            System.out.println("---> " + types.get().getContent().size());
            System.out.println("---> " + types.get().getContent().get(0).getDescription());
            List<ProductType> tps = types.get().getContent();

            for (ProductType pt : types.get().getContent()) {
                System.out.println(pt.getDescription());
            }

            status.setCode(co.edu.javeriana.products.domain.Status.SUCCESS.name());
            status.setDescription("Rows found!");
            response.setStatus(status);
            response.setTypes(tps);

            return CompletableFuture.completedFuture(response);
        } catch(Exception e) {
            e.printStackTrace();
            status.setCode(co.edu.javeriana.products.domain.Status.ERROR.name());
            status.setDescription(String.format("Error getting rows: %s", e.getMessage()));
            response.setStatus(status);

            return CompletableFuture.completedFuture(response);
        }
    }

    @Override
    public CompletableFuture<ResponseType> getTypeById(String id) {
        ResponseType response = new ResponseType();
        Status status = new Status();
        try {
            Optional<ProductType> type = this.types.findById(id);

            if (type.isPresent()) {
                status.setCode(co.edu.javeriana.products.domain.Status.EMPTY.name());
                status.setDescription("No row found!");
                response.setStatus(status);
                return CompletableFuture.completedFuture(response);
            }

            status.setCode(co.edu.javeriana.products.domain.Status.SUCCESS.name());
            status.setDescription("Rows found!");
            response.setStatus(status);
            response.setType(type.get());

            return CompletableFuture.completedFuture(response);
        } catch(Exception e) {
            status.setCode(co.edu.javeriana.products.domain.Status.ERROR.name());
            status.setDescription(String.format("Error getting detail type: %s", e.getMessage()));
            response.setStatus(status);

            return CompletableFuture.completedFuture(response);
        }
    }

}
