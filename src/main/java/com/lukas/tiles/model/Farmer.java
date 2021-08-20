package com.lukas.tiles.model;

import com.lukas.tiles.viewModel.game.FarmerObserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Farmer implements Serializable {
    @Serial
    private static final long serialVersionUID = -4787293802369998854L;
    private final Money money;
    private final String name;
    private transient ArrayList<FarmerObserver> observers;

    public Farmer(Money money, String name) {
        observers = new ArrayList<>();
        this.money = money;
        this.name = name;
    }

    public void subscribe(FarmerObserver observer) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(observer);
        observer.update(this);
    }

    private void updateFarmer() {
        for (FarmerObserver observer : observers) {
            observer.update(this);
        }
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
        return Objects.equals(money, farmer.money) && Objects.equals(name, farmer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(money, name);
    }

    @Override
    public String toString() {
        return "Farmer{" +
                "money=" + money +
                ", name='" + name + '\'' +
                '}';
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        observers = new ArrayList<>();
    }
}
