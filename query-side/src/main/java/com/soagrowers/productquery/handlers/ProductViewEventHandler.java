package com.soagrowers.productquery.handlers;

import com.soagrowers.productevents.events.ProductAddedEvent;
import com.soagrowers.productevents.events.ProductSaleableEvent;
import com.soagrowers.productevents.events.ProductUnsaleableEvent;
import com.soagrowers.productquery.domain.Product;
import com.soagrowers.productquery.repository.ProductRepository;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventhandling.replay.ReplayAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * is responsible for handling various events related to products in a system. It
 * listens to events such as product addition, saleability changes, and unSALEability
 * changes, and updates the product repository accordingly. The class also provides
 * methods for clearing the view before replaying events and logging information
 * during event handling.
 */
@Component
public class ProductViewEventHandler implements ReplayAware {

    private static final Logger LOG = LoggerFactory.getLogger(ProductViewEventHandler.class);

    @Autowired
    private ProductRepository productRepository;

    /**
     * is an EventHandler that processes a `ProductAddedEvent`. It saves the product to
     * the repository with its ID and name.
     * 
     * @param event `ProductAddedEvent` object that triggered the function, providing its
     * ID and name as additional information.
     * 
     * 	- `event.getId()`: The unique identifier of the event.
     * 	- `event.getName()`: The name of the product added.
     * 	- `event.getId()`: The ID of the product added.
     */
    @EventHandler
    public void handle(ProductAddedEvent event) {
        LOG.info("ProductAddedEvent: [{}] '{}'", event.getId(), event.getName());
        productRepository.save(new Product(event.getId(), event.getName(), false));
    }

    /**
     * is triggered when a `ProductSaleableEvent` occurs and updates the `saleable` status
     * of the related product in the repository, if it was not saleable before.
     * 
     * @param event ProductSaleableEvent object that contains information about the sale
     * of a product, including its ID.
     * 
     * 	- `event.getId()`: This property returns the unique identifier of the event.
     * 	- `productRepository.exists(event.getId())`: This method checks if a product with
     * the specified ID exists in the repository.
     * 	- `product = productRepository.findOne(event.getId())`: This method retrieves the
     * product associated with the specified ID from the repository.
     * 	- `!product.isSaleable()`: This property indicates whether the product is saleable
     * or not.
     * 	- `product.setSaleable(true)`: This method sets the saleability of the product
     * to true.
     * 	- `productRepository.save(product)`: This method saves the modified product in
     * the repository.
     */
    @EventHandler
    public void handle(ProductSaleableEvent event) {
        LOG.info("ProductSaleableEvent: [{}]", event.getId());
        if (productRepository.exists(event.getId())) {
            Product product = productRepository.findOne(event.getId());
            if (!product.isSaleable()) {
                product.setSaleable(true);
                productRepository.save(product);
            }
        }
    }

    /**
     * updates a product's saleability status based on an event, logging and saving changes
     * to the repository if necessary.
     * 
     * @param event "ProductUnsaleableEvent" object that contains information about an
     * unsaleable product, which is used to determine if the product is still saleable
     * and to update its saleability status in the database.
     * 
     * 	- `event.getId()` returns the unique identifier of the event.
     * 	- `productRepository.exists(event.getId())` checks if a product with the matching
     * ID exists in the repository.
     * 	- `product = productRepository.findOne(event.getId())` retrieves the product
     * associated with the ID from the repository.
     * 	- `if (product.isSaleable())` checks if the product is saleable, based on its
     * current state.
     * 	- `product.setSaleable(false)` updates the saleability status of the product to
     * false.
     * 	- `productRepository.save(product)` saves the updated product in the repository.
     */
    @EventHandler
    public void handle(ProductUnsaleableEvent event) {
        LOG.info("ProductUnsaleableEvent: [{}]", event.getId());

        if (productRepository.exists(event.getId())) {
            Product product = productRepository.findOne(event.getId());
            if (product.isSaleable()) {
                product.setSaleable(false);
                productRepository.save(product);
            }
        }
    }

    /**
     * clears the view before starting an event replay.
     */
    public void beforeReplay() {
        LOG.info("Event Replay is about to START. Clearing the View...");
    }

    /**
     * logs an event to the system log with the message "Event Replay has FINISHED."
     */
    public void afterReplay() {
        LOG.info("Event Replay has FINISHED.");
    }

    /**
     * logs an error message to the log when event replay fails.
     * 
     * @param cause Throwable that caused the event replay to fail, providing additional
     * information about the error.
     * 
     * 	- The type of Throwable is specified as `Throwable`.
     * 	- The cause of the failure is reported to the error log using the `LOG.error()`
     * method with a message indicating that the event replay has failed.
     */
    public void onReplayFailed(Throwable cause) {
        LOG.error("Event Replay has FAILED.");
    }
}
