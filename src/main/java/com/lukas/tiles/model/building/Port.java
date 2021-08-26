package com.lukas.tiles.model.building;

import com.lukas.tiles.model.Money;
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

    public Port() {
        super(new Money(PRICE), BUILD_TIME);
    }

    public static Money price() {
        return new Money(PRICE);
    }

    public static int buildTime() {
        return BUILD_TIME;
    }


    @Override
    public Money getCost() {
        return new Money(2000); //Hardcoded $20 expenses
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
