package com.soagrowers.productevents.events;


/**
 * extends AbstractEvent and provides a way to create an event object with either no
 * ID or a specific ID during its constructor, following the same pattern as the
 * superclass AbstractEvent.
 */
public class ProductSaleableEvent extends AbstractEvent {

    public ProductSaleableEvent() {
    }

    public ProductSaleableEvent(String id) {
        super(id);
    }
}
