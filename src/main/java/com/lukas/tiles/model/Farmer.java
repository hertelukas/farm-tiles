package com.lukas.tiles.model;

public class Farmer {
    private final Money money;

    public Farmer(Money money) {
        this.money = money;
    }

    public Money getMoney() {
        return money;
    }

    public static Farmer generate(Money startMoney) {
        return new Farmer(startMoney);
    }
}
