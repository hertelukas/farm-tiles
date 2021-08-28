package com.lukas.tiles.view.game;

import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.TileType;
import com.lukas.tiles.model.WorldMap;
import com.lukas.tiles.view.BasicObserver;
import com.lukas.tiles.viewModel.game.Hexagon;
import com.lukas.tiles.viewModel.game.MapViewModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

/**
 * This class is a visual representation of the map. It gets instantiated by the GameView.
 */
public class MapView extends Pane implements BasicObserver {

    private final MapViewModel mapViewModel;
    private final Canvas canvas;

    /**
     * @param map the map that should be displayed
     */
    public MapView(WorldMap map) {
        mapViewModel = new MapViewModel(map);
        mapViewModel.subscribe(this);
        canvas = new Canvas();
        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());
        this.widthProperty().addListener((observable, oldValue, newValue) -> draw());
        this.getChildren().add(canvas);

        //update the MapViewModel when the canvas size changes
        canvas.heightProperty().addListener(e -> mapViewModel.update());
        canvas.widthProperty().addListener(e -> mapViewModel.update());

        //Adding all mouse event handlers to move the map
        this.addEventHandler(ScrollEvent.SCROLL, e -> mapViewModel.handleScroll(e, this.getWidth(), this.getHeight()));
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> mapViewModel.handleDragged(e, this.getWidth(), this.getHeight()));
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, mapViewModel::mouseDown);
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, mapViewModel::mouseUp);
    }

    /**
     * Is called by the MapViewModel to notify about updates.
     * Redraws the entire map based on the calculations in the MapViewModel
     */
    @Override
    public void update() {
        draw();
    }

    /**
     * @param handler that gets called when a tile gets selected. Can only hold one handler
     */
    public void setOnSelect(TileSelectedHandler handler) {
        mapViewModel.setOnSelect(handler);
    }

    /**
     * redraws the entire map
     */
    private void draw() {
        canvas.getGraphicsContext2D().setFill(TileType.Water.getColor());
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        int i = 0;

        while (i < mapViewModel.getHexagons().length && mapViewModel.getHexagons()[i].getX()[4] < 0) {
            i += mapViewModel.getWidth();
        }
        Hexagon tempHex;
        Tile tempTile;
        for (; i < mapViewModel.getHexagons().length; i++) {
            tempHex = mapViewModel.getHexagons()[i];
            //All future tiles are below
            if (tempHex.getX()[1] > this.getHeight()) {
                return;
            }
            if (tempHex.isVisible()) {
                tempTile = mapViewModel.getTiles()[i / mapViewModel.getWidth()][i % mapViewModel.getWidth()];
                canvas.getGraphicsContext2D().setFill(tempTile.getPaint());
                canvas.getGraphicsContext2D().fillPolygon(tempHex.getY(), tempHex.getX(), 6);
            }
        }
    }
}
