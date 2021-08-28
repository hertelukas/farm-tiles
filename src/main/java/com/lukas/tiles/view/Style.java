package com.lukas.tiles.view;

import javafx.geometry.Insets;
import javafx.scene.paint.Color;

public class Style {
    private final static double V_SPACING = 20;
    private final static double H_SPACING = 40;
    private final static Insets PADDING = new Insets(50);
    private final static Insets SMALL_PADDING = new Insets(20);
    private final static String MAIN_STYLE = "/fxml/stylesheets/main.css";
    private final static String CHART_STYLE = "/fxml/stylesheets/chart.css";


    public static double getVSpacing() {
        return V_SPACING;
    }

    public static double getHSpacing() {
        return H_SPACING;
    }

    public static Insets getPADDING() {
        return PADDING;
    }

    public static Insets getSmallPadding() {
        return SMALL_PADDING;
    }

    public static String getChartStyle() {
        return CHART_STYLE;
    }

    public static String getMainStyle() {
        return MAIN_STYLE;
    }

    public static String convertToWebString(Color c) {
        int r = (int) (255 * c.getRed());
        int g = (int) (255 * c.getGreen());
        int b = (int) (255 * c.getBlue());
        return String.format("#%02x%02x%02x", r, g, b);
    }

}
