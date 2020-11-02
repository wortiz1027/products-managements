package co.edu.javeriana.products.application.dtos.rpc.products;

import co.edu.javeriana.products.application.dtos.Status;
import co.edu.javeriana.products.domain.Product;
import lombok.Data;

import java.util.List;

@Data
public class Response implements java.io.Serializable {

    private Status status;
    private List<Product> products;

}