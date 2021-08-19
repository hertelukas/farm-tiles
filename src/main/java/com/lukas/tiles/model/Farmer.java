package com.lukas.tiles.model;

import java.util.Objects;

public class Farmer {
    private final Money money;
    private final String name;

    public Farmer(Money money, String name) {
        this.money = money;
        this.name = name;
    }

    public Money getMoney() {
        return money;
    }

    public String getName() {
        return name;
    }

    public static Farmer generate(Money startMoney) {
        return new Farmer(startMoney, "Joe");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Farmer farmer = (Farmer) o;

        if (!Objects.equals(money, farmer.money)) return false;
        return Objects.equals(name, farmer.name);
    }

    @Override
    public int hashCode() {
        int result = money != null ? money.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
