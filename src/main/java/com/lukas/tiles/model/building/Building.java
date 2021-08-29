package com.lukas.tiles.model.building;

import com.lukas.tiles.model.ScheduledObject;
import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.finance.UnmodifiableMoneyAccount;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.util.Objects;

/**
 * An abstraction for every building
 */
public abstract class Building extends ScheduledObject {
    @Serial
    private static final long serialVersionUID = -2101702233736096724L;
    private final UnmodifiableMoneyAccount price;
    private final Tile tile;

    /**
     * @param price             of the building
     * @param buildTime_seconds time it takes to build
     * @param tile              name of the building
     */
    protected Building(UnmodifiableMoneyAccount price, int buildTime_seconds, Tile tile) {
        super(buildTime_seconds);

        this.price = price;
        this.tile = tile;
    }

    /**
     * @return the price of the building
     */
    public UnmodifiableMoneyAccount getPrice() {
        return price;
    }

    /**
     * @return the current maintenance cost of the building, can be negative if it makes profit
     */
    public abstract UnmodifiableMoneyAccount getCost();

    /**
     * @return the tile where the building is standing on
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * @return a JavaFX Parent that shows interaction with the building and gets shown in the tile view
     */
    @NotNull
    public abstract Parent getDescription();

    /**
     * @return a JavaFX ProgressBar that shows the progress of the build process
     */
    @NotNull
    protected Parent progressBar() {
        VBox vBox = new VBox();
        ProgressBar pb = new ProgressBar();
        pb.progressProperty().bind(progressProperty());

        vBox.getChildren().add(pb);
        return vBox;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return Objects.equals(price, building.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
