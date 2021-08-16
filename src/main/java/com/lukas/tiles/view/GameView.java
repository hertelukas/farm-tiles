package com.lukas.tiles.view;

import com.lukas.tiles.viewModel.GameViewModel;
import javafx.scene.layout.BorderPane;

public class GameView extends BorderPane {

    private final GameViewModel gameViewModel;

    public GameView() {
        gameViewModel = new GameViewModel();
        this.setBottom(new BottomBarView());
    }
}
