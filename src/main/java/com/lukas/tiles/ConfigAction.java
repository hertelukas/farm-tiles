package com.lukas.tiles;

/**
 * Functional interface of configuration changes that will be executed on save
 */
public interface ConfigAction {
    void apply();
}
