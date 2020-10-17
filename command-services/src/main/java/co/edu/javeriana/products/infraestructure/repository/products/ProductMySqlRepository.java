package co.edu.javeriana.products.infraestructure.repository.products;

import co.edu.javeriana.products.domain.Image;
import co.edu.javeriana.products.domain.Product;
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
public class ProductMySqlRepository implements ProductRepository {

    private final JdbcTemplate template;

    @Override
    public Optional<Product> findById(String id) {
        try {
            String sql = "SELECT * FROM PRODUCTS WHERE PRODUCT_ID = ?";
            return template.queryForObject(sql,
                                            new Object[]{id},
                                            (rs, rowNum) ->
                                                    Optional.of(new Product(
                                                            rs.getString("PRODUCT_ID"),
                                                            rs.getString("PRODUCT_CODE"),
                                                            rs.getString("PRODUCT_NAME"),
                                                            rs.getString("PRODUCT_DESCRIPTION"),
                                                            rs.getDate("START_DATE").toLocalDate(),
                                                            rs.getDate("END_DATE").toLocalDate(),
                                                            new ProductType(rs.getString("PRODUCT_TYPE_ID"), "", ""),
                                                            rs.getLong("PRODUCT_PRICE"),
                                                            rs.getString("ORIGIN_CITY"),
                                                            rs.getString("DESTINATION_CITY"),
                                                            new Image(rs.getString("PRODUCT_IMAGE"), ""),
                                                            rs.getString("VENDOR_ID"),
                                                            ""
                                                    ))
                                            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public CompletableFuture<String> create(Product product) {
        try {
            if (findById(product.getProductId()).isPresent()) return CompletableFuture.completedFuture(Status.EXIST.name());

            String sql = "INSERT INTO PRODUCTS (PRODUCT_ID, " +
                                                "PRODUCT_CODE, " +
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
                                                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

            template.update(sql,
                            product.getProductId(),
                            product.getProductCode(),
                            product.getProductName(),
                            product.getProductDescription(),
                            product.getStartDate(),
                            product.getEndDate(),
                            product.getType().getId(),
                            product.getProductPrice(),
                            product.getOriginCity(),
                            product.getDestinationCity(),
                            product.getImage().getId(),
                            product.getVendorId());

            return CompletableFuture.completedFuture(Status.CREATED.name());
        } catch(Exception e) {
            return CompletableFuture.completedFuture(Status.ERROR.name());
        }
    }

    @Override
    public CompletableFuture<String> update(Product product) {
        try {
            if (findById(product.getProductId()).isEmpty()) return CompletableFuture.completedFuture(Status.NO_EXIST.name());

            String sql = "UPDATE PRODUCTS SET " +
                                "PRODUCT_CODE = ?, " +
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
                                "WHERE PRODUCT_ID = ? " ;

            this.template.update(sql, product.getProductCode(),
                                        product.getProductName(),
                                        product.getProductDescription(),
                                        product.getStartDate(),
                                        product.getEndDate(),
                                        product.getType().getId(),
                                        product.getProductPrice(),
                                        product.getOriginCity(),
                                        product.getDestinationCity(),
                                        product.getImage().getId(),
                                        product.getVendorId(),
                                        product.getProductId());

            return CompletableFuture.completedFuture(Status.UPDATED.name());
        } catch (Exception e) {
            return CompletableFuture.completedFuture(Status.ERROR.name());
        }

    }

    @Override
    public CompletableFuture<String> delete(Product product) {
        try {
            if (findById(product.getProductId()).isEmpty()) return CompletableFuture.completedFuture(Status.NO_EXIST.name());

            String sql = "DELETE FROM PRODUCTS WHERE PRODUCT_ID = ?";

            this.template.update(sql, product.getProductId());

            return CompletableFuture.completedFuture(Status.DELETED.name());
        } catch (Exception e) {
            return CompletableFuture.completedFuture(Status.ERROR.name());
        }
    }

}
