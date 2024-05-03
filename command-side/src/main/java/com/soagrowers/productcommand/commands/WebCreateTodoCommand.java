package com.soagrowers.productcommand.commands;

/**
 * is a Java object that represents a command for creating a new todo item. It has
 * two instance variables: id and description, which are used to store the unique
 * identifier and textual description of the item, respectively. The class also
 * includes a constructor and two method implementations for retrieving the id and
 * description of the todo item.
 */
public class WebCreateTodoCommand {

    private final String id;
    private final String description;

    public WebCreateTodoCommand(String id, String description) {
        this.id = id;
        this.description = description;
    }

    /**
     * retrieves the value of a field named 'id'.
     * 
     * @returns a string representing the value of the `id` field.
     */
    public String getId() {
        return id;
    }

    /**
     * retrieves a string representing a description associated with an object.
     * 
     * @returns a string containing the value of the `description` field.
     */
    public String getDescription() {
        return description;
    }
}
