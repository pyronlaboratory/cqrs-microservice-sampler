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
 * is a Spring configuration class that sets up a RabbitMQ environment for an
 * application. It defines various properties and beans related to RabbitMQ, such as
 * the hostname, username, password, exchange name, queue name, and index. The class
 * also provides methods for creating queues and exchanges, as well as declaring
 * bindings between them. Overall, the class sets up a basic RabbitMQ environment for
 * an application to use.
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

    /**
     * generates a unique name for a queue by combining the given queue name with an
     * incremental index number, resulting in a distinct identifier for each queue.
     * 
     * @returns a string representation of a unique queue name, constructed by combining
     * the original queue name and an incrementing index number.
     */
    @Bean
    public String uniqueQueueName() {
        return queueName + "." + index;
    }

    /**
     * creates a new `Queue` instance with a specified unique name, allowing for message
     * storage and retrieval.
     * 
     * @param uniqueQueueName name of the queue that is being created.
     * 
     * @returns a `Queue` object with the specified unique name and properties.
     * 
     * The function returns a `Queue` object with a unique name provided as input. The
     * `false` argument in the constructor indicates that the queue is not durable, meaning
     * messages will be lost if the node where the queue is located fails. Additionally,
     * the `false` argument for the `autoStart` property means that the queue will not
     * start automatically when the node boots up. Finally, the `true` argument for the
     * `cancellationHandled` property indicates that cancellations are handled correctly.
     */
    @Bean
    Queue eventStream(String uniqueQueueName) {
        return new Queue(uniqueQueueName, false, false, true);
    }

    /**
     * creates a new `FanoutExchange` instance with the specified `exchangeName`, set to
     * `true` for fanout routing and set to `false` for non-fanout routing.
     * 
     * @returns a new `FanoutExchange` instance with the specified name and parameters.
     * 
     * The `FanoutExchange` object returned by the function has an `exchangeName` property
     * that specifies the name of the exchange.
     * The `true` value for the `isFanout` property indicates that the exchange is a
     * fanout exchange.
     * The `false` value for the `isTemporary` property means that the exchange is not temporary.
     */
    @Bean
    FanoutExchange eventBusExchange() {
        return new FanoutExchange(exchangeName, true, false);
    }

    /**
     * creates a binding for a queue with a unique name, specifying the destination type
     * as `QUEUE`, the exchange name, and the wildcard character `*.*`.
     * 
     * @param uniqueQueueName name of the queue to which messages will be bound.
     * 
     * @returns a binding definition that sets up a queue with the specified name and
     * destination type.
     * 
     * 	- `uniqueQueueName`: The unique name of the queue to which the binding is created.
     * 	- `Binding.DestinationType.QUEUE`: The type of destination, in this case, a queue.
     * 	- `exchangeName`: The name of the exchange where the binding is created.
     * 	- ` "*.*"`: The routing key pattern for the binding, indicating that the message
     * should be routed to any queue with the same name as the original message.
     * 	- `null`: The value of the `routingKey` attribute is null, indicating that no
     * specific routing key is specified.
     */
    @Bean
    Binding binding(String uniqueQueueName) {
        return new Binding(uniqueQueueName, Binding.DestinationType.QUEUE, exchangeName, "*.*", null);
    }

    /**
     * creates a `CachingConnectionFactory` instance with customized configuration for a
     * database connection, storing it for later use.
     * 
     * @returns a caching ConnectionFactory instance configured with the specified hostname,
     * username, and password.
     * 
     * 	- The `CachingConnectionFactory` object created is of type `hostname`.
     * 	- The `setUsername()` and `setPassword()` methods are called on the object to set
     * the values of the `username` and `password` attributes, respectively.
     * 
     * Overall, the function returns a `CachingConnectionFactory` object with the specified
     * `hostname`, `username`, and `password` properties.
     */
    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(hostname);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    /**
     * creates a new `RabbitAdmin` instance, sets auto-startup to true, declares an
     * exchange, queue, and binding using the provided unique queue name.
     * 
     * @param uniqueQueueName name of a unique queue that is declared and managed by the
     * `RabbitAdmin` instance generated by the function.
     * 
     * @returns a `RabbitAdmin` instance that sets up and manages RabbitMQ exchanges,
     * queues, and bindings.
     * 
     * 1/ `RabbitAdmin rabbitAdmin`: This is the instance of the `RabbitAdmin` class that
     * is returned by the function. It represents a RabbitMQ administrator that can be
     * used to manage queues, exchanges, and other rabbitmq resources.
     * 2/ `connectionFactory()`: This is a method that returns a connection factory object,
     * which is used to connect to the RabbitMQ server. The connection factory object is
     * responsible for creating and managing connections to the RabbitMQ server.
     * 3/ `setAutoStartup(true)`: This method sets whether the RabbitMQ administrator
     * should automatically start up when the application starts.
     * 4/ `declareExchange(eventBusExchange())`: This method declares an exchange with
     * the specified name, which is used to route messages between queues and exchanges.
     * 5/ `declareQueue(eventStream(uniqueQueueName))`: This method declares a queue with
     * the specified name, which is used to store messages that are consumed by a consumer.
     * 6/ `declareBinding(binding(uniqueQueueName))`: This method declares a binding
     * between a queue and an exchange, which specifies how messages are routed from the
     * queue to the exchange.
     * 
     * Overall, the `rabbitAdmin` function provides a way to manage RabbitMQ resources
     * in a Java application, including creating and managing queues, exchanges, and bindings.
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
