package co.edu.javeriana.products.infraestructure.repository.products;

import co.edu.javeriana.products.domain.Product;
import co.edu.javeriana.products.domain.ProductType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int i) throws SQLException {
        ProductType type = new ProductType();
        type.setId(rs.getString("ID_TYPE"));
        type.setDescription(rs.getString("DESCRIPTION"));

        Product product = new Product();
        product.setProductCode(rs.getString("PRODUCT_CODE"));
        product.setProductName(rs.getString("PRODUCT_NAME"));
        product.setProductDescription(rs.getString("PRODUCT_DESCRIPTION"));
        product.setStartDate(rs.getDate("START_DATE").toLocalDate());
        product.setEndDate(rs.getDate("END_DATE").toLocalDate());
        product.setType(type);
        product.setProductPrice(rs.getLong("PRODUCT_PRICE"));
        product.setOriginCity(rs.getString("ORIGIN_CITY"));
        product.setDestinationCity(rs.getString("DESTINATION_CITY"));
        product.setImageId(rs.getString("PRODUCT_IMAGE"));
        product.setVendorId(rs.getString("VENDOR_ID"));

        return product;
    }
}
