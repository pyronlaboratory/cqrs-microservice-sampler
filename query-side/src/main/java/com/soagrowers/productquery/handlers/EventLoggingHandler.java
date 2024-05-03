package com.soagrowers.productquery.handlers;

import com.soagrowers.productevents.events.ProductAddedEvent;
import com.soagrowers.productevents.events.ProductSaleableEvent;
import com.soagrowers.productevents.events.ProductUnsaleableEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Handler's (a.k.a. Listeners) can be used to react to events and perform associated
 * actions, such as updating a 'materialised-view' for example.
 * Created by ben on 24/09/15.
 */
@Component
public class EventLoggingHandler {

    private static final Logger LOG = LoggerFactory.getLogger(EventLoggingHandler.class);

    @Value("${spring.application.index}")
    private Integer indexId;

    /**
     * At `@EventHandler` level logs the details of a `ProductAddedEvent` to the application
     * log, including the instance ID, event class name, event ID, and event name.
     * 
     * @param event `ProductAddedEvent` that triggered the function execution, providing
     * its class name and ID number, as well as its name.
     */
    @EventHandler
    public void handle(ProductAddedEvent event) {
        LOG.debug("Instance:[{}] Event:{} Id:{} Name:'{}'", indexId, event.getClass().getSimpleName(), event.getId(), event.getName());
    }

    /**
     * At `@EventHandler` level logs information about an event of a class type and event
     * ID using the `LOG` method in passive voice.
     * 
     * @param event ProductSaleableEvent object that is being handled by the function.
     */
    @EventHandler
    public void handle(ProductSaleableEvent event) {
        LOG.debug("Instance:[{}] Event:{} Id:{}", indexId, event.getClass().getSimpleName(), event.getId());
    }

    /**
     * At `@EventHandler` level logs details of a `ProductUnsaleableEvent`. It provides
     * information about the event, including its class name and ID, using the `LOG.debug()`
     * method.
     * 
     * @param event `ProductUnsaleableEvent` instance that triggered the event handler.
     */
    @EventHandler
    public void handle(ProductUnsaleableEvent event) {
        LOG.debug("Instance:[{}] Event:{} Id:{}", indexId, event.getClass().getSimpleName(), event.getId());
    }
}






