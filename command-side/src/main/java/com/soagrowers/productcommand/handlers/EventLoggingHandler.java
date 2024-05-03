package com.soagrowers.productcommand.handlers;

import com.soagrowers.productevents.events.ProductAddedEvent;
import com.soagrowers.productevents.events.ProductSaleableEvent;
import com.soagrowers.productevents.events.ProductUnsaleableEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * EventHandler's (a.k.a. EventListeners) are used to react to events and perform associated
 * actions.
 * Created by ben on 24/09/15.
 */
@Component
public class EventLoggingHandler {

    private static final Logger LOG = LoggerFactory.getLogger(EventLoggingHandler.class);
    private static final String IID = String.valueOf(Double.valueOf(Math.random() * 1000).intValue());

    /**
     * Is called when a product is added to an inventory. It logs information about the
     * event, including the instance ID, event type, and event ID, along with the name
     * of the product.
     * 
     * @param event `ProductAddedEvent` object that triggered the function execution,
     * providing its class name and ID, as well as the event name.
     */
    @EventHandler
    public void handle(ProductAddedEvent event) {
        LOG.debug("Instance:{} EventType:{} EventId:[{}] '{}'", IID, event.getClass().getSimpleName(), event.getId(), event.getName());
    }

    /**
     * Logs a debug message with the instance ID, event type, and event ID when the
     * `ProductSaleableEvent` occurs.
     * 
     * @param event ProductSaleableEvent object that contains information about a product
     * sale, and provides the Event ID for logging purposes.
     */
    @EventHandler
    public void handle(ProductSaleableEvent event) {
        LOG.debug("Instance:{} EventType:{} EventId:[{}]", IID, event.getClass().getSimpleName(), event.getId());
    }

    /**
     * At @EventHandler is triggered when a product becomes unsaleable. It logs information
     * about the event, including the product ID and event type, to the system log using
     * the `LOG.debug()` method.
     * 
     * @param event product unsaleable event that triggered the `handle()` method execution.
     */
    @EventHandler
    public void handle(ProductUnsaleableEvent event) {
        LOG.debug("Instance:{} EventType:{} EventId:[{}]", IID, event.getClass().getSimpleName(), event.getId());
    }
}






