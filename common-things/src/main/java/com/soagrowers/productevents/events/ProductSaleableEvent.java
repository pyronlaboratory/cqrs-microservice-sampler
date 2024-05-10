package com.soagrowers.productevents.events;


/**
 * extends AbstractEvent and provides a superclass for representing events related
 * to product saleability.
 */
public class ProductSaleableEvent extends AbstractEvent {

    public ProductSaleableEvent() {
    }
    public ProductSaleableEvent(String id) {
        super(id);
    }
}
