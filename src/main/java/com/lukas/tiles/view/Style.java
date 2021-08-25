package com.lukas.tiles.view;

import javafx.geometry.Insets;

public class Style {
    private final static double V_SPACING = 20;
    private final static double H_SPACING = 40;
    private final static Insets PADDING = new Insets(50);
    private final static Insets SMALL_PADDING = new Insets(20);

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
}
