package com.lukas.tiles.view;

import com.lukas.tiles.Config;
import com.lukas.tiles.FarmTilesApplication;
import com.lukas.tiles.SceneLoader;
import com.lukas.tiles.io.GameHandler;
import com.lukas.tiles.model.Game;
import com.lukas.tiles.viewModel.LoadViewModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class LoadView extends VBox {
    private final LoadViewModel loadViewModel;

    public LoadView(Config config) {
        this.getStylesheets().add(FarmTilesApplication.getMainStyle());
        loadViewModel = new LoadViewModel(config);
        initialize();
    }

    private void initialize() {
        //VBox setup
        this.setSpacing(Style.getVSpacing());
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(Style.getMARGIN());

        //Content setup
        Label label = new Label();
        label.textProperty().bind(loadViewModel.titlePropertyProperty());
        label.getStyleClass().add("h1");
        this.getChildren().add(label);

        this.getChildren().addAll(loadViewModel.generateButtons());

        Button button = new Button();
        button.textProperty().bind(loadViewModel.backProperty());
        button.setOnAction(loadViewModel::goBack);
        this.getChildren().add(button);
    }
}

