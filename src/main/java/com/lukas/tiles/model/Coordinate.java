package com.lukas.tiles.model;

/**
 * Represents a coordinate on the map
 */
public record Coordinate(int x, int y) {
    public boolean isEven() {
        return (x % 2 == 0);
    }
}
