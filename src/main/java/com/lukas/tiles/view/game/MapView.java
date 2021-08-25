package com.lukas.tiles.view.game;

import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.TileType;
import com.lukas.tiles.model.WorldMap;
import com.lukas.tiles.viewModel.game.Hexagon;
import com.lukas.tiles.viewModel.game.MapViewModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

public class MapView extends Pane implements MapViewModelObserver {

    private final MapViewModel mapViewModel;
    private final Canvas canvas;

    public MapView(WorldMap map) {
        mapViewModel = new MapViewModel(map);
        mapViewModel.subscribe(this);
        canvas = new Canvas();
        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());
        this.widthProperty().addListener((observable, oldValue, newValue) -> draw());
        this.getChildren().add(canvas);


        canvas.heightProperty().addListener(e -> mapViewModel.update());
        canvas.widthProperty().addListener(e -> mapViewModel.update());

        this.addEventHandler(ScrollEvent.SCROLL, e -> mapViewModel.handleScroll(e, this.getWidth(), this.getHeight()));
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> mapViewModel.handleDragged(e, this.getWidth(), this.getHeight()));
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, mapViewModel::mouseDown);
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, mapViewModel::mouseUp);
    }

    @Override
    public void update() {
        draw();
    }

    public void setOnSelect(TileSelectedHandler handler) {
        mapViewModel.setOnSelect(handler);
    }

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
                canvas.getGraphicsContext2D().setFill(tempTile.getColor());
                canvas.getGraphicsContext2D().fillPolygon(tempHex.getY(), tempHex.getX(), 6);
            }
        }
    }
}
