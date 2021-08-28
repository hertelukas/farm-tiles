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

/**
 * This class is the main holder for the game. It holds every component.
 * Furthermore, it automatically starts or continues a game.
 */
public class GameView extends BorderPane implements BasicObserver {

    private final GameViewModel gameViewModel;
    private final MapView mapView;

    /**
     * Instantiates a new game view based on the setup.
     * The game gets generated and saved correctly.
     *
     * @param setup the user generated setup which contains all game setup information
     */
    public GameView(Setup setup) {
        gameViewModel = new GameViewModel(setup);
        mapView = new MapView(gameViewModel.getGame().getMap());
        initialize();
    }

    /**
     * This constructor is used to load an existing game.
     * It builds all relations between objects and initializes them
     *
     * @param game the game that should be displayed
     */
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

    /**
     * Displays a given tile with their representation in a TileView on the right side.
     * If null is passed, no tile will be shown.
     *
     * @param tile that should be displayed
     */
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

    /**
     * Method that gets called by the gameViewModel if it gets updated.
     * Using the pull notification variant of the observer pattern.
     */
    @Override
    public void update() {
        toggleTab(gameViewModel.isShowingTab());
    }
}
