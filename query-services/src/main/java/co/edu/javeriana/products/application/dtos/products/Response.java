package co.edu.javeriana.products.application.dtos.products;

import co.edu.javeriana.products.application.dtos.Status;
import co.edu.javeriana.products.domain.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response implements java.io.Serializable {

    private Status status;
    private List<Product> products;

}
