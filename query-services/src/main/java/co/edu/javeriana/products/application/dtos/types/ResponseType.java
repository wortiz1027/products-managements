package co.edu.javeriana.products.application.dtos.types;

import co.edu.javeriana.products.application.dtos.Status;
import co.edu.javeriana.products.domain.ProductType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseType implements java.io.Serializable {

    private Status status;
    private ProductType type;

}
