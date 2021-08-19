package com.lukas.tiles.view;

import javafx.geometry.Insets;

public class Style {
    private final static double V_SPACING = 20;
    private final static double H_SPACING = 40;
    private final static Insets MARGIN = new Insets(50);

    public static double getVSpacing() {
        return V_SPACING;
    }

    public static double getHSpacing() {
        return H_SPACING;
    }

    public static Insets getMARGIN() {
        return MARGIN;
    }
}
