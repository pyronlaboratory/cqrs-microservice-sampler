package com.soagrowers.productcommand.commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * Sets the ToDoItem to reopened.
 *
 * @author robertgolder
 */
public class MarkProductAsUnsaleableCommand {

    @TargetAggregateIdentifier
    private final String id;

    public MarkProductAsUnsaleableCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
