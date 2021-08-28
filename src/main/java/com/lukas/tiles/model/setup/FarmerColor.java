package com.lukas.tiles.model.setup;

import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

/**
 * Colors that a farmer can choose
 */
public enum FarmerColor {
    Pink(Color.PINK),
    Blue(Color.LIGHTBLUE),
    Red(Color.RED),
    Black(Color.BLACK),
    White(Color.WHITE),
    Cyan(Color.CYAN);

    private final Color color;

    FarmerColor(Color color) {
        this.color = color;
    }

    /**
     * @return a predefined color
     */
    public @NotNull Color getColor() {
        return color;
    }

    /**
     * @return the default color
     */
    public static @NotNull FarmerColor getDefault() {
        return Blue;
    }
}
