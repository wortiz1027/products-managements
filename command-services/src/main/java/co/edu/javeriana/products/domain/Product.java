package co.edu.javeriana.products.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private String id;
    private String productCode;
    private String productName;
    private String productDescription;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long productPrice;
    private String originCity;
    private String destinationCity;
    private String vendorId;
    private String status;

}
