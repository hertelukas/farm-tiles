package com.lukas.tiles.model.setup;

import com.lukas.tiles.model.finance.UnmodifiableMoneyAccount;

/**
 * Default settings for different difficulties
 */
public enum Difficulty {
    Easy(new UnmodifiableMoneyAccount(100000), new UnmodifiableMoneyAccount(50000)),
    Medium(new UnmodifiableMoneyAccount(100000), new UnmodifiableMoneyAccount(10000)),
    Hard(new UnmodifiableMoneyAccount(50000), new UnmodifiableMoneyAccount(50000));

    private final UnmodifiableMoneyAccount playerStart;
    private final UnmodifiableMoneyAccount farmerStart;

    Difficulty(UnmodifiableMoneyAccount playerStart, UnmodifiableMoneyAccount farmerStart) {
        this.playerStart = playerStart;
        this.farmerStart = farmerStart;
    }

    /**
     * @return the amount a player gets at the start
     */
    public UnmodifiableMoneyAccount getPlayerStart() {
        return playerStart;
    }

    /**
     * @return the amount every NPC-farmer gets
     */
    public UnmodifiableMoneyAccount getFarmerStart() {
        return farmerStart;
    }
}
