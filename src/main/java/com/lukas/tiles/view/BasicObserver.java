package com.lukas.tiles.view;

/**
 * A generic functional interface for implementing
 * the pull notification variant of the observer pattern
 */
public interface BasicObserver {
    /**
     * Should implement the handling of the changes occurred by an update of the observed object
     */
    void update();
}
