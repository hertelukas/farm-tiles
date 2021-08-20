package com.lukas.tiles.viewModel.game;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Hexagon {
    private final static double dis = Math.sqrt(3) / 2;
    private final static DoubleProperty width = new SimpleDoubleProperty();
    private final static double SCALE = 0.05;

    private final double[] x;
    private final double[] y;

    public Hexagon(int xCoordinate, int yCoordinate, double scale, double xOffset, double yOffset) {
        x = new double[]{0.5, 0, 0.5, 1.5, 2, 1.5};
        y = new double[]{0, dis, 2 * dis, 2 * dis, dis, 0};

        for (int i = 0; i < 6; i++) {
            x[i] = (x[i] + xCoordinate * 1.5) * scale * getRelativeScale() + xOffset;

            //Even row
            if (xCoordinate % 2 == 0) {
                y[i] = (y[i] + yCoordinate * 2 * dis) * scale * getRelativeScale() + yOffset;
            }
            //Uneven row
            else {
                y[i] = y[i] = (y[i] + yCoordinate * 2 * dis + dis) * scale * getRelativeScale() + yOffset;
            }
        }
    }

    public static void bindWidth(ReadOnlyDoubleProperty width) {
        Hexagon.width.bind(width);
    }

    public static double getRelativeScale() {
        return width.doubleValue() * SCALE;
    }

    // TODO: 8/19/21 not accurate, clicks in the top corner are getting ignored
    public boolean isInside(double x, double y) {
        if (this.y[0] > y) return false;
        if (this.y[2] < y) return false;
        if (this.x[0] > x) return false;
        if (this.x[3] < x) return false;
        return true;
    }

    public double[] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }
}
