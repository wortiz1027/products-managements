package co.edu.javeriana.products.domain;

import co.edu.javeriana.products.events.dtos.Image;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product implements java.io.Serializable {

    @ApiModelProperty(notes = "Campo con la informacion del identificador del producto")
    @JsonProperty("productId")
    private String productId;

    @ApiModelProperty(notes = "Campo con el codigo asociado al producto")
    @JsonProperty("productCode")
    private String productCode;

    @ApiModelProperty(notes = "Campo con el nombre que identifica el producto")
    @JsonProperty("productName")
    private String productName;

    @ApiModelProperty(notes = "Campo con la descripcion adicional del producto")
    @JsonProperty("productDescription")
    private String productDescription;

    @ApiModelProperty(notes = "Campo con la fecha de vigencia del producto")
    @JsonProperty("startDate")
    private LocalDate startDate;

    @ApiModelProperty(notes = "Campo con la fecha final de vigencia del producto")
    @JsonProperty("endDate")
    private LocalDate endDate;

    @ApiModelProperty(notes = "Estructura con la informacion del tipo de producto")
    @JsonProperty("type")
    private ProductType type;

    @ApiModelProperty(notes = "Campo con el valor del producto")
    @JsonProperty("productPrice")
    private Long productPrice;

    @ApiModelProperty(notes = "Campo con la ciudad de origen del producto")
    @JsonProperty("originCity")
    private String originCity;

    @ApiModelProperty(notes = "Campo con la ciudad de destino del producto")
    @JsonProperty("destinationCity")
    private String destinationCity;

    @ApiModelProperty(notes = "Estructura con el detalle de la imagen asociada al producto")
    @JsonProperty("image")
    private Image image;

    @ApiModelProperty(notes = "Campo con el identificador del proveedor")
    @JsonProperty("vendor")
    private Vendor vendor;

    @ApiModelProperty(notes = "Campo que informa el estado del producto")
    @JsonProperty("status")
    private String status;

}
