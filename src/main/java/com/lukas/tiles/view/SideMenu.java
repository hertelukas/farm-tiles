package com.lukas.tiles.view;

import com.lukas.tiles.view.menu.MenuEntry;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * A multi-functional side-menu to use in game or in menus
 */
public class SideMenu extends VBox {

    private int menuIndex = 2;

    /**
     * Instantiates a new SideMenu with a title and a separator
     *
     * @param name the title of the menu
     */
    public SideMenu(StringProperty name) {
        Label title = new Label();
        title.textProperty().bind(name);
        title.getStyleClass().add("h3");
        Separator separator = new Separator();
        this.getChildren().addAll(title, separator);
        this.getStylesheets().add(Style.getMainStyle());


        //Setup layout
        Region space = new Region();
        VBox.setVgrow(space, Priority.ALWAYS);
        this.getChildren().add(space);

        this.setSpacing(Style.getVSpacing());
        this.setPadding(Style.getPADDING());
    }


    /**
     * @param entry that should be displayed in the menu
     */
    public void addMenuEntry(MenuEntry entry) {
        Button temp = new Button();
        temp.textProperty().bind(entry.textProperty());
        temp.setOnAction(event -> entry.getMenuEventHandler().handle(entry.getIndex()));
        this.getChildren().add(menuIndex, temp);
        entry.setIndex(menuIndex - 2);
        menuIndex++;
    }

    /**
     * Bottom entries are for more meta control - like closing or saving
     *
     * @param entry that should be displayed at the bottom
     */
    public void addBottomEntry(MenuEntry entry) {
        Button temp = new Button();
        temp.textProperty().bind(entry.textProperty());
        temp.setOnAction(event -> entry.getMenuEventHandler().handle(entry.getIndex()));
        this.getChildren().add(temp);
    }

}
