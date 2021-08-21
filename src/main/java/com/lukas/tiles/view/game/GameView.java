package com.lukas.tiles.view.game;

import com.lukas.tiles.model.Game;
import com.lukas.tiles.model.Setup;
import com.lukas.tiles.model.Tile;
import com.lukas.tiles.viewModel.game.GameViewModel;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class GameView extends BorderPane {

    private final GameViewModel gameViewModel;
    private final MapView mapView;

    public GameView(Setup setup) {
        gameViewModel = new GameViewModel(setup);
        mapView = new MapView(gameViewModel.getGame().getMap());
        initialize();
    }

    public GameView(Game game) {
        gameViewModel = new GameViewModel(game);
        mapView = new MapView(gameViewModel.getGame().getMap());
        initialize();
    }

    private void initialize() {
        this.setBottom(new BottomBarView(gameViewModel.getGame().getFarmers().get(0)));
        this.setCenter(mapView);

        this.setFocused(true);
        System.out.println(this.isFocused());
        this.addEventHandler(KeyEvent.KEY_PRESSED, gameViewModel::handleKey);

        mapView.setOnSelect(this::showTile);
    }

    public void showTile(Tile tile) {
        if (tile == null) {
            this.setRight(null);
        } else {
            this.setRight(new TileView(tile, gameViewModel.getGame()));
        }
    }
}
