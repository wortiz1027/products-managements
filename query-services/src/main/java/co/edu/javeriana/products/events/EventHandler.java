package co.edu.javeriana.products.events;

import co.edu.javeriana.products.domain.Product;
import co.edu.javeriana.products.domain.ProductType;
import co.edu.javeriana.products.domain.Status;
import co.edu.javeriana.products.infraestructure.repository.Repositories;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(EventHandler.class);

    private final Repositories<Product> products;
    private final Repositories<ProductType> types;

    @RabbitListener(queues = "${events.amqp.queue}")
    public void consumer(final Product data) {
        LOG.info("recibiendo: {}", data);

        Optional<ProductType> tp = types.findById(data.getType().getId());

        if (data.getStatus().equalsIgnoreCase(Status.CREATED.name()) && tp.isEmpty()) {
            ProductType t = ProductType.builder().id(data.getType().getId()).description(data.getType().getDescription()).build();
            this.types.create(t);
        }

        Optional<Product> product = products.findById(data.getProductId());

        if (data.getStatus().equalsIgnoreCase(Status.CREATED.name()) && product.isEmpty()) {
            this.products.create(data);
        }

        if (data.getStatus().equalsIgnoreCase(Status.UPDATED.name()) && !product.isPresent()) {
            this.products.update(data);
        }

        if (data.getStatus().equalsIgnoreCase(Status.DELETED.name()) && !product.isPresent()) {
            this.products.delete(data);
        }

        LOG.info("Product with code [{}] has been saved", data.getProductCode());
    }

}
