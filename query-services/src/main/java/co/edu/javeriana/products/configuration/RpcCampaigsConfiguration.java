package co.edu.javeriana.products.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RpcCampaigsConfiguration {

    @Value("${events.rpc.products.exchange}")
    private String rpcExchange;

    @Value("${events.rpc.products.queue}")
    private String rpcQueue;

    @Value("${events.rpc.products.routing-key}")
    private String rpcRoutingKey;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(rpcExchange);
    }

    @Bean
    public Queue queue() {
        return new Queue(rpcQueue);
    }

    @Bean
    public Binding binding(DirectExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(rpcRoutingKey);
    }

}
