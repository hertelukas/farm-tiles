package com.lukas.tiles.model;

public record Coordinate(int x, int y) {
    public boolean isEven() {
        return (x % 2 == 0);
    }
}
