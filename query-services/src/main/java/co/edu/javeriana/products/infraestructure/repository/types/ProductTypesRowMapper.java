package co.edu.javeriana.products.infraestructure.repository.types;

import co.edu.javeriana.products.domain.ProductType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductTypesRowMapper implements RowMapper<ProductType> {
    @Override
    public ProductType mapRow(ResultSet rs, int i) throws SQLException {
        ProductType type = new ProductType();
        type.setId(rs.getString("ID_TYPE"));
        type.setDescription(rs.getString("DESCRIPTION"));
        return type;
    }
}
