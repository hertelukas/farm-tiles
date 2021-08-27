package com.lukas.tiles.model;

import com.lukas.tiles.model.building.Building;
import com.lukas.tiles.model.setup.FarmerColor;
import com.lukas.tiles.viewModel.game.FarmerObserver;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.Unmodifiable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Farmer implements Serializable, TurnBasedUpdatable {
    @Serial
    private static final long serialVersionUID = -4787293802369998854L;
    private final FarmerColor color;
    private final Money money;
    private final String name;
    private final List<Building> buildings;
    private final List<Long> moneyHistory;
    private transient ArrayList<FarmerObserver> observers;

    private boolean hasHeadquarter = false;

    public Farmer(Money money, String name, FarmerColor color) {
        observers = new ArrayList<>();
        buildings = new ArrayList<>();
        moneyHistory = new ArrayList<>();
        this.money = money;
        this.name = name;
        this.color = color;
    }

    public void subscribe(FarmerObserver observer) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(observer);
        observer.update(this);
    }

    private void promoteUpdate() {
        for (FarmerObserver observer : observers) {
            observer.update(this);
        }
    }

    public boolean buy(Money price) {
        if (money.subAmountIfPossible(price)) {
            promoteUpdate();
            return true;
        }
        return false;
    }

    public boolean buyBuilding(Building building) {
        if (buy(building.getPrice())) {
            buildings.add(building);
            promoteUpdate();
            return true;
        }
        return false;
    }

    public Money getMoney() {
        return money;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color.getColor();
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public boolean isHasHeadquarter() {
        return hasHeadquarter;
    }

    public void setHasHeadquarter(boolean hasHeadquarter) {
        this.hasHeadquarter = hasHeadquarter;
    }

    @Unmodifiable
    public List<Long> getMoneyHistory() {
        return Collections.unmodifiableList(moneyHistory);
    }

    @Override
    public void doTurnBasedUpdate() {
        for (Building building : buildings) {
            this.money.subAmount(building.getCost());
        }
        moneyHistory.add(money.getAmount());
        promoteUpdate();
    }

    public static Farmer generate(Money startMoney, FarmerColor color) {
        return new Farmer(startMoney, "Joe", color);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Farmer farmer = (Farmer) o;
        return color == farmer.color && Objects.equals(money, farmer.money) && Objects.equals(name, farmer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, money, name);
    }

    @Override
    public String toString() {
        return "Farmer{" +
                "color=" + color +
                ", money=" + money +
                ", name='" + name + '\'' +
                '}';
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        observers = new ArrayList<>();
    }
}
