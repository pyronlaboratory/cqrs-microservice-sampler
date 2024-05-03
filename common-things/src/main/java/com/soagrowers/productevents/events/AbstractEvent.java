package com.soagrowers.productevents.events;

import java.io.Serializable;

/**
 * is an abstract class that serves as a base for other events in the product events
 * package. It provides an identifier (id) field and a getId() method for retrieving
 * the identifier. The class also has a constructor that takes an id parameter for
 * customizing the event instance.
 */
public abstract class AbstractEvent implements Serializable {

    private String id;

    public AbstractEvent() {}

    public AbstractEvent(String id) {
        this.id = id;
    }

    /**
     * returns the `id` field value associated with a given object instance.
     * 
     * @returns a string representing the value of the `id` variable.
     */
    public String getId() {
        return id;
    }
}
