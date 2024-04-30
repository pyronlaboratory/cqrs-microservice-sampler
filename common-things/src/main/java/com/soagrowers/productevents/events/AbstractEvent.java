package com.soagrowers.productevents.events;

import java.io.Serializable;

/**
 * is an abstract class in Java that defines an event with an unique identifier, known
 * as id. The class provides a base implementation for events and allows for further
 * extension and customization through derived classes.
 */
public abstract class AbstractEvent implements Serializable {

    private String id;

    public AbstractEvent() {}

    public AbstractEvent(String id) {
        this.id = id;
    }

    /**
     * returns the `id` field of a class instance.
     * 
     * @returns a string representing the value of the `id` field.
     */
    public String getId() {
        return id;
    }
}
