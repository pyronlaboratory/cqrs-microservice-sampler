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
 * Created by Ben on 10/08/2015.
 */
@Component
public class ProductViewEventHandler implements ReplayAware {

    private static final Logger LOG = LoggerFactory.getLogger(ProductViewEventHandler.class);

    @Autowired
    private ProductRepository productRepository;

    /**
     * Listens for a `ProductAddedEvent` and saves the new product to the repository when
     * triggered.
     * 
     * @param event `ProductAddedEvent` object that contains information about the added
     * product, including its ID and name.
     */
    @EventHandler
    public void handle(ProductAddedEvent event) {
        LOG.info("ProductAddedEvent: [{}] '{}'", event.getId(), event.getName());
        productRepository.save(new Product(event.getId(), event.getName(), false));
    }

    /**
     * Updates the saleability status of a product in the repository when a ProductSaleableEvent
     * is triggered.
     * 
     * @param event ProductSaleableEvent object that contains information about a saleable
     * event, such as the event ID and product ID.
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
     * Is called when a product becomes unsaleable. It checks if the product exists, and
     * if it's saleable. If it is, it sets the saleable field to false and saves it to
     * the repository.
     * 
     * @param event ProductUnsaleableEvent object that contains information about an
     * unsaleable product, which is used to determine whether the product is still saleable
     * and to update its saleability status accordingly.
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

    public void beforeReplay() {
        LOG.info("Event Replay is about to START. Clearing the View...");
    }

    public void afterReplay() {
        LOG.info("Event Replay has FINISHED.");
    }

    /**
     * Logs an error message to the log when replaying an event fails.
     * 
     * @param cause Throwable object that caused the replay to fail.
     */
    public void onReplayFailed(Throwable cause) {
        LOG.error("Event Replay has FAILED.");
    }
}
