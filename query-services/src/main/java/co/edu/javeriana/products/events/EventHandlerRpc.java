package co.edu.javeriana.products.events;

import co.edu.javeriana.products.application.dtos.rpc.products.Request;
import co.edu.javeriana.products.application.dtos.rpc.products.Response;
import co.edu.javeriana.products.application.products.ProductQueryService;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class EventHandlerRpc {

    private static final Logger LOG = LoggerFactory.getLogger(EventHandlerRpc.class);

    private final ProductQueryService service;

    @RabbitListener(queues = "${events.rpc.products.queue}", concurrency = "10")
    public Response receive(Request data) throws ExecutionException, InterruptedException {
        LOG.debug("INFORMACION_IDS: {}", data.getIds());
        List<String> ids = Arrays.asList(data.getIds().split(","));
        return service.getByProductById(ids).get();
    }

}
