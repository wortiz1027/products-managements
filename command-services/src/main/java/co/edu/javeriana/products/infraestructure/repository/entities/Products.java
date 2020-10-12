package co.edu.javeriana.products.infraestructure.repository.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "PRODUCTS", schema = "productcommanddb")
public class Products {

    @Id
    @Column(name = "PRODUCT_ID", nullable = false, length = 512)
    private String productId;

    @Basic
    @Column(name = "PRODUCT_CODE", nullable = true, length = 512)
    private String productCode;

    @Basic
    @Column(name = "PRODUCT_NAME", nullable = true, length = 512)
    private String productName;

    @Basic
    @Column(name = "PRODUCT_DESCRIPTION", nullable = true, length = 512)
    private String productDescription;

    @Basic
    @Column(name = "START_DATE", nullable = true)
    private Date startDate;

    @Basic
    @Column(name = "END_DATE", nullable = true)
    private Date endDate;

    @Basic
    @Column(name = "PRODUCT_PRICE", nullable = true)
    private Long productPrice;

    @Basic
    @Column(name = "ORIGIN_CITY", nullable = true, length = 512)
    private String originCity;

    @Basic
    @Column(name = "DESTINATION_CITY", nullable = true, length = 512)
    private String destinationCity;

    @Basic
    @Column(name = "PRODUCT_IMAGE", nullable = true, length = 1500)
    private String productImage;

    @Basic
    @Column(name = "VENDOR_ID", nullable = true, length = 512)
    private String vendorId;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_TYPE_ID", referencedColumnName = "ID_TYPE")
    private ProductType productTypeId;

}
