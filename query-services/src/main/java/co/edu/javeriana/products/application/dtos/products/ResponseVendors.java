package co.edu.javeriana.products.application.dtos.products;

import co.edu.javeriana.products.application.dtos.Status;
import co.edu.javeriana.products.domain.Vendor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "Estructura para el manejo de la informacion del vendor")
public class ResponseVendors implements java.io.Serializable {

    @ApiModelProperty(notes = "Campo que indica el codigo del estado de la transaccion")
    private Status status;

    @ApiModelProperty(notes = "Listado de vendors registradas en toures balon")
    private List<Vendor> vendors;

}
