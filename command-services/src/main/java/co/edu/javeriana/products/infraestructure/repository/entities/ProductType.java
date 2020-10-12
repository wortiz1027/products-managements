package co.edu.javeriana.products.infraestructure.repository.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "PRODUCT_TYPE", schema = "productcommanddb")
public class ProductType {

    @Id
    @Column(name = "ID_TYPE", nullable = false, length = 512)
    private String idType;

    @Basic
    @Column(name = "DESCRIPTION", nullable = true, length = 512)
    private String description;

}
