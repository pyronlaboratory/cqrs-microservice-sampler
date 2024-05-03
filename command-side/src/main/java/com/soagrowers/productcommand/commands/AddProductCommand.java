package com.soagrowers.productcommand.commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * is a Java class that represents an instruction to add a product to an aggregate
 * entity. The class has two instance fields: `id` and `name`, which represent the
 * unique identifier and name of the product, respectively. The class also has two
 * methods: `getId()` and `getName()`, which return the value of the `id` and `name`
 * fields, respectively.
 */
public class AddProductCommand {

    @TargetAggregateIdentifier
    private final String id;
    private final String name;

    public AddProductCommand(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * returns the `id` field of an object.
     * 
     * @returns a string representing the value of the `id` variable.
     */
    public String getId() {
        return id;
    }

    /**
     * retrieves the name of an object and returns it as a string.
     * 
     * @returns a string representation of the variable `name`.
     */
    public String getName() {
        return name;
    }
}
