package com.lukas.tiles.model.building;

import com.lukas.tiles.model.Money;
import com.lukas.tiles.model.Tile;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;

/**
 * The most basic building in the game
 */
public class Farm extends Building {
    @Serial
    private static final long serialVersionUID = 6538625479050341086L;
    private final static long PRICE = 40000; //$400.00
    private final static int BUILD_TIME = 60;
    private final static long MAINTENANCE_COST = 10000; //$100.00


    public Farm(Tile tile) {
        super(new Money(PRICE), BUILD_TIME, tile);
    }

    /**
     * @return the price of the building
     */
    public static @NotNull Money price() {
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
    public static @NotNull Money getMaintenance() {
        return new Money(MAINTENANCE_COST);
    }

    /**
     * @return the current maintenance cost of the building, can be negative if it makes profit
     */
    @Override
    public long getCost() {
        if (isFinished()) {
            return -10000; //Hardcoded $100 earnings
        }
        return 0;
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
