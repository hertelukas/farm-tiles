package com.lukas.tiles.view;

import com.lukas.tiles.viewModel.GameViewModel;
import javafx.scene.layout.VBox;

public class GameView extends VBox {

    private final GameViewModel gameViewModel;

    public GameView() {
        gameViewModel = new GameViewModel();
        this.getChildren().add(new BottomBarView());
    }
}
