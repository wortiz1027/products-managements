package co.edu.javeriana.products.infraestructure.repository.types;

import co.edu.javeriana.products.domain.Product;
import co.edu.javeriana.products.domain.ProductType;
import co.edu.javeriana.products.domain.Status;
import co.edu.javeriana.products.infraestructure.repository.Repositories;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Repository
@RequiredArgsConstructor
public class ProductTypeMySqlRepository  implements Repositories<ProductType> {

    private final JdbcTemplate template;

    @Override
    public Optional<Page<ProductType>> findByAll(Pageable paging) {
        String sql = "SELECT * " +
                     "FROM PRODUCT_TYPE " +
                     "LIMIT %d OFFSET %d";
        List<ProductType> types = this.template.query(String.format(sql, paging.getPageSize(), paging.getOffset()), new ProductTypesRowMapper());
        return Optional.of(new PageImpl<>(types, paging, count()));
    }

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
    public Optional<ProductType> findByCode(String code) {
        return Optional.empty();
    }

    @Override
    public Optional<Page<ProductType>> findByText(String text, Pageable paging) {
        List<ProductType> types = new ArrayList<>();
        return Optional.empty();
    }

    private int count() {
        return this.template.queryForObject("SELECT count(*) FROM PRODUCT_TYPE", Integer.class);
    }

    @Override
    public CompletableFuture<String> create(ProductType data) {
        try {
            if (findById(data.getId()).isPresent()) return CompletableFuture.completedFuture(Status.EXIST.name());

            String sql = "INSERT INTO PRODUCT_TYPE (ID_TYPE, DESCRIPTION) VALUES (?, ?)";
            this.template.update(sql, data.getId(), data.getDescription());
            return CompletableFuture.completedFuture(Status.CREATED.name());
        } catch(Exception e) {
            return CompletableFuture.completedFuture(Status.ERROR.name());
        }
    }

    @Override
    public CompletableFuture<String> update(ProductType data) {
        try {
            if (findById(data.getId()).isEmpty()) return CompletableFuture.completedFuture(Status.NO_EXIST.name());

            String sql = "UPDATE PRODUCT_TYPE SET DESCRIPTION = ? WHERE ID_TYPE = ?";

            this.template.update(sql, data.getDescription(), data.getId());

            return CompletableFuture.completedFuture(Status.UPDATED.name());
        } catch (Exception e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture(Status.ERROR.name());
        }
    }

    @Override
    public CompletableFuture<String> delete(ProductType data) {
        try {
            if (findById(data.getId()).isEmpty()) return CompletableFuture.completedFuture(Status.NO_EXIST.name());

            String sql = "DELETE FROM PRODUCT_TYPE WHERE ID_TYPE = ?";

            this.template.update(sql, data.getId());

            return CompletableFuture.completedFuture(Status.DELETED.name());
        } catch (Exception e) {
            return CompletableFuture.completedFuture(Status.ERROR.name());
        }
    }
}
