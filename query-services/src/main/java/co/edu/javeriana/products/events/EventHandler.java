package co.edu.javeriana.products.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(EventHandler.class);

    @RabbitListener(queues = "${events.amqp.queue}")
    public void consumer(String msg) {
        LOG.info("recibiendo: {}", msg);
    }

}
