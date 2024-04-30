package com.soagrowers.productcommand.commands;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * is a Java class that defines a command to add a product to an aggregate identifier.
 * The class has two instance variables: id and name, which are used to identify the
 * product being added. The class also has two methods: getId() and getName(), which
 * return the value of the id and name respectively.
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
     * retrieves the `id` field of an object and returns its value as a string.
     * 
     * @returns a string representing the value of the `id` field.
     */
    public String getId() {
        return id;
    }

    /**
     * retrieves a string variable named 'name'.
     * 
     * @returns a string representing the name of the object.
     */
    public String getName() {
        return name;
    }
}
