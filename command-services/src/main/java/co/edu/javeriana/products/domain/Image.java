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
@ApiModel(description = "Estructura para el manejo de la informacion de la imagen asociada al producto")
public class Image implements java.io.Serializable {

    @ApiModelProperty(notes = "Campo con el identificador de la imagen")
    private String id;

    @ApiModelProperty(notes = "Campo con la url asociada a la imagen")
    private String url;

}
