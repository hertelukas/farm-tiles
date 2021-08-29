package com.lukas.tiles.model.building;

import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.finance.UnmodifiableMoneyAccount;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;

/**
 * The most basic building in the game
 */
public class Farm extends Building {
    @Serial
    private static final long serialVersionUID = 6538625479050341086L;
    private final static UnmodifiableMoneyAccount PRICE = new UnmodifiableMoneyAccount(40000); //$400.00
    private final static int BUILD_TIME = 60;
    private final static UnmodifiableMoneyAccount MAINTENANCE_COST = new UnmodifiableMoneyAccount(10000); //$100.00

    /**
     * @param tile where the farm should stand on
     */
    public Farm(Tile tile) {
        super(PRICE, BUILD_TIME, tile);
    }

    /**
     * @return the price of the building
     */
    public static @NotNull UnmodifiableMoneyAccount price() {
        return PRICE;
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
    public static @NotNull UnmodifiableMoneyAccount getMaintenance() {
        return MAINTENANCE_COST;
    }

    /**
     * @return The current maintenance cost of the building, can be negative if it makes profit.
     */
    @Override
    public UnmodifiableMoneyAccount getCost() {
        if (isFinished()) {
            long amount = MAINTENANCE_COST.getAmount();
            // TODO: 8/29/21 make profit with the port and add it to the amount
            return new UnmodifiableMoneyAccount(amount);
        }
        return UnmodifiableMoneyAccount.EmptyAccount;
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
