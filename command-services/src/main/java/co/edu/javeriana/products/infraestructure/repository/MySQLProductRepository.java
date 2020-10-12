package co.edu.javeriana.products.infraestructure.repository;

import co.edu.javeriana.products.infraestructure.repository.entities.Products;
import org.springframework.data.repository.CrudRepository;

public interface MySQLProductRepository extends CrudRepository<Products, String> {

}