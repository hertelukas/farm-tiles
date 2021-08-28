package com.lukas.tiles.model.building;

import com.lukas.tiles.model.Money;
import com.lukas.tiles.model.Tile;
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

    public Headquarter(Tile tile) {
        super(new Money(PRICE), BUILD_TIME, tile);
    }

    /**
     * @return the price of the building
     */
    public static Money price() {
        return new Money(PRICE);
    }

    /**
     * @return the time it takes to build in seconds
     */
    public static int buildTime() {
        return BUILD_TIME;
    }

    /**
     * @return the base maintenance cost of this building
     */
    public static Money getMaintenance() {
        return new Money(MAINTENANCE_COST);
    }

    /**
     * The HQ costs always a fixed amount
     *
     * @return the current maintenance cost of the building, can be negative if it makes profit
     */
    @Override
    public long getCost() {
        return MAINTENANCE_COST;
    }

    /**
     * @return a JavaFX Parent that shows interaction with the building and gets shown in the tile view
     */
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
