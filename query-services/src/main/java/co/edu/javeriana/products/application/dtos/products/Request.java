package co.edu.javeriana.products.application.dtos.products;

import lombok.Data;

@Data
public class Request implements java.io.Serializable {

    private String type;
    private String data;

}
