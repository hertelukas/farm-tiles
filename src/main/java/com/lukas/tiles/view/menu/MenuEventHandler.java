package com.lukas.tiles.view.menu;

/**
 * Functional interface that handles a menu entry based on its index
 */
public interface MenuEventHandler {
    /**
     * @param index where the button press occurred
     */
    void handle(int index);
}
