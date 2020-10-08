package co.edu.javeriana.products.repository.entities;

import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@AllArgsConstructor
@Table(name = "PRODUCT_TYPE", schema = "productcommanddb", catalog = "")
public class ProductType {

    @Id
    @Column(name = "ID_TYPE", nullable = false, length = 512)
    private String idType;

    @Basic
    @Column(name = "DESCRIPTION", nullable = true, length = 512)
    private String description;

    @OneToMany(mappedBy = "productType")
    private Collection<Products> productsByIdType;

}
