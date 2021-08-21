package com.lukas.tiles.view.game;

import com.lukas.tiles.FarmTilesApplication;
import com.lukas.tiles.model.Game;
import com.lukas.tiles.viewModel.game.EscapeMenuViewModel;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EscapeMenu extends Stage {

    private EscapeMenuViewModel escapeMenuViewModel;

    public EscapeMenu(Game game) {
        escapeMenuViewModel = new EscapeMenuViewModel(game);
        this.initModality(Modality.APPLICATION_MODAL);
        this.initStyle(StageStyle.UNDECORATED);
        this.setMinWidth(500);
        this.setMinHeight(800);

        Scene scene = new Scene(generateContent());
        scene.getStylesheets().add(FarmTilesApplication.getMainStyle());

        this.setScene(scene);
    }

    private VBox generateContent() {
        VBox result = new VBox();

        Label label = new Label("Menu");
        label.getStyleClass().add("h2");

        result.getChildren().add(label);
        return result;
    }
}
