package com.lukas.tiles.view;

import javafx.beans.property.StringProperty;

public class MenuEntry {
    private final MenuEventHandler menuEventHandler;
    private final StringProperty text;
    private int index;

    public MenuEntry(MenuEventHandler menuEventHandler, StringProperty text) {
        this.menuEventHandler = menuEventHandler;
        this.text = text;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getText() {
        return text.get();
    }

    public StringProperty textProperty() {
        return text;
    }

    public MenuEventHandler getMenuEventHandler() {
        return menuEventHandler;
    }
}
