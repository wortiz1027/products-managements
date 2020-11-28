package co.edu.javeriana.products.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Estructura para el manejo de la informacion del tipo de vendor")
public class VendorTypes {

    @ApiModelProperty(notes = "Campo con el id del tipo de vendor")
    private String idType;

    @ApiModelProperty(notes = "Campo con la descripcion del tipo de vendor")
    private String description;

}