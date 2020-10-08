package co.edu.javeriana.products.projectors;

import co.edu.javeriana.products.events.ProductAddedEvent;
import co.edu.javeriana.products.repository.ProductRepository;
import co.edu.javeriana.products.repository.entities.Products;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductProjector {

    private final ProductRepository repository;

    @EventHandler
    public void on(ProductAddedEvent event) {
        Products product = new Products(event.getProductId(),
                                        event.getProductCode(),
                                        event.getProductName(),
                                        event.getProductDescription(),
                                        event.getStartDate(),
                                        event.getEndDate(),
                                        event.getProductPrice(),
                                        event.getOriginCity(),
                                        event.getDestinationCity(),
                                        event.getVendorId(),
                                        event.getProductType());
        repository.save(product);
    }

}
