package co.edu.javeriana.products.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Estructura para el manejo de la informacion del producto")
public class Product {

    @ApiModelProperty(notes = "Campo con el id del producto")
    private String productId;

    @ApiModelProperty(notes = "Campo que almacena el codigo que se le entrega al producto y facilita las busquedas")
    private String productCode;

    @ApiModelProperty(notes = "Nombre del producto")
    private String productName;

    @ApiModelProperty(notes = "Detalle con la informacion adicional del producto")
    private String productDescription;

    @ApiModelProperty(notes = "Fecha de inicio de vigencia del producto")
    private LocalDate startDate;

    @ApiModelProperty(notes = "Fecha final de la vigencia del producto en el sistema")
    private LocalDate endDate;

    @ApiModelProperty(notes = "Tipo de producto")
    private ProductType type;

    @ApiModelProperty(notes = "Valor del producto COP")
    private Long productPrice;

    @ApiModelProperty(notes = "Ciudad de origen del producto")
    private String originCity;

    @ApiModelProperty(notes = "Ciudad de destino del producto")
    private String destinationCity;

    @ApiModelProperty(notes = "Informacion de la imagen asociada al producto")
    private Image image;

    @ApiModelProperty(notes = "Identificador del proveedor del producto")
    private String vendorId;

    @ApiModelProperty(notes = "Estado del producto")
    private String status;

}
