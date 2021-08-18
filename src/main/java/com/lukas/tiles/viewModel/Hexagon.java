package com.lukas.tiles.viewModel;

public class Hexagon {
    private final double[] x;
    private final double[] y;

    public Hexagon(double[] x, double[] y) {
        if (x.length != 6 || y.length != 6) {
            throw new IllegalArgumentException("Hexagon has to have 6 edges.");
        }
        this.x = x;
        this.y = y;
    }

    public double[] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }
}
