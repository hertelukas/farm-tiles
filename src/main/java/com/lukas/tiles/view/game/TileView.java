package com.lukas.tiles.view.game;

import com.lukas.tiles.FarmTilesApplication;
import com.lukas.tiles.model.Game;
import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.building.Farm;
import com.lukas.tiles.view.Style;
import com.lukas.tiles.viewModel.game.TileViewModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TileView extends VBox {
    private final TileViewModel tileViewModel;

    public TileView(Tile tile, Game game) {
        this.getStylesheets().add(FarmTilesApplication.getMainStyle());
        this.tileViewModel = new TileViewModel(tile, game);
        initialize();
    }

    private void initialize() {
        //VBox setup
        this.setSpacing(Style.getVSpacing());
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(Style.getMARGIN());

        //Content setup
        Label label = new Label();
        label.textProperty().bind(tileViewModel.getName());
        label.getStyleClass().add("h1");
        this.getChildren().add(label);

        Label feedback = new Label();

        if (tileViewModel.hasBuilding()) {
            this.getChildren().add(tileViewModel.getBuilding().getDescription());
        } else {
            Button button = new Button("Buy farm");
            button.setOnAction(e -> {
                if (!tileViewModel.buyBuilding(new Farm())) {
                    feedback.setVisible(true);
                    feedback.setText("Failed to buy farm");
                } else {
                    this.getChildren().remove(button);
                }
            });
            this.getChildren().add(button);
        }


        feedback.setVisible(false);

        this.getChildren().add(feedback);
    }
}
