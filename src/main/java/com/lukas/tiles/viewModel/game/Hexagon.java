package com.lukas.tiles.viewModel.game;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Arrays;

/**
 * The mathematical representation of a tile
 * Holds all information about the size and layout of a tile
 * +------------------> y
 * |
 * |
 * |
 * |
 * v
 * x
 */
public class Hexagon {
    private final static double dis = Math.sqrt(3) / 2;
    private final static DoubleProperty width = new SimpleDoubleProperty();
    private final static double SCALE = 0.05;

    private final double[] x;
    private final double[] y;
    private boolean visible = true;

    /**
     * Creates a new tile and calculates its position and size
     *
     * @param xCoordinate      the x position of the tile on the map
     * @param yCoordinate      the y position of the tile on the map
     * @param scale            how much the hexagon is zoomed in to
     * @param xOffset          how much the map is moved up in pixels
     * @param yOffset          how much the map is moved to the left in pixels
     * @param forceCalculation whether the position should be calculated even when not displayed
     */
    public Hexagon(int xCoordinate, int yCoordinate, double scale, double xOffset, double yOffset, boolean forceCalculation) {
        x = new double[]{0.5, 0, 0.5, 1.5, 2, 1.5};
        y = new double[]{0, dis, 2 * dis, 2 * dis, dis, 0};

        if (!forceCalculation) {
            boolean yNotVisible = (y[3] + yCoordinate * 2 * dis + dis) * scale * getRelativeScale() + yOffset < 0 || (y[0] + yCoordinate * 2 * dis) * scale * getRelativeScale() + yOffset > width.doubleValue();
            boolean xNotVisible = (x[4] + xCoordinate * 1.5) * scale * getRelativeScale() + xOffset < 0;

            if (yNotVisible || xNotVisible) {
                visible = false;
                return;
            }
        }

        for (int i = 0; i < 6; i++) {
            x[i] = (x[i] + xCoordinate * 1.5) * scale * getRelativeScale() + xOffset;

            //Even row
            if (xCoordinate % 2 == 0) {
                y[i] = (y[i] + yCoordinate * 2 * dis) * scale * getRelativeScale() + yOffset;
            }
            //Uneven row
            else {
                y[i] = (y[i] + yCoordinate * 2 * dis + dis) * scale * getRelativeScale() + yOffset;
            }
        }
    }

    /**
     * Binds the width of the current viewport statically to the Hexagon class
     *
     * @param width the width that should be observed
     */
    public static void bindWidth(ReadOnlyDoubleProperty width) {
        Hexagon.width.bind(width);
    }

    // TODO: 8/29/21 check if this is correct

    /**
     * Use this method to get a factor the tile has to multiplied with to handle different resolutions.
     * This method allows the tiles to stay the same size, no matter how large the view port is
     *
     * @return the scale multiplied with the width of the view port
     */
    public static double getRelativeScale() {
        return width.doubleValue() * SCALE;
    }

    /**
     * @param x vertical position of the point to be checked
     * @param y horizontal position of point to be checked
     * @return whether the point is in the current hexagon
     */
    public boolean isInside(double x, double y) {
        if (!isVisible()) {
            return false;
        }
        return mightBeInIt(x, y) && (isInCenter(x, y) || isInTopLeft(x, y) || isInTopRight(x, y) || isInBottomLeft(x, y) || isInBottomRight(x, y));
    }

    private static final double tan = Math.tan(Math.toRadians(30));

    private boolean mightBeInIt(double x, double y) {
        return x > this.x[1] && x < this.x[4] && y > this.y[0] && y < this.y[2];
    }

    private boolean isInCenter(double x, double y) {
        return this.y[0] < y && this.x[0] < x && this.y[3] > y && this.x[3] > x;
    }

    private boolean isInTopLeft(double x, double y) {
        double cathetus = y - this.y[0];
        double opposite = tan * cathetus;

        return x > this.x[0] - opposite && x < this.x[0] && y < this.y[1];
    }

    private boolean isInTopRight(double x, double y) {
        double cathetus = this.y[2] - y;
        double opposite = tan * cathetus;

        return x > this.x[0] - opposite && x < this.x[0] && y > this.y[1];
    }

    private boolean isInBottomLeft(double x, double y) {
        double cathetus = y - this.y[0];
        double opposite = tan * cathetus;

        return x < this.x[5] + opposite && x > this.x[5] && y < this.y[1];
    }

    private boolean isInBottomRight(double x, double y) {
        double cathetus = this.y[2] - y;
        double opposite = tan * cathetus;

        return x < this.x[5] + opposite && x > this.x[5] && y > this.y[1];
    }

    /**
     * @return get the x coordinates of all points of the hexagon
     */
    public double[] getX() {
        return x;
    }

    /**
     * @return get the y coordinates of all points of the hexagon
     */
    public double[] getY() {
        return y;
    }

    /**
     * @return whether the current hexagon is visible on the screen
     */
    public boolean isVisible() {
        return visible;
    }

    @Override
    public String toString() {
        return "Hexagon{" +
                "x=" + Arrays.toString(x) +
                ", y=" + Arrays.toString(y) +
                ", visible=" + visible +
                '}';
    }
}
