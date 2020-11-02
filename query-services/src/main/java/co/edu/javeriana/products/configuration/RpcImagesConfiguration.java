package co.edu.javeriana.products.configuration;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RpcImagesConfiguration {

    @Value("${events.rpc.images.exchange}")
    private String rpcExchange;

    @Value("${events.rpc.images.queue}")
    private String rpcQueue;

    @Bean
    public DirectExchange directExchange1() {
        return new DirectExchange(rpcExchange);
    }

    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate1(RabbitTemplate rabbitTemplate){
        return new AsyncRabbitTemplate(rabbitTemplate);
    }

    @Bean
    public Queue response1(){
        return new Queue(rpcQueue);
    }

}
