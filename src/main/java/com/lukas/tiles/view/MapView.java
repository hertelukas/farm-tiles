package com.lukas.tiles.view;

import com.lukas.tiles.model.TileType;
import com.lukas.tiles.model.WorldMap;
import com.lukas.tiles.viewModel.Hexagon;
import com.lukas.tiles.viewModel.MapViewModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;

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

        Hexagon.bindWidth(this.widthProperty());

        this.addEventHandler(ScrollEvent.SCROLL, mapViewModel::handleScroll);
        // FIXME: 8/18/21 Can't react to key events in a pane
        //this.addEventHandler(KeyEvent.KEY_PRESSED, mapViewModel::handleKey);
    }

    @Override
    public void update() {
        draw();
    }

    private void draw() {
        canvas.getGraphicsContext2D().setFill(TileType.Water.getColor());
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < mapViewModel.getHexagons().length; i++) {
            canvas.getGraphicsContext2D().setFill(mapViewModel.getTiles()[i / mapViewModel.getWidth()][i % mapViewModel.getWidth()].getTileType().getColor());
            canvas.getGraphicsContext2D().fillPolygon(mapViewModel.getHexagons()[i].getY(), mapViewModel.getHexagons()[i].getX(), 6);
        }
    }
}
