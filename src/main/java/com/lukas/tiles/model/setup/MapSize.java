package com.lukas.tiles.model.setup;

/**
 * Different predefined map sizes
 */
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

    /**
     * @return the width of the map
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height of the map
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the default map size
     */
    public static MapSize getDefault() {
        return Medium;
    }

}
