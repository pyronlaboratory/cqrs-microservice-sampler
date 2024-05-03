package com.soagrowers.productquery.domain;


//import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by ben on 07/10/15.
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

    public String getId() {
        return id;
    }

    /**
     * Sets the value of its class member variable `id`.
     * 
     * @param id new value of the class instance variable `id`.
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    /**
     * Sets the value of the class's `name` field to the input parameter passed by the caller.
     * 
     * @param name new value of the object's `name` field.
     */
    public void setName(String name) {
        this.name = name;
    }

    public boolean isSaleable() {
        return saleable;
    }

    /**
     * Sets the instance field `saleable` to the provided boolean value, ensuring the
     * field's state is consistent with the passed argument.
     * 
     * @param saleable boolean value of whether the object is saleable or not, and by
     * setting it to `true`, the method marks the object as saleable.
     */
    public void setSaleable(boolean saleable) {
        this.saleable = saleable;
    }
}
