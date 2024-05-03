package com.soagrowers.productquery.domain;


//import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * is an entity in Java, representing a product with an ID, name, and saleability
 * flag. The class has constructor and getter/setter methods for each field.
 */
@Entity
public class Product {

    @Id
    private String id;

    private String name;
    private boolean saleable;

    public Product() {
    }

    public Product(String id, String name, boolean saleable) {
        this.id = id;
        this.name = name;
        this.saleable = saleable;
    }

    /**
     * retrieves the `id` field value associated with an object instance and returns it
     * as a string.
     * 
     * @returns a string representing the `id` variable.
     */
    public String getId() {
        return id;
    }

    /**
     * sets the `id` field of an object to a given `String` value.
     * 
     * @param id value that will be assigned to the `id` field of the class instance being
     * modified by the function.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * returns a string representing the name of an object.
     * 
     * @returns a string representing the name of an object.
     */
    public String getName() {
        return name;
    }

    /**
     * sets the value of the class instance variable `name`.
     * 
     * @param name new value of the `name` field for the object being modified by the
     * `setName()` method.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * evaluates to `true` if the object being passed as a parameter is `saleable`, and
     * `false` otherwise.
     * 
     * @returns a boolean value indicating whether an item is saleable or not.
     */
    public boolean isSaleable() {
        return saleable;
    }

    /**
     * updates the `saleable` field of the current object by assigning a new boolean value.
     * 
     * @param saleable Boolean value that determines whether an object is saleable or
     * not, which is then stored in the `this.saleable` field.
     */
    public void setSaleable(boolean saleable) {
        this.saleable = saleable;
    }
}
