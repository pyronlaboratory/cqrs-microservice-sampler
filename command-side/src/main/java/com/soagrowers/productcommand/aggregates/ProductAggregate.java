package com.soagrowers.productcommand.aggregates;

import com.soagrowers.productcommand.commands.AddProductCommand;
import com.soagrowers.productcommand.commands.MarkProductAsSaleableCommand;
import com.soagrowers.productcommand.commands.MarkProductAsUnsaleableCommand;
import com.soagrowers.productevents.events.ProductAddedEvent;
import com.soagrowers.productevents.events.ProductSaleableEvent;
import com.soagrowers.productevents.events.ProductUnsaleableEvent;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ProductAggregate is essentially a DDD AggregateRoot (from the DDD concept). In event-sourced
 * systems, Aggregates are often stored and retreived using a 'Repository'. In the
 * simplest terms, Aggregates are the sum of their applied 'Events'.
 * <p/>
 * The Repository stores the aggregate's Events in an 'Event Store'. When an Aggregate
 * is re-loaded by the repository, the Repository re-applies all the stored events
 * to the aggregate thereby re-creating the logical state of the Aggregate.
 * <p/>
 * The ProductAggregate Aggregate can handle and react to 'Commands', and when it reacts
 * to these com.soagrowers.product.commands it creates and 'applies' Events that represent the logical changes
 * to be made. These Events are also handled by the ProductAggregate.
 * <p/>
 * Axon takes care of much of this via the CommandBus, EventBus and Repository.
 * <p/>
 * Axon delivers com.soagrowers.product.commands placed on the bus to the Aggregate. Axon supports the 'applying' of
 * Events to the Aggregate, and the handling of those events by the aggregate or any other
 * configured EventHandlers.
 */
public class ProductAggregate extends AbstractAnnotatedAggregateRoot {

    private static final Logger LOG = LoggerFactory.getLogger(ProductAggregate.class);

    /**
     * Aggregates that are managed by Axon must have a unique identifier.
     * Strategies similar to GUID are often used. The annotation 'AggregateIdentifier'
     * identifies the id field as such.
     */
    @AggregateIdentifier
    private String id;
    private String name;
    private boolean isSaleable = false;

    /**
     * This default constructor is used by the Repository to construct
     * a prototype ProductAggregate. Events are then used to set properties
     * such as the ProductAggregate's Id in order to make the Aggregate reflect
     * it's true logical state.
     */
    public ProductAggregate() {
    }

    /**
     * This constructor is marked as a 'CommandHandler' for the
     * AddProductCommand. This command can be used to construct
     * new instances of the Aggregate. If successful a new ProductAddedEvent
     * is 'applied' to the aggregate using the Axon 'apply' method. The apply
     * method appears to also propagate the Event to any other registered
     * 'Event Listeners', who may take further action.
     *
     * @param command
     */
    @CommandHandler
    public ProductAggregate(AddProductCommand command) {
        LOG.debug("Command: 'AddProductCommand' received.");
        LOG.debug("Queuing up a new ProductAddedEvent for product '{}'", command.getId());
        apply(new ProductAddedEvent(command.getId(), command.getName()));
    }

    /**
     * Determines if a product can be marked as saleable and applies the appropriate event
     * based on the product's current state.
     * 
     * @param command `MarkProductAsSaleableCommand`, which triggers the function to
     * either make the product saleable or report an error if the product is already saleable.
     */
    @CommandHandler
    public void markSaleable(MarkProductAsSaleableCommand command) {
        LOG.debug("Command: 'MarkProductAsSaleableCommand' received.");
        if (!this.isSaleable()) {
            apply(new ProductSaleableEvent(id));
        } else {
            throw new IllegalStateException("This ProductAggregate (" + this.getId() + ") is already Saleable.");
        }
    }

    /**
     * Takes a `MarkProductAsUnsaleableCommand` object as input and marks a product as
     * unsaleable if it is currently saleable, or throws an illegal state exception if
     * it is already off-sale.
     * 
     * @param command `MarkProductAsUnsaleableCommand` that triggers the function to mark
     * a product as unsaleable if it is currently saleable, or raise an illegal state
     * exception if it is already off-sale.
     */
    @CommandHandler
    public void markUnsaleable(MarkProductAsUnsaleableCommand command) {
        LOG.debug("Command: 'MarkProductAsUnsaleableCommand' received.");
        if (this.isSaleable()) {
            apply(new ProductUnsaleableEvent(id));
        } else {
            throw new IllegalStateException("This ProductAggregate (" + this.getId() + ") is already off-sale.");
        }
    }

    /**
     * At `@EventSourcingHandler` takes a `ProductAddedEvent` object and updates the
     * fields `id` and `name` based on the values in the event, then logs a debug message
     * indicating the event type and updated field values.
     * 
     * @param event `ProductAddedEvent` object passed to the handler, and its fields (`id`
     * and `name`) are assigned the corresponding values from the event object.
     */
    @EventSourcingHandler
    public void on(ProductAddedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        LOG.debug("Applied: 'ProductAddedEvent' [{}] '{}'", event.getId(), event.getName());
    }

    /**
     * Updates a component's `isSaleable` property to `true` after receiving a
     * `ProductSaleableEvent`. It also logs the event ID using `LOG.debug()`.
     * 
     * @param event `ProductSaleableEvent` event object passed to the function, allowing
     * the function to access and process its data.
     */
    @EventSourcingHandler
    public void on(ProductSaleableEvent event) {
        this.isSaleable = true;
        LOG.debug("Applied: 'ProductSaleableEvent' [{}]", event.getId());
    }

    /**
     * For `@EventSourcingHandler` sets the value of `isSaleable` to `false` when it
     * receives a `ProductUnsaleableEvent`.
     * 
     * @param event ProductUnsaleableEvent object that triggered the function.
     */
    @EventSourcingHandler
    public void on(ProductUnsaleableEvent event) {
        this.isSaleable = false;
        LOG.debug("Applied: 'ProductUnsaleableEvent' [{}]", event.getId());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isSaleable() {
        return isSaleable;
    }
}
