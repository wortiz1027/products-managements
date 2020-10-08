package co.edu.javeriana.products.commands;

import co.edu.javeriana.products.repository.entities.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class AddProductCommand {

    private String productId;
    private String productCode;
    private String productName;
    private String productDescription;
    private Date startDate;
    private Date endDate;
    private Long productPrice;
    private String originCity;
    private String destinationCity;
    private String vendorId;
    private ProductType productType;

}
