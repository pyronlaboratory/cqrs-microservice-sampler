package com.soagrowers.productcommand.commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * is a Java class that enables Axon to identify the appropriate aggregate to mark
 * as completed based on the Id field annotated with the TargetAggregateIdentifier
 * annotation. The class has a constructor and a getId() method for retrieving the
 * Id value.
 */
public class MarkProductAsSaleableCommand {

    /**
     * How does Axon know which Aggregate to Mark as completed? It uses
     * the TargetAggregateIdentifier annotation so that it can get the
     * correct one based on the Id in the annotated field.
     */
    @TargetAggregateIdentifier
    private final String id;

    /**
     * This constructor must set the Id field, otherwise it's unclear
     * to Axon which aggregate this command is intended for.
     *
     * @param id
     */
    public MarkProductAsSaleableCommand(String id) {
        this.id = id;
    }

    /**
     * returns the `id` field of a class, providing an easy way to access and retrieve
     * the value of this instance variable.
     * 
     * @returns a string representing the value of the `id` field.
     */
    public String getId() {
        return id;
    }
}
