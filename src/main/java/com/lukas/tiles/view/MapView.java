package com.lukas.tiles.view;

import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.TileType;
import com.lukas.tiles.model.WorldMap;
import com.lukas.tiles.viewModel.Hexagon;
import com.lukas.tiles.viewModel.MapViewModel;
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
        this.getChildren().add(canvas);

        Hexagon.bindWidth(this.widthProperty());

        this.addEventHandler(ScrollEvent.SCROLL, mapViewModel::handleScroll);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> mapViewModel.handleDragged(e, this.getWidth(), this.getHeight()));
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, mapViewModel::mouseDown);
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, mapViewModel::mouseUp);
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
            Tile temp = mapViewModel.getTiles()[i / mapViewModel.getWidth()][i % mapViewModel.getWidth()];
            canvas.getGraphicsContext2D().setFill(temp.getColor());
            canvas.getGraphicsContext2D().fillPolygon(mapViewModel.getHexagons()[i].getY(), mapViewModel.getHexagons()[i].getX(), 6);
        }
    }
}
