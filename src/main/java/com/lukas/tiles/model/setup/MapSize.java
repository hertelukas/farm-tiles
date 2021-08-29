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
     * @return The width of the map
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return The height of the map
     */
    public int getHeight() {
        return height;
    }

    /**
     * This method should be called for getting the default map size.
     *
     * @return The default map size
     */
    public static MapSize getDefault() {
        return Medium;
    }

}
