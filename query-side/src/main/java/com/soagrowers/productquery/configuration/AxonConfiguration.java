package com.soagrowers.productquery.configuration;

import org.axonframework.contextsupport.spring.AnnotationDriven;
import org.axonframework.eventhandling.*;
import org.axonframework.eventhandling.amqp.spring.ListenerContainerLifecycleManager;
import org.axonframework.eventhandling.amqp.spring.SpringAMQPConsumerConfiguration;
import org.axonframework.eventhandling.amqp.spring.SpringAMQPTerminal;
import org.axonframework.serializer.json.JacksonSerializer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * defines various configurations for an AMQP event bus, including connection factory,
 * transaction manager, and queue name. It also provides methods to create a
 * SimpleCluster, SpringAMQPConsumerConfiguration, and EventBusTerminal, as well as
 * an EventBus instance.
 */
@Configuration
@AnnotationDriven
class AxonConfiguration {

    private static final String AMQP_CONFIG_KEY = "AMQP.Config";

    @Autowired
    public ConnectionFactory connectionFactory;

    @Autowired
    public PlatformTransactionManager transactionManager;

    @Autowired
    public String uniqueQueueName;

    @Value("${spring.application.terminal}")
    private String terminalName;


    /*
    @Value("${spring.application.queue}")
    private String queueName;

    @Bean
    XStreamSerializer xmlSerializer() {
        return new XStreamSerializer();
    }*/


    /**
     * returns a Jackson serializer for JSON data.
     * 
     * @returns a Jackson Serializer instance for JSON serialization.
     * 
     * 	- `JacksonSerializer`: The class that implements the serialization functionality
     * using Jackson library.
     * 	- `new`: The operator used to create a new instance of the `JacksonSerializer` class.
     * 
     * Therefore, the output is a newly created instance of the `JacksonSerializer` class.
     */
    @Bean
    JacksonSerializer axonJsonSerializer() {
        return new JacksonSerializer();
    }

    /**
     * creates a new `ListenerContainerLifecycleManager` instance, sets its `connectionFactory`
     * property to a provided value, and returns the instance.
     * 
     * @returns a `ListenerContainerLifecycleManager` instance with a set `connectionFactory`.
     * 
     * 	- `ListenerContainerLifecycleManager`: This is the class that represents a container
     * for managing listeners. It provides methods to register, unregister, and check if
     * a listener is registered.
     * 	- `setConnectionFactory()`: This method sets the connection factory used by the
     * listener container lifecycle manager. The connection factory is responsible for
     * creating connections to the database.
     * 
     * Overall, this function returns a `ListenerContainerLifecycleManager` object that
     * can be used to manage listeners in a Spring Boot application.
     */
    @Bean
    ListenerContainerLifecycleManager listenerContainerLifecycleManager() {
        ListenerContainerLifecycleManager listenerContainerLifecycleManager = new ListenerContainerLifecycleManager();
        listenerContainerLifecycleManager.setConnectionFactory(connectionFactory);
        return listenerContainerLifecycleManager;
    }

    /**
     * creates a Spring AMQP consumer configuration with settings for message size,
     * transaction manager, and queue name.
     * 
     * @returns a Spring AMQP consumer configuration object with settings for transaction
     * size, transaction manager, and queue name.
     * 
     * 	- `setTxSize(10)` sets the transaction size limit for the consumer. This value
     * determines how much data can be consumed before a new transaction is started.
     * 	- `setTransactionManager(transactionManager)` specifies the transaction manager
     * to be used by the consumer. This is an important configuration option as it
     * determines how transactions are managed and rolled back in case of failure.
     * 	- `setQueueName(uniqueQueueName)` sets the name of the queue that the consumer
     * will subscribe to. This value is used to identify the queue and differentiate it
     * from other queues in the same broker.
     */
    @Bean
    SpringAMQPConsumerConfiguration springAMQPConsumerConfiguration() {
        SpringAMQPConsumerConfiguration amqpConsumerConfiguration = new SpringAMQPConsumerConfiguration();
        amqpConsumerConfiguration.setTxSize(10);
        amqpConsumerConfiguration.setTransactionManager(transactionManager);
        amqpConsumerConfiguration.setQueueName(uniqueQueueName);
        return amqpConsumerConfiguration;
    }


