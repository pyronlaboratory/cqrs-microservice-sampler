package com.soagrowers.productevents.events;

import java.io.Serializable;

/**
 * is an abstract class in Java that provides a base implementation for events. It
 * has a private field for storing an identifier, known as id, and three methods: the
 * constructor, getId(), and the constructor. These methods allow for the creation
 * of instances of the class with different initial values for the id field.
 */
public abstract class AbstractEvent implements Serializable {

    private String id;

    public AbstractEvent() {}

    public AbstractEvent(String id) {
        this.id = id;
    }

    /**
     * retrieves the `id` field from an object and returns it as a string.
     * 
     * @returns a string representing the value of the `id` variable.
     */
    public String getId() {
        return id;
    }
}
