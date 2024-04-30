package com.soagrowers.productevents.events;


/**
 * is an extension of the AbstractEvent class and represents an event related to the
 * addition of a product. The class has a name field that holds the name of the product
 * added, which can be retrieved through the getName() method.
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
     * retrieves a string representing the name of an object.
     * 
     * @returns a string representing the value of the `name` field.
     */
    public String getName() {
        return name;
    }
}
