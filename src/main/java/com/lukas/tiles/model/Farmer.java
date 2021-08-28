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

/**
 * This class represents the main actor of the game, a player is represented by a farmer
 */
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

    /**
     * @param money that a farmer has on creation
     * @param name  of the farmer
     * @param color that represents the farmer in the game, should be unique
     */
    public Farmer(Money money, String name, FarmerColor color) {
        observers = new ArrayList<>();
        buildings = new ArrayList<>();
        moneyHistory = new ArrayList<>();
        this.money = money;
        this.name = name;
        this.color = color;
    }

    /**
     * @param observer the object that wants to get notified about farmer updates
     */
    public void subscribe(FarmerObserver observer) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(observer);
        observer.update(this);
    }

    /**
     * Update all objects listening to the farmer
     */
    private void promoteUpdate() {
        for (FarmerObserver observer : observers) {
            observer.update(this);
        }
    }

    /**
     * All transactions should go via this method
     * Only succeeds when the farmer has enough money
     *
     * @param price of the object
     * @return whether transaction was successful
     */
    public boolean buy(Money price) {
        if (money.subAmountIfPossible(price)) {
            promoteUpdate();
            return true;
        }
        return false;
    }

    /**
     * Buys a building, subtracts the money and adds it to the owned buildings
     *
     * @param building the building to be bought
     * @return whether transaction was successful
     */
    public boolean buyBuilding(Building building) {
        if (buy(building.getPrice())) {
            buildings.add(building);
            promoteUpdate();
            return true;
        }
        return false;
    }

    /**
     * @return the money account of the farmer
     */

    // TODO: 8/28/21 make unmodifiable
    public Money getMoney() {
        return money;
    }

    /**
     * @return the name of the farmer
     */
    public String getName() {
        return name;
    }

    /**
     * @return the color representing the farmer
     */
    public Color getColor() {
        return color.getColor();
    }

    /**
     * @return a list of all owned buildings
     */
    public List<Building> getBuildings() {
        return buildings;
    }

    /**
     * @return if the farmer has a HQ
     */
    public boolean isHasHeadquarter() {
        return hasHeadquarter;
    }

    /**
     * set that the farmer has a HQ
     */
    public void setHeadquarter() {
        this.hasHeadquarter = true;
    }

    /**
     * @return a list of all money balances for every game update
     */
    @Unmodifiable
    public List<Long> getMoneyHistory() {
        return Collections.unmodifiableList(moneyHistory);
    }

    /**
     * updates the farmer information
     */
    @Override
    public void doTurnBasedUpdate() {
        for (Building building : buildings) {
            this.money.subAmount(building.getCost());
        }
        moneyHistory.add(money.getAmount());
        promoteUpdate();
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

    /**
     * @param startMoney the initial money of the farmer
     * @param color      that represents the farmer
     * @return the generated farmer
     */
    public static Farmer generate(Money startMoney, FarmerColor color) {
        return new Farmer(startMoney, "Joe", color);
    }
}
