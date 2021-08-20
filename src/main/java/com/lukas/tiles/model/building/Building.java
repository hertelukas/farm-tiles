package com.lukas.tiles.model.building;

import com.lukas.tiles.model.Money;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public abstract class Building implements Serializable {
    @Serial
    private static final long serialVersionUID = -2101702233736096724L;
    private final Money price;
    private final int buildTime;

    protected Building(Money price, int buildTime_seconds) {
        this.price = price;
        this.buildTime = buildTime_seconds;
    }

    public Money getPrice() {
        return price;
    }

    public int getBuildTime() {
        return buildTime;
    }

    public VBox getDescription() {
        VBox result = new VBox();
        result.getChildren().add(new Label(getClass().getName()));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return buildTime == building.buildTime && Objects.equals(price, building.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, buildTime);
    }
}
