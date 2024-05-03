package com.soagrowers.productcommand.commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * is a Axon Framework command that sets an aggregate identifier, allowing it to
 * target a specific aggregation root. The class has a constructor that takes an id
 * parameter and a method to retrieve the id.
 */
public class MarkProductAsUnsaleableCommand {

    @TargetAggregateIdentifier
    private final String id;

    /**
     * This constructor must set the Id field, otherwise it's unclear
     * to Axon which aggregate this command is intended for.
     *
     * @param id
     */
    public MarkProductAsUnsaleableCommand(String id) {
        this.id = id;
    }

    /**
     * retrieves the value of a variable `id`.
     * 
     * @returns a string representing the value of the `id` variable.
     */
    public String getId() {
        return id;
    }
}
