package com.lukas.tiles.view.menu;

import javafx.beans.property.StringProperty;

/**
 * This represents one entry in a menu
 */
public class MenuEntry {
    private final MenuEventHandler menuEventHandler;
    private final StringProperty text;
    private int index;

    /**
     * @param menuEventHandler that handles the given entry based on its index
     * @param text             the name of the entry
     */
    public MenuEntry(MenuEventHandler menuEventHandler, StringProperty text) {
        this.menuEventHandler = menuEventHandler;
        this.text = text;
    }

    /**
     * @param index set the index where the item is placed in the menu
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return get the index where the item is placed in the menu
     */
    public int getIndex() {
        return index;
    }

    /**
     * @return the text of the entry as string
     */
    public String getText() {
        return text.get();
    }

    /**
     * @return the text of the entry as stringProperty
     */
    public StringProperty textProperty() {
        return text;
    }

    /**
     * @return get the event handler for this entry
     */
    public MenuEventHandler getMenuEventHandler() {
        return menuEventHandler;
    }
}
