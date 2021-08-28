package com.lukas.tiles.model.setup;

/**
 * Default settings for different difficulties
 */
public enum Difficulty {
    Easy(50000, 50000),
    Medium(10000, 50000),
    Hard(1000, 50000);

    private final long playerStart;
    private final long farmerStart;


    Difficulty(long playerStart, long farmerStart) {
        this.playerStart = playerStart;
        this.farmerStart = farmerStart;
    }

    /**
     * @return the amount a player gets at the start
     */
    public long getPlayerStart() {
        return playerStart;
    }

    /**
     * @return the amount every NPC-farmer gets
     */
    public long getFarmerStart() {
        return farmerStart;
    }
}
