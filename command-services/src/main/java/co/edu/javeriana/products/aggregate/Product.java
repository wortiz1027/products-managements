package co.edu.javeriana.products.aggregate;

import co.edu.javeriana.products.repository.entities.ProductType;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.sql.Date;

@Aggregate
public class Product {

    @AggregateIdentifier
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
    private ProductType productTypeByProductTypeId;

    public Product() {
    }

    // TODO agregar los handlers

}
