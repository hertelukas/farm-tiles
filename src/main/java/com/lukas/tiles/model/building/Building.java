package com.lukas.tiles.model.building;

import com.lukas.tiles.model.Money;
import com.lukas.tiles.model.ScheduledObject;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.util.Objects;

public abstract class Building extends ScheduledObject {
    @Serial
    private static final long serialVersionUID = -2101702233736096724L;
    private final Money price;

    protected Building(Money price, int buildTime_seconds) {
        super(buildTime_seconds);

        this.price = price;
    }

    public Money getPrice() {
        return price;
    }

    @NotNull
    public abstract Parent getDescription();

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
