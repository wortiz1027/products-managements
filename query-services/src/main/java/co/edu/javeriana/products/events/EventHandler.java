package co.edu.javeriana.products.events;

import co.edu.javeriana.products.domain.Product;
import co.edu.javeriana.products.domain.ProductType;
import co.edu.javeriana.products.infraestructure.repository.Repositories;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(EventHandler.class);

    private final ObjectMapper mapper;
    private final Repositories<Product> products;
    private final Repositories<ProductType> types;

    @RabbitListener(queues = "${events.amqp.queue}")
    public void consumer(final Product data) throws IOException {
        LOG.info("recibiendo: {}", data);

        ProductType t = ProductType.builder().id(data.getType().getId()).description(data.getType().getDescription()).build();
        this.types.create(t);
        this.products.create(data);
        LOG.info("Product with code [{}] has been saved", data.getProductCode());
    }

}
