package com.soagrowers.productevents.events;


/**
 * is a subclass of AbstractEvent with no fields and two constructors: a default
 * constructor and a constructor that takes a String ID as a parameter.
 */
public class ProductUnsaleableEvent extends AbstractEvent {

    public ProductUnsaleableEvent() {
    }

    public ProductUnsaleableEvent(String id) {
        super(id);
    }
}
