package com.lukas.tiles.viewModel.game;

import com.lukas.tiles.model.Farmer;

/**
 * Should be implemented by all ViewModels that have to keep track of a farmer
 * Implements the push notification variant of the observer pattern
 */
public interface FarmerObserver {
    void update(Farmer player);
}
