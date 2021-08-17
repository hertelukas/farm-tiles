package com.lukas.tiles.view;

import com.lukas.tiles.Config;
import com.lukas.tiles.FarmTilesApplication;
import com.lukas.tiles.viewModel.SetupViewModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SetupView extends VBox {

    private final SetupViewModel setupViewModel;

    public SetupView(Config config) {
        this.getStylesheets().add(FarmTilesApplication.getMainStyle());
        setupViewModel = new SetupViewModel(config);
        initialize();
    }

    private void initialize() {
        //VBox setup
        this.setSpacing(Style.getVSpacing());
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(Style.getMARGIN());

        //Content setup
        Label label = new Label();
        label.textProperty().bind(setupViewModel.titleProperty());
        label.getStyleClass().add("h1");
        this.getChildren().add(label);

        Button playButton = new Button();
        playButton.textProperty().bind(setupViewModel.playProperty());
        playButton.setOnAction(e -> setupViewModel.play());
        this.getChildren().add(playButton);
    }


}
