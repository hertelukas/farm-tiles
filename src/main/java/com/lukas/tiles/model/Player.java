package com.lukas.tiles.model;

import com.lukas.tiles.viewModel.PlayerObserver;

import java.util.ArrayList;
import java.util.List;

public class Player extends Farmer {

    private final List<PlayerObserver> playerObservers;

    public Player(Money startMoney) {
        super(startMoney);
        playerObservers = new ArrayList<>();
    }

    public void subscribe(PlayerObserver playerObserver) {
        this.playerObservers.add(playerObserver);
        playerObserver.update(this);
    }

    private void updatePlayerObservers() {
        for (PlayerObserver playerObserver : playerObservers) {
            playerObserver.update(this);
        }
    }
}
