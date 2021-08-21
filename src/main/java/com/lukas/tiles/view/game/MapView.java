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

        Hexagon.bindWidth(this.widthProperty());

        this.addEventHandler(ScrollEvent.SCROLL, mapViewModel::handleScroll);
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

        int firstVisible = -1;
        int jump = -1;
        int i = 0;

        while (i < mapViewModel.getHexagons().length && mapViewModel.getHexagons()[i].getX()[4] < 0) {
            i += mapViewModel.getWidth();
        }
        for (; i < mapViewModel.getHexagons().length; i++) {
            //All future tiles are below
            if (mapViewModel.getHexagons()[i].getX()[1] > this.getHeight()) {
                return;
            }
            //Horizontal optimisation (not working)
//            //Not drawing everything
//            if (firstVisible == -1 && mapViewModel.getHexagons()[i].getY()[2] > 0) {
//                firstVisible = i;
//            }
//            //current one no more visible
//            if (mapViewModel.getHexagons()[i].getY()[0] > this.getWidth()) {
//                if (jump == -1) {
//                    jump = mapViewModel.getWidth() - i % mapViewModel.getWidth() + firstVisible - 2;
//                }
//                i += jump;
//                if (i >= mapViewModel.getHexagons().length) {
//                    break;
//                }
//            }

            Tile temp = mapViewModel.getTiles()[i / mapViewModel.getWidth()][i % mapViewModel.getWidth()];
            canvas.getGraphicsContext2D().setFill(temp.getColor());
            canvas.getGraphicsContext2D().fillPolygon(mapViewModel.getHexagons()[i].getY(), mapViewModel.getHexagons()[i].getX(), 6);
        }
    }
}
