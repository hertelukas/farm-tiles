package com.lukas.tiles.model.building;

import com.lukas.tiles.model.Money;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;

public class Headquarter extends Building {

    @Serial
    private static final long serialVersionUID = 5078330346355501148L;
    private final static long PRICE = 50000; //$500.00
    private final static int BUILD_TIME = 5;
    private final static long MAINTENANCE_COST = 10000; //$100.00

    public Headquarter() {
        super(new Money(PRICE), BUILD_TIME);
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

    //The HQ always costs $100
    @Override
    public long getCost() {
        return MAINTENANCE_COST;
    }

    @Override
    public @NotNull Parent getDescription() {
        if (!isFinished()) {
            return progressBar();
        }
        VBox result = new VBox();
        Label label = new Label(getClass().getSimpleName());
        result.getChildren().add(label);
        return result;    }
}
