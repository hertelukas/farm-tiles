package com.lukas.tiles.view.game;

import com.lukas.tiles.FarmTilesApplication;
import com.lukas.tiles.model.Game;
import com.lukas.tiles.view.Style;
import com.lukas.tiles.viewModel.game.EscapeMenuViewModel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EscapeMenu extends Stage {

    private final EscapeMenuViewModel escapeMenuViewModel;

    public EscapeMenu(Game game) {
        escapeMenuViewModel = new EscapeMenuViewModel(game);
        this.initModality(Modality.APPLICATION_MODAL);
        this.initStyle(StageStyle.UNDECORATED);
        this.setMinWidth(500);
        this.setMinHeight(800);

        Scene scene = new Scene(generateContent());
        scene.getStylesheets().add(Style.getMainStyle());

        this.setScene(scene);
    }

    private VBox generateContent() {
        VBox result = new VBox();
        result.setAlignment(Pos.CENTER);
        result.setPadding(Style.getPADDING());
        result.setSpacing(Style.getVSpacing());

        Label label = new Label("Menu");
        label.getStyleClass().add("h2");


        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            escapeMenuViewModel.save();
            this.close();
        });

        Button saveAndMenuButton = new Button("Save & Menu");
        saveAndMenuButton.setOnAction(e -> {
            escapeMenuViewModel.saveAndMenu();
            this.close();
        });

        Button saveAndQuitButton = new Button("Save & Exit");
        saveAndQuitButton.setOnAction(e -> {
            escapeMenuViewModel.saveAndQuit();
            this.close();
        });

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> this.close());

        result.getChildren().addAll(label, saveButton, saveAndMenuButton, saveAndQuitButton, closeButton);
        return result;
    }
}
