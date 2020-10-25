package co.edu.javeriana.products.application.dtos.products;

import co.edu.javeriana.products.application.dtos.Status;
import co.edu.javeriana.products.domain.Product;
import lombok.Data;

@Data
public class ResponseProductDetail implements java.io.Serializable {
    private Status status;
    private Product product;
}
