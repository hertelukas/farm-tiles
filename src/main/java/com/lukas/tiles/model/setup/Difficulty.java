package com.lukas.tiles.model.setup;

import com.lukas.tiles.model.Money;

public enum Difficulty {
    Easy(new Money(50000), new Money(50000)),
    Medium(new Money(10000), new Money(50000)),
    Hard(new Money(1000), new Money(50000));

    private final Money playerStart;
    private final Money farmerStart;


    Difficulty(Money playerStart, Money farmerStart) {
        this.playerStart = playerStart;
        this.farmerStart = farmerStart;
    }

    public Money getPlayerStart() {
        return playerStart;
    }

    public Money getFarmerStart() {
        return farmerStart;
    }
}
