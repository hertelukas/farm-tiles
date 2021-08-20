package com.lukas.tiles.view.game;

import com.lukas.tiles.FarmTilesApplication;
import com.lukas.tiles.model.Tile;
import com.lukas.tiles.view.Style;
import com.lukas.tiles.viewModel.game.TileViewModel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TileView extends VBox {
    private final TileViewModel tileViewModel;

    public TileView(Tile tile) {
        this.getStylesheets().add(FarmTilesApplication.getMainStyle());
        this.tileViewModel = new TileViewModel(tile);
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
    }
}
