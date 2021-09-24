package com.lukas.tiles.view.game;

import com.lukas.tiles.SceneLoader;
import com.lukas.tiles.model.Game;
import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.setup.Setup;
import com.lukas.tiles.view.BasicObserver;
import com.lukas.tiles.view.Style;
import com.lukas.tiles.view.game.tab.TabView;
import com.lukas.tiles.viewModel.game.GameViewModel;
import com.lukas.tiles.viewModel.game.Hexagon;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

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
     * @param tile The title that should be displayed
     */
    public void showTile(Tile tile) {
        if (tile == null) {
            this.setRight(null);
        } else {
            this.setRight(new TileView(tile, gameViewModel.getGame()));
        }
    }

    /**
     * Shows a flash message, disappears after 3 seconds
     *
     * @param message     The message to be shown
     * @param messageType The type of the message. The color is set based on the type
     */
    public void showFlashMessage(String message, FlashMessageType messageType) {
        Stage window = new Stage();
        window.initStyle(StageStyle.TRANSPARENT);
        window.setHeight(20);
        window.setMinWidth(800);
        window.initOwner(SceneLoader.getInstance().getStage());

        VBox layout = new VBox(10, new Text(message));
        layout.setPadding(new Insets(3));
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(new Background(new BackgroundFill(messageType.getColor(), new CornerRadii(3), Insets.EMPTY)));

        Scene scene = new Scene(layout, Color.TRANSPARENT);
        scene.getStylesheets().add(Style.getMainStyle());

        window.setScene(scene);
        window.setAlwaysOnTop(true);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, evt -> {
                    window.show();
                    window.setY(50);
                    // TODO: 9/24/21 window isn't centered
                }, new KeyValue(layout.opacityProperty(), 0)),
                new KeyFrame(Duration.millis(200), new KeyValue(layout.opacityProperty(), 1.0)),
                new KeyFrame(Duration.millis(2600), new KeyValue(layout.opacityProperty(), 1.0)),
                new KeyFrame(Duration.millis(3000), new KeyValue(layout.opacityProperty(), 0.2)));
        timeline.setOnFinished(evt -> window.close());
        timeline.play();
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
