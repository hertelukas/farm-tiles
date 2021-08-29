package com.lukas.tiles.model;

/**
 * Represents a coordinate on the map.
 */
public record Coordinate(int x, int y) {
    /**
     * @return Whether the row is even
     */
    public boolean isEven() {
        return (x % 2 == 0);
    }
}
