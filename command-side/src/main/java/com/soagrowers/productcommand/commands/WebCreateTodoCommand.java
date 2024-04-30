package com.soagrowers.productcommand.commands;

/**
 * is a Java object that represents a command for creating a new to-do item. It has
 * two private fields: `id` and `description`, which are set in the constructor when
 * the command is created. The `getId()` and `getDescription()` methods return the
 * values of these fields.
 */
public class WebCreateTodoCommand {

    private final String id;
    private final String description;

    public WebCreateTodoCommand(String id, String description) {
        this.id = id;
        this.description = description;
    }

    /**
     * returns the `id` field of an object.
     * 
     * @returns a string representation of the `id` variable.
     */
    public String getId() {
        return id;
    }

    /**
     * returns a string representing the description associated with an object.
     * 
     * @returns a string representing the function's description.
     */
    public String getDescription() {
        return description;
    }
}
