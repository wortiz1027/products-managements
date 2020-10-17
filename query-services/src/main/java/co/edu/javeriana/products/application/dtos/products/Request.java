package co.edu.javeriana.products.application.dtos.products;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Request implements java.io.Serializable {

    private String type;
    private String data;

}
