package com.lukas.tiles.view.game.tab;

import com.lukas.tiles.FarmTilesApplication;
import com.lukas.tiles.view.Style;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Abstraction of every view in the tab view menu
 * Sets only a title and loads the stylesheet
 */
public abstract class AbstractTabView extends VBox {

    public AbstractTabView(StringProperty title) {
        this.getStylesheets().add(Style.getMainStyle());

        Label label = new Label();
        label.textProperty().bind(title);
        label.getStyleClass().add("h1");
        this.getChildren().add(label);

        //Setup margins etc
        this.setSpacing(Style.getVSpacing());
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(Style.getPADDING());
        HBox.setHgrow(this, Priority.ALWAYS);
    }

    /**
     * This method has to be called by every child.
     * It is not possible to call this by the constructor,
     * because children might have to assign final fields in theirs
     * after calling super()
     */
    abstract void initialize();
}
