package com.lukas.tiles.view.game;

import com.lukas.tiles.model.Game;
import com.lukas.tiles.model.setup.Setup;
import com.lukas.tiles.model.Tile;
import com.lukas.tiles.view.BasicObserver;
import com.lukas.tiles.view.game.tab.TabView;
import com.lukas.tiles.viewModel.game.GameViewModel;
import com.lukas.tiles.viewModel.game.Hexagon;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameView extends BorderPane implements BasicObserver {

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
        gameViewModel.subscribe(this);
        Hexagon.bindWidth(this.widthProperty());

        this.setBottom(new BottomBarView(gameViewModel.getGame().getFarmers().get(0), gameViewModel.getGameScheduler()));
        this.setCenter(mapView);

        // FIXME: 8/26/21 Remove event handler when game gets closed
        Stage.getWindows().get(0).addEventHandler(KeyEvent.KEY_PRESSED, gameViewModel::handleKey);

        mapView.setOnSelect(this::showTile);
    }

    public void showTile(Tile tile) {
        if (tile == null) {
            this.setRight(null);
        } else {
            this.setRight(new TileView(tile, gameViewModel.getGame()));
        }
    }

    private void toggleTab(boolean show) {
        if (show) {
            this.setCenter(new TabView(this, gameViewModel));
        } else {
            this.setCenter(mapView);
        }
    }

    @Override
    public void update() {
        toggleTab(gameViewModel.isShowingTab());
    }
}
