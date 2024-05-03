package com.soagrowers.productevents.events;


/**
 * is an extension of the AbstractEvent class and represents an event related to the
 * addition of a product. The class has a name field that stores the product's name.
 */
public class ProductAddedEvent extends AbstractEvent {


    private String name;

    public ProductAddedEvent() {
    }

    public ProductAddedEvent(String id, String name) {
        super(id);
        this.name = name;
    }

    /**
     * retrieves the value of the `name` field of a class instance and returns it as a string.
     * 
     * @returns a string representing the value of the `name` variable.
     */
    public String getName() {
        return name;
    }
}
