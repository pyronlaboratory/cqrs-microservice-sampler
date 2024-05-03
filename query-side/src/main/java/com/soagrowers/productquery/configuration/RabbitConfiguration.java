package com.soagrowers.productquery.configuration;

import org.axonframework.contextsupport.spring.AnnotationDriven;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ben on 19/02/16.
 */
@Configuration
@AnnotationDriven
public class RabbitConfiguration {

    @Value("${spring.rabbitmq.hostname}")
    private String hostname;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.application.exchange}")
    private String exchangeName;

    @Value("${spring.application.queue}")
    private String queueName;

    @Value("${spring.application.index}")
    private Integer index;

    @Bean
    public String uniqueQueueName() {
        return queueName + "." + index;
    }

    /**
     * Creates a new queue named based on a given string and configures its properties
     * for a non-durable and non-transitive store, with an auto-acknowledge mechanism for
     * messages.
     * 
     * @param uniqueQueueName name of the queue that is to be created.
     * 
     * @returns a queue with the specified name.
     */
    @Bean
    Queue eventStream(String uniqueQueueName) {
        return new Queue(uniqueQueueName, false, false, true);
    }

    @Bean
    FanoutExchange eventBusExchange() {
        return new FanoutExchange(exchangeName, true, false);
    }

    /**
     * Creates a binding that directs messages from any exchange to a queue with the
     * specified name.
     * 
     * @param uniqueQueueName name of the queue to which the binding will be created.
     * 
     * @returns a binding definition for a queue with the given name.
     */
    @Bean
    Binding binding(String uniqueQueueName) {
        return new Binding(uniqueQueueName, Binding.DestinationType.QUEUE, exchangeName, "*.*", null);
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(hostname);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    /**
     * Creates a new instance of RabbitAdmin, sets its auto-startup to true, declares an
     * exchange and two queues using the unique queue name, and returns the instance.
     * 
     * @param uniqueQueueName name of a queue that is being declared and used by the `RabbitAdmin`.
     * 
     * @returns a RabbitAdmin object with the declared exchanges, queues, and bindings.
     */
    @Bean
    @Required
    RabbitAdmin rabbitAdmin(String uniqueQueueName) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory());
        admin.setAutoStartup(true);
        admin.declareExchange(eventBusExchange());
        admin.declareQueue(eventStream(uniqueQueueName));
        admin.declareBinding(binding(uniqueQueueName));
        return admin;
    }
}
