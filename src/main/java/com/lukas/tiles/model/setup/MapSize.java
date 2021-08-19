package com.lukas.tiles.model.setup;

public enum MapSize {
    Small(32, 18),
    Medium(48, 27),
    Large(64, 36),
    Huge(96, 54);

    private final int width;
    private final int height;

    MapSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static MapSize getDefault() {
        return Medium;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
