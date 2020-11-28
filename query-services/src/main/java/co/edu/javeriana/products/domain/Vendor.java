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
@ApiModel(description = "Estructura para el manejo de la informacion del vendor")
public class Vendor implements java.io.Serializable {

    @ApiModelProperty(notes = "Campo con el id del vendor")
    private String idProvider;

    @ApiModelProperty(notes = "Campo con el nombre del vendor")
    private String nameProvider;

    @ApiModelProperty(notes = "Campo con el nit del vendor")
    private String nit;

    @ApiModelProperty(notes = "Campo con el id del tipo de vendor")
    private VendorTypes types;

    @ApiModelProperty(notes = "Campo con la direccion del vendor")
    private String address;

    @ApiModelProperty(notes = "Campo con el telefono del vendor")
    private String telephone;

    @ApiModelProperty(notes = "Campo con el email del vendor")
    private String email;

    @ApiModelProperty(notes = "Campo con el id del pais")
    private String idCountry;

    @ApiModelProperty(notes = "Campo con el id de la provincia/departamento")
    private String idProvince;

    @ApiModelProperty(notes = "Campo con el id de la ciudad")
    private String idCity;

    @ApiModelProperty(notes = "Estado del vendor")
    private String status;

}