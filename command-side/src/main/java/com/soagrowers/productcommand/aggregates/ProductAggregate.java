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
 * in Axon handles the management of products, including their unique identifier,
 * name, and saleability status. The class has three constructors: one for the default
 * instance, one for handling the AddProductCommand, and one for handling the
 * MarkProductAsSaleableCommand and MarkProductAsUnsaleableCommand. Additionally,
 * there are event handlers for ProductAddedEvent, ProductSaleableEvent, and
 * ProductUnsaleableEvent to update the product's state based on events raised by the
 * application.
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
     * marks a product as saleable based on its current state. If the product is not
     * saleable, it applies an event to make it saleable. Otherwise, it throws an exception
     * indicating that the product is already saleable.
     * 
     * @param command MarkProductAsSaleableCommand object that triggered the function
     * execution, providing the necessary context for the function to perform its actions.
     * 
     * The `MarkProductAsSaleableCommand` has an `id` field that represents the product
     * ID.
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
     * marks a product as unsaleable based on its saleability status, throwing an illegal
     * state exception if it is already off-sale.
     * 
     * @param command `MarkProductAsUnsaleableCommand` message that triggers the function
     * execution.
     * 
     * 	- The function first logs a message in the debug log stating that the
     * `MarkProductAsUnsaleableCommand` command has been received.
     * 	- If the product is saleable, a new `ProductUnsaleableEvent` object is created
     * and applied to the product using the `apply()` method.
     * 	- If the product is already off-sale, an `IllegalStateException` is thrown with
     * the message "This ProductAggregate ( id ) is already off-sale."
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
     * at `@EventSourcingHandler` processes a `ProductAddedEvent` by assigning the event's
     * `id` and `name` to instance variables, then logs a debug message with the event's
     * ID and name.
     * 
     * @param event `ProductAddedEvent` that triggered the event handler, providing the
     * event's ID and name for further processing.
     * 
     * 	- `id`: A unique identifier for the event, represented by an integer value.
     * 	- `name`: The name of the product added, represented by a string value.
     */
    @EventSourcingHandler
    public void on(ProductAddedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        LOG.debug("Applied: 'ProductAddedEvent' [{}] '{}'", event.getId(), event.getName());
    }

    /**
     * updates the `isSaleable` field of an object and logs a message with the ID of the
     * received `ProductSaleableEvent`.
     * 
     * @param event `ProductSaleableEvent` that triggered the event handler method.
     * 
     * 	- `isSaleable`: A boolean variable representing whether the product is saleable
     * or not. It is set to `true` by this function.
     */
    @EventSourcingHandler
    public void on(ProductSaleableEvent event) {
        this.isSaleable = true;
        LOG.debug("Applied: 'ProductSaleableEvent' [{}]", event.getId());
    }

    /**
     * updates the `isSaleable` field of the object to `false` when a `ProductUnsaleableEvent`
     * occurs, and logs the event ID with a debug message.
     * 
     * @param event `ProductUnsaleableEvent` object that is being handled by the `on()`
     * method.
     * 
     * 	- `isSaleable`: A boolean indicating whether the product is saleable or not.
     * 	- `LOG`: An instance of the `Logger` class used for debugging purposes.
     */
    @EventSourcingHandler
    public void on(ProductUnsaleableEvent event) {
        this.isSaleable = false;
        LOG.debug("Applied: 'ProductUnsaleableEvent' [{}]", event.getId());
    }

    /**
     * returns the `id` field of an object.
     * 
     * @returns a string representing the value of the `id` field.
     */
    public String getId() {
        return id;
    }

    /**
     * retrieves a string representing the name of an object.
     * 
     * @returns a string representing the name of the object.
     */
    public String getName() {
        return name;
    }

    /**
     * determines if an item is saleable based on a predefined condition.
     * 
     * @returns a boolean value indicating whether the item is saleable or not.
     */
    public boolean isSaleable() {
        return isSaleable;
    }
}
