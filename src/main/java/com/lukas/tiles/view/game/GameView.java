package com.lukas.tiles.view.game;

import com.lukas.tiles.model.Game;
import com.lukas.tiles.model.Setup;
import com.lukas.tiles.viewModel.game.GameViewModel;
import javafx.scene.layout.BorderPane;

public class GameView extends BorderPane {

    private final GameViewModel gameViewModel;

    public GameView(Setup setup) {
        gameViewModel = new GameViewModel(setup);
        initialize();
    }

    public GameView(Game game) {
        gameViewModel = new GameViewModel(game);
        initialize();
    }

    private void initialize() {
        this.setBottom(new BottomBarView(gameViewModel.getGame().getFarmers().get(0)));
        this.setCenter(new MapView(gameViewModel.getGame().getMap()));
    }
}
