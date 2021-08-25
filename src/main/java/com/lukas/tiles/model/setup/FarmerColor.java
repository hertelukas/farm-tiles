package com.lukas.tiles.model.setup;

import javafx.scene.paint.Color;

public enum FarmerColor {
    Pink(Color.PINK),
    Blue(Color.LIGHTBLUE),
    Cyan(Color.CYAN);

    private final Color color;

    FarmerColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public static FarmerColor getDefault() {
        return Blue;
    }
}
