package com.lukas.tiles.view.game;

import javafx.scene.paint.Color;

public enum FlashMessageType {
    INFO(Color.WHITE),
    WARNING(Color.YELLOW),
    ERROR(Color.RED);

    private final Color color;

    FlashMessageType(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
