package com.lukas.tiles.view;

import com.lukas.tiles.model.Setup;
import com.lukas.tiles.viewModel.GameViewModel;
import javafx.scene.layout.BorderPane;

public class GameView extends BorderPane {

    private final GameViewModel gameViewModel;

    public GameView(Setup setup) {
        gameViewModel = new GameViewModel(setup);
        this.setBottom(new BottomBarView());
    }
}