    /**
     * creates a new `SimpleCluster` instance and sets the `AMQP_CONFIG_KEY` property of
     * its metadata with the provided `SpringAMQPConsumerConfiguration`.
     * 
     * @param springAMQPConsumerConfiguration configuration for an AMQP consumer in Spring,
     * which is used to customize the behavior of the AMQP consumer within the SimpleCluster
     * framework.
     * 
     * 1/ AMQP_CONFIG_KEY: This is a property that sets the configuration for an AMQP
     * consumer in the Spring framework.
     * 2/ UniqueQueueName: This is a string representing the name of the unique queue to
     * be created for the consumer.
     * 
     * @returns a new SimpleCluster instance with an AMQP configuration property set.
     * 
     * 	- The `SimpleCluster` object represents a cluster of AMQP consumers that can
     * handle messages from an AMQP queue.
     * 	- The `uniqueQueueName` property is set to a unique identifier for the queue,
     * which is used to distinguish it from other queues in the cluster.
     * 	- The `MetaData` property is set to a map of configuration properties for the
     * consumer, which are obtained from the `springAMQPConsumerConfiguration` parameter
     * passed to the function. These properties can be used to customize the behavior of
     * the consumer.
     */
    @Bean
    SimpleCluster simpleCluster(SpringAMQPConsumerConfiguration springAMQPConsumerConfiguration) {
        SimpleCluster simpleCluster = new SimpleCluster(uniqueQueueName);
        simpleCluster.getMetaData().setProperty(AMQP_CONFIG_KEY, springAMQPConsumerConfiguration);
        return simpleCluster;
    }

    /**
     * creates an instance of `SpringAMQPTerminal`, setting various properties and returning
     * it.
     * 
     * @returns a Spring AMQP terminal instance configured with various properties and settings.
     * 
     * 	- The `SpringAMQPTerminal` object represents a Spring AMQP terminal, which provides
     * an interface for sending and receiving messages through an AMQP broker.
     * 	- The `setConnectionFactory` method sets the connection factory used to connect
     * to the AMQP broker.
     * 	- The `setSerializer` methods sets the serializer used to serialize messages
     * before sending them through the broker. The default is `axonJsonSerializer`, which
     * uses JSON to serialize messages.
     * 	- The `setExchangeName` method sets the name of the exchange on which the terminal
     * will send and receive messages.
     * 	- The `setListenerContainerLifecycleManager` method sets the listener container
     * lifecycle manager, which manages the creation and destruction of message listeners.
     * 	- The `durable` property indicates whether the terminal is durable or not, meaning
     * whether the messages sent through it will be persisted in case of a failure.
     * 	- The `transactional` property indicates whether the terminal is transactional
     * or not, meaning whether the messages sent through it will be part of a larger transaction.
     */
    @Bean
    EventBusTerminal terminal() {
        SpringAMQPTerminal terminal = new SpringAMQPTerminal();
        terminal.setConnectionFactory(connectionFactory);
        //terminal.setSerializer(xmlSerializer());
        terminal.setSerializer(axonJsonSerializer());
        terminal.setExchangeName(terminalName);
        terminal.setListenerContainerLifecycleManager(listenerContainerLifecycleManager());
        terminal.setDurable(true);
        terminal.setTransactional(true);
        return terminal;
    }

    /**
     * creates an EventBus instance by combining a custom cluster selector with the default
     * terminal. The resulting EventBus enables the transmission of events between different
     * components in a clustered environment.
     * 
     * @param simpleCluster cluster manager for which the event bus is to be created
     * 
     * 	- `SimpleCluster`: This class is the base class for all clusters in the application.
     * It contains various attributes such as `id`, `name`, `type`, and `nodes`.
     * 	- `DefaultClusterSelector`: This class implements the `ClusterSelector` interface,
     * which defines the methods for selecting a cluster based on the input parameters.
     * 	- `terminal()`: This method returns a terminal object, which is used to indicate
     * the end of the stream in the `EventBus` implementation.
     * 
     * @returns a ClusteringEventBus instance, which is used to handle events related to
     * cluster management.
     * 
     * 	- `SimpleCluster`: This is an argument passed to the function, which represents
     * the cluster configuration for the event bus.
     * 	- `ClusteringEventBus`: This is the type of event bus returned by the function,
     * which is a custom implementation of an event bus that uses a default cluster
     * selector and terminator.
     * 	- `DefaultClusterSelector`: This is another argument passed to the function, which
     * represents the default cluster selector used by the event bus.
     * 	- `terminal()`: This is a method called internally within the function to generate
     * the event bus. It returns a terminal object, which is an implementation detail not
     * intended for external use.
     */
    @Bean
    EventBus eventBus(SimpleCluster simpleCluster) {
        return new ClusteringEventBus(new DefaultClusterSelector(simpleCluster), terminal());
    }

}
