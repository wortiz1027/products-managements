package co.edu.javeriana.products.application.dtos.products;

import co.edu.javeriana.products.application.dtos.Status;
import co.edu.javeriana.products.domain.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Estructura con la informacion del detalle del producto")
public class ResponseProductDetail implements java.io.Serializable {

    @ApiModelProperty(notes = "Campo que indica el estado de la consulta")
    private Status status;

    @ApiModelProperty(notes = "Estructura con el detalle del producto consultado")
    private Product product;
}
