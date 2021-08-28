package com.lukas.tiles.view;

import javafx.geometry.Insets;
import javafx.scene.paint.Color;

/**
 * A class with only static fields for setting up default style guidelines
 * - Should be implemented in CSS maybe
 */
public class Style {
    private final static double V_SPACING = 20;
    private final static double H_SPACING = 40;
    private final static Insets PADDING = new Insets(50);
    private final static Insets SMALL_PADDING = new Insets(20);
    private final static String MAIN_STYLE = "/fxml/stylesheets/main.css";
    private final static String CHART_STYLE = "/fxml/stylesheets/chart.css";

    /**
     * @return the default vertical spacing between UI components
     */
    public static double getVSpacing() {
        return V_SPACING;
    }

    /**
     * @return the default horizontal spacing between UI components
     */
    public static double getHSpacing() {
        return H_SPACING;
    }

    /**
     * @return the default padding
     */
    public static Insets getPADDING() {
        return PADDING;
    }

    /**
     * @return a smaller padding
     */
    public static Insets getSmallPadding() {
        return SMALL_PADDING;
    }

    /**
     * @return the location of the stylesheet for styling charts
     */
    public static String getChartStyle() {
        return CHART_STYLE;
    }

    /**
     * @return the location of the main stylesheet
     */
    public static String getMainStyle() {
        return MAIN_STYLE;
    }

    /**
     * @param c the color that should be converted to hex
     * @return a String representing the color in hex
     */
    public static String convertToWebString(Color c) {
        int r = (int) (255 * c.getRed());
        int g = (int) (255 * c.getGreen());
        int b = (int) (255 * c.getBlue());
        return String.format("#%02x%02x%02x", r, g, b);
    }

}
