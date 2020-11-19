package co.edu.javeriana.products.application.dtos.products;

import co.edu.javeriana.products.application.dtos.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Estructura con la informacion de los productos")
public class ResponseProduct implements java.io.Serializable {

    @ApiModelProperty(notes = "Campo que indica el estado de la consulta")
    private Status status;

    @ApiModelProperty(notes = "Listado de productos registrados en toures balon")
    private Map<String, Object> data;

}
