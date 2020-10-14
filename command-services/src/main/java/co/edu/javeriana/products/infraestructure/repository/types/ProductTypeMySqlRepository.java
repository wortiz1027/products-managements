package co.edu.javeriana.products.infraestructure.repository.types;

import co.edu.javeriana.products.domain.ProductType;
import co.edu.javeriana.products.domain.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Repository
@RequiredArgsConstructor
public class ProductTypeMySqlRepository implements ProductTypeRepository {

    private final JdbcTemplate template;

    @Override
    public Optional<ProductType> findById(String id) {
        try {
            String sql = "SELECT * FROM PRODUCT_TYPE WHERE ID_TYPE = ?";
            return template.queryForObject(sql,
                    new Object[]{id},
                    (rs, rowNum) ->
                            Optional.of(new ProductType(
                                    rs.getString("ID_TYPE"),
                                    rs.getString("DESCRIPTION"),
                                    ""
                            ))
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public CompletableFuture<String> create(ProductType type) {
        try {
            if (findById(type.getId()).isPresent()) return CompletableFuture.completedFuture(Status.EXIST.name());

            String sql = "INSERT INTO PRODUCT_TYPE (ID_TYPE, DESCRIPTION) VALUES (?, ?)";
            this.template.update(sql, type.getId(), type.getDescription());
            return CompletableFuture.completedFuture(Status.CREATED.name());
        } catch(Exception e) {
            return CompletableFuture.completedFuture(Status.ERROR.name());
        }
    }

    @Override
    public CompletableFuture<String> update(ProductType type) {
        try {
            if (findById(type.getId()).isEmpty()) return CompletableFuture.completedFuture(Status.NO_EXIST.name());

            String sql = "UPDATE PRODUCT_TYPE SET DESCRIPTION = ? WHERE ID_TYPE = ?";

            this.template.update(sql, type.getDescription(), type.getId());

            return CompletableFuture.completedFuture(Status.UPDATED.name());
        } catch (Exception e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture(Status.ERROR.name());
        }
    }

    @Override
    public CompletableFuture<String> delete(ProductType type) {
        try {
            if (findById(type.getId()).isEmpty()) return CompletableFuture.completedFuture(Status.NO_EXIST.name());

            String sql = "DELETE FROM PRODUCT_TYPE WHERE ID_TYPE = ?";

            this.template.update(sql, type.getId());

            return CompletableFuture.completedFuture(Status.DELETED.name());
        } catch (Exception e) {
            return CompletableFuture.completedFuture(Status.ERROR.name());
        }
    }

}
