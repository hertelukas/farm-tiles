package com.lukas.tiles.view;

import com.lukas.tiles.FarmTilesApplication;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class SideMenu extends VBox {

    private int menuIndex = 2;

    public SideMenu(StringProperty name) {
        Label title = new Label();
        title.textProperty().bind(name);
        title.getStyleClass().add("title");
        Separator separator = new Separator();
        this.getChildren().addAll(title, separator);
        this.getStylesheets().add(FarmTilesApplication.getMainStyle());


        //Setup layout
        Region space = new Region();
        VBox.setVgrow(space, Priority.ALWAYS);
        this.getChildren().add(space);

        this.setSpacing(20);
        HBox.setMargin(this, new Insets(50));
    }


    public void addMenuEntry(MenuEntry entry) {
        Button temp = new Button();
        temp.textProperty().bind(entry.textProperty());
        temp.setOnAction(event -> entry.getMenuEventHandler().handle(entry.getIndex()));
        this.getChildren().add(menuIndex, temp);
        entry.setIndex(menuIndex - 2);
        menuIndex++;
    }

    public void addBottomEntry(MenuEntry entry) {
        Button temp = new Button();
        temp.textProperty().bind(entry.textProperty());
        temp.setOnAction(event -> entry.getMenuEventHandler().handle(entry.getIndex()));
        this.getChildren().add(temp);
    }

}
