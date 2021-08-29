package com.lukas.tiles.model;

/**
 * All objects that can be updated with every game update
 */
public interface TurnBasedUpdatable {
    /**
     * All classes implementing this interface update themselves after every game update.
     */
    void doTurnBasedUpdate();
}
