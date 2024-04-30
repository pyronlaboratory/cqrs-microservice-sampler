package com.soagrowers.productcommand.commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * Created by Ben on 07/08/2015.
 */
public class MarkProductAsSaleableCommand {

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

    public String getId() {
        return id;
    }
}
