package co.edu.javeriana.products.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product implements java.io.Serializable {

    /*private String productId;
    private String productCode;
    private String productName;
    private String productDescription;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProductType type;
    private Long productPrice;
    private String originCity;
    private String destinationCity;
    private String imageId;
    private String vendorId;
    private String status;*/
    @JsonProperty("productId")
    private String productId;
    @JsonProperty("productCode")
    private String productCode;
    @JsonProperty("productName")
    private String productName;
    @JsonProperty("productDescription")
    private String productDescription;
    @JsonProperty("startDate")
    private LocalDate startDate;
    @JsonProperty("endDate")
    private LocalDate endDate;
    @JsonProperty("type")
    private ProductType type;
    @JsonProperty("productPrice")
    private Long productPrice;
    @JsonProperty("originCity")
    private String originCity;
    @JsonProperty("destinationCity")
    private String destinationCity;
    @JsonProperty("imageId")
    private String imageId;
    @JsonProperty("vendorId")
    private String vendorId;
    @JsonProperty("status")
    private String status;

}
