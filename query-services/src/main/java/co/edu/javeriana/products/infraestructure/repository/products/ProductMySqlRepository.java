package co.edu.javeriana.products.infraestructure.repository.products;

import co.edu.javeriana.products.domain.Product;
import co.edu.javeriana.products.domain.ProductType;
import co.edu.javeriana.products.domain.Status;
import co.edu.javeriana.products.infraestructure.repository.Repositories;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Repository
@RequiredArgsConstructor
public class ProductMySqlRepository implements Repositories<Product> {

    private final JdbcTemplate template;

    @Override
    public Optional<Product> findById(String id) {
        try {
            String sql = "SELECT * FROM PRODUCTS WHERE PRODUCT_CODE = ?";
            return template.queryForObject(sql,
                    new Object[]{id},
                    (rs, rowNum) ->
                            Optional.of(new Product(
                                    rs.getString("PRODUCT_CODE"),
                                    rs.getString("PRODUCT_NAME"),
                                    rs.getString("PRODUCT_DESCRIPTION"),
                                    rs.getDate("START_DATE").toLocalDate(),
                                    rs.getDate("END_DATE").toLocalDate(),
                                    new ProductType(rs.getString("PRODUCT_TYPE_ID"), "", ""),
                                    rs.getLong("PRODUCT_PRICE"),
                                    rs.getString("ORIGIN_CITY"),
                                    rs.getString("DESTINATION_CITY"),
                                    rs.getString("PRODUCT_IMAGE"),
                                    rs.getString("VENDOR_ID"),
                                    ""
                            ))
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public CompletableFuture<String> create(Product data) {
        try {
            if (findById(data.getProductCode()).isPresent()) return CompletableFuture.completedFuture(Status.EXIST.name());

            String sql = "INSERT INTO PRODUCTS (PRODUCT_CODE, " +
                                                "PRODUCT_NAME, " +
                                                "PRODUCT_DESCRIPTION, " +
                                                "START_DATE, " +
                                                "END_DATE, " +
                                                "PRODUCT_TYPE_ID, " +
                                                "PRODUCT_PRICE, " +
                                                "ORIGIN_CITY, " +
                                                "DESTINATION_CITY, " +
                                                "PRODUCT_IMAGE, " +
                                                "VENDOR_ID) " +
                         "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

            template.update(sql,
                            data.getProductCode(),
                            data.getProductName(),
                            data.getProductDescription(),
                            data.getStartDate(),
                            data.getEndDate(),
                            data.getType().getId(),
                            data.getProductPrice(),
                            data.getOriginCity(),
                            data.getDestinationCity(),
                            data.getImageId(),
                            data.getVendorId());

            return CompletableFuture.completedFuture(Status.CREATED.name());
        } catch(Exception e) {
            return CompletableFuture.completedFuture(Status.ERROR.name());
        }
    }

    @Override
    public CompletableFuture<String> update(Product data) {
        try {
            if (findById(data.getProductCode()).isEmpty()) return CompletableFuture.completedFuture(Status.NO_EXIST.name());

            String sql = "UPDATE PRODUCTS SET " +
                                    "PRODUCT_NAME = ?, " +
                                    "PRODUCT_DESCRIPTION = ?, " +
                                    "START_DATE = ?, " +
                                    "END_DATE = ?, " +
                                    "PRODUCT_TYPE_ID = ?, " +
                                    "PRODUCT_PRICE = ?, " +
                                    "ORIGIN_CITY = ?, " +
                                    "DESTINATION_CITY = ?, " +
                                    "PRODUCT_IMAGE = ?, " +
                                    "VENDOR_ID = ?" +
                         "WHERE PRODUCT_CODE = ? " ;

            this.template.update(sql, data.getProductName(),
                                      data.getProductDescription(),
                                      data.getStartDate(),
                                      data.getEndDate(),
                                      data.getType().getId(),
                                      data.getProductPrice(),
                                      data.getOriginCity(),
                                      data.getDestinationCity(),
                                      data.getImageId(),
                                      data.getVendorId(),
                                      data.getProductCode());

            return CompletableFuture.completedFuture(Status.UPDATED.name());
        } catch (Exception e) {
            return CompletableFuture.completedFuture(Status.ERROR.name());
        }
    }

    @Override
    public CompletableFuture<String> delete(Product data) {
        try {
            if (findById(data.getProductCode()).isEmpty()) return CompletableFuture.completedFuture(Status.NO_EXIST.name());

            String sql = "DELETE FROM PRODUCTS WHERE PRODUCT_CODE = ?";

            this.template.update(sql, data.getProductCode());

            return CompletableFuture.completedFuture(Status.DELETED.name());
        } catch (Exception e) {
            return CompletableFuture.completedFuture(Status.ERROR.name());
        }
    }
}
