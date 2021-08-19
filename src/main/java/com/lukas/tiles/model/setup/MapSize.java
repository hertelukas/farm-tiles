package com.lukas.tiles.model.setup;

public enum MapSize {
    Small(16, 9),
    Medium(32, 18),
    Large(48, 27),
    Huge(64, 36);

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
