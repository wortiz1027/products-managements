package co.edu.javeriana.products.application.dtos.products;

import co.edu.javeriana.products.domain.Product;
import lombok.Data;

import java.util.List;

@Data
public class Response implements java.io.Serializable {

    private List<Product> products;

}
