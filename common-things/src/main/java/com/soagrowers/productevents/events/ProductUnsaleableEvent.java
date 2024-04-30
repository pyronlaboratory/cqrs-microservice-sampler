package com.soagrowers.productevents.events;


/**
 * is an extension of AbstractEvent Class and has no fields or methods beyond its
 * superclass's constructor and ID parameter.
 */
public class ProductUnsaleableEvent extends AbstractEvent {

    public ProductUnsaleableEvent() {
    }

    public ProductUnsaleableEvent(String id) {
        super(id);
    }
}
