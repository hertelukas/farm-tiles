package com.lukas.tiles.model.setup;

public enum Difficulty {
    Easy(50000, 50000),
    Medium( 10000,  50000),
    Hard(1000, 50000);

    private final long playerStart;
    private final long farmerStart;


    Difficulty(long playerStart, long farmerStart) {
        this.playerStart = playerStart;
        this.farmerStart = farmerStart;
    }

    public long getPlayerStart() {
        return playerStart;
    }

    public long getFarmerStart() {
        return farmerStart;
    }
}
