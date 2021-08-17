package com.lukas.tiles.view.settings;

import com.lukas.tiles.FarmTilesApplication;
import com.lukas.tiles.view.Style;
import com.lukas.tiles.viewModel.SettingsViewModel;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public abstract class AbstractSettingsView extends VBox {

    private final SettingsViewModel settingsViewModel;

    public AbstractSettingsView(SettingsViewModel settingsViewModel, StringProperty title) {
        this.settingsViewModel = settingsViewModel;
        this.getStylesheets().add(FarmTilesApplication.getMainStyle());

        //Add a titles
        Label label = new Label();
        label.textProperty().bind(title);
        label.getStyleClass().add("h1");
        this.getChildren().add(label);

        //Setup margins etc
        this.setSpacing(Style.getVSpacing());
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(Style.getMARGIN());
        HBox.setHgrow(this, Priority.ALWAYS);

        initialize();
    }

    abstract void initialize();

    public SettingsViewModel getSettingsViewModel() {
        return settingsViewModel;
    }
}
