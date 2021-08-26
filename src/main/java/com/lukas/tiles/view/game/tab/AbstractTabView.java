package com.lukas.tiles.view.game.tab;

import com.lukas.tiles.FarmTilesApplication;
import com.lukas.tiles.view.Style;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public abstract class AbstractTabView extends VBox {

    public AbstractTabView(StringProperty title) {
        this.getStylesheets().add(FarmTilesApplication.getMainStyle());

        Label label = new Label();
        label.textProperty().bind(title);
        label.getStyleClass().add("h1");
        this.getChildren().add(label);

        //Setup margins etc
        this.setSpacing(Style.getVSpacing());
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(Style.getPADDING());
        HBox.setHgrow(this, Priority.ALWAYS);

        initialize();
    }

    abstract void initialize();
}
