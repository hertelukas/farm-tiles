package com.lukas.tiles.model.building;

import com.lukas.tiles.model.Money;
import com.lukas.tiles.model.Tile;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;

public class Port extends Building {
    @Serial
    private static final long serialVersionUID = -1233125229887741946L;
    private final static long PRICE = 5000; //$50.00
    private final static int BUILD_TIME = 20;
    private final static int MAINTENANCE_COST = 10000; //$100.00

    public Port(Tile tile) {
        super(new Money(PRICE), BUILD_TIME, tile);
    }

    public static Money price() {
        return new Money(PRICE);
    }

    public static int buildTime() {
        return BUILD_TIME;
    }

    public static Money getMaintenance() {
        return new Money(MAINTENANCE_COST);
    }


    @Override
    public long getCost() {
        if (isFinished()) {
            return 2000; //Hardcoded $20 expenses
        }
        return 0;
    }

    @Override
    public @NotNull Parent getDescription() {
        if (!isFinished()) {
            return progressBar();
        }
        VBox result = new VBox();
        Label label = new Label(getClass().getSimpleName());
        result.getChildren().add(label);
        return result;
    }
}
