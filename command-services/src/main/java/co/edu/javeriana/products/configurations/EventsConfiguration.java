package co.edu.javeriana.products.configurations;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventsConfiguration {

    @Value("${events.dead-letter.exchange}")
    String deadExchange;

    @Value("${events.dead-letter.queue}")
    String deadQueue;

    @Value("${events.dead-letter.routing-key}")
    String deadRoutingKey;

    @Value("${events.amqp.exchange}")
    String productExchange;

    @Value("${events.amqp.queue}")
    String productQueue;

    @Value("${events.amqp.routing-key}")
    String productRoutingKey;

    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange(deadExchange);
    }

    @Bean
    Queue dlq() {
        return QueueBuilder.durable(deadQueue).build();
    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable(productQueue)
                           .withArgument("x-dead-letter-exchange", deadExchange)
                           .withArgument("x-dead-letter-routing-key", deadRoutingKey)
                           .build();
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(productExchange);
    }

    @Bean
    Binding DLQbinding(Queue dlq, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(dlq).to(deadLetterExchange).with(deadRoutingKey);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(productRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    /*@Bean
    public Exchange exchange(){
        return ExchangeBuilder.fanoutExchange(exchangeName).build();
    }

    @Bean
    public Queue queue(){
        return QueueBuilder.durable(exchangeName).build();
    }

    @Bean
    public Binding binding(Queue queue, Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("*").noargs();
    }

    @Autowired
    public void configure(AmqpAdmin amqpAdmin, Exchange exchange, Queue queue, Binding binding){
        amqpAdmin.declareExchange(exchange);
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(binding);
    }*/

}
