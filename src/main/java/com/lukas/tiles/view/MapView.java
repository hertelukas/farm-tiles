package com.lukas.tiles.view;

import com.lukas.tiles.model.WorldMap;
import com.lukas.tiles.model.Tile;
import com.lukas.tiles.viewModel.MapViewModel;
import com.lukas.tiles.viewModel.Hexagon;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;

import java.util.Map;

public class MapView extends Pane implements MapViewModelObserver {

    private final MapViewModel mapViewModel;
    private final Canvas canvas;


    public MapView(WorldMap map) {
        mapViewModel = new MapViewModel(map);
        mapViewModel.subscribe(this);
        canvas = new Canvas();
        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());
        this.getChildren().add(canvas);

        this.addEventHandler(ScrollEvent.SCROLL, mapViewModel::handleScroll);
        // FIXME: 8/18/21 Can't react to key events in a pane
        //this.addEventHandler(KeyEvent.KEY_PRESSED, mapViewModel::handleKey);
    }

    @Override
    public void update() {
        draw();
    }

    private void draw() {
        for (Map.Entry<Tile, Hexagon> entry : mapViewModel.getTilePolygonMap().entrySet()) {
            canvas.getGraphicsContext2D().setFill(entry.getKey().getTileType().getColor());
            canvas.getGraphicsContext2D().fillPolygon(entry.getValue().getX(), entry.getValue().getY(), 6);
        }
    }
}
