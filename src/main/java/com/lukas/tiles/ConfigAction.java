package com.lukas.tiles;

/**
 * Functional interface of configuration changes that will be executed on save
 */
public interface ConfigAction {
    /**
     * Implementation should apply the configuration change to the config object.
     */
    void apply();
}
