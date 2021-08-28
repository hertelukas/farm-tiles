package com.lukas.tiles.viewModel.game;

import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.WorldMap;
import com.lukas.tiles.view.BasicObserver;
import com.lukas.tiles.view.game.TileSelectedHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.util.ArrayList;
import java.util.List;

public class MapViewModel implements MapObserver {
    private final WorldMap map;
    private final List<BasicObserver> mapViewModelObservers;

    private TileSelectedHandler selectedHandler;

    private double zoom = 1;
    private final static double MAX_ZOOM = 2;
    private final static double MIN_ZOOM = 0.5;
    private final static double SENSITIVITY = 240; //Higher is slower

    private final Tile[][] tiles;
    private final int width;
    private final int height;
    private final Hexagon[] hexagons;

    private double lastX;
    private double lastY;

    private double xOffset = 0;
    private double yOffset = 0;

    private int forceCalculate = -1;

    public MapViewModel(WorldMap map) {
        this.map = map;
        map.subscribe(this);


        mapViewModelObservers = new ArrayList<>();
        hexagons = new Hexagon[map.getSize()];

        width = map.getWidth();
        height = map.getHeight();
        tiles = map.getTiles();

        update();
    }


    public Hexagon[] getHexagons() {
        return hexagons;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void subscribe(BasicObserver observer) {
        mapViewModelObservers.add(observer);
    }

    public void handleScroll(ScrollEvent scrollEvent, double width, double height) {

        //Find the current hovered hexagon
        int hoveredHexagonIndex = -1;
        for (int i = 0; i < hexagons.length; i++) {
            if (hexagons[i].isInside(scrollEvent.getSceneY(), scrollEvent.getSceneX())) {
                hoveredHexagonIndex = i;
                break;
            }
        }
        //Ignore the scroll if we are outside the map
        if (hoveredHexagonIndex == -1) {
            return;
        }

        zoom += scrollEvent.getDeltaY() / SENSITIVITY;
        zoom = Math.max(MIN_ZOOM, Math.min(MAX_ZOOM, zoom));

        double oldX = hexagons[hoveredHexagonIndex].getX()[0];
        double oldY = hexagons[hoveredHexagonIndex].getY()[1];
        forceCalculate = hoveredHexagonIndex;
        recalculate();

        double newX = hexagons[hoveredHexagonIndex].getX()[0];
        double newY = hexagons[hoveredHexagonIndex].getY()[1];

        move((oldX - newX), (oldY - newY), width, height);
        forceCalculate = -1;

        recalculate();
        updateMapView();
    }

    public void handleDragged(MouseEvent mouseEvent, double width, double height) {
        move(mouseEvent.getSceneY() - lastX, mouseEvent.getSceneX() - lastY, width, height);

        lastY = mouseEvent.getSceneX();
        lastX = mouseEvent.getSceneY();

        recalculate();
        updateMapView();
    }

    private void move(double x, double y, double width, double height) {
        double horizontalFactor = Math.sqrt(3) * zoom * Hexagon.getRelativeScale();
        double verticalFactor = 1.5 * zoom * Hexagon.getRelativeScale();
        yOffset = Math.max(-1 * (map.getWidth() + 1) * horizontalFactor + width, Math.min(0.5 * horizontalFactor, yOffset + y));
        xOffset = Math.max(-1 * (map.getHeight() + 1) * verticalFactor + height, Math.min(0.5 * verticalFactor, xOffset + x));
    }

    public void mouseDown(MouseEvent mouseEvent) {
        lastY = mouseEvent.getSceneX();
        lastX = mouseEvent.getSceneY();
    }

    public void mouseUp(MouseEvent mouseEvent) {
        if (lastY != mouseEvent.getSceneX() || lastX != mouseEvent.getSceneY()) {
            return; //Was a drag
        }

        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            for (int i = 0; i < hexagons.length; i++) {
                Tile current = tiles[i / map.getWidth()][i % map.getWidth()];
                if (hexagons[i].isInside(mouseEvent.getSceneY(), mouseEvent.getSceneX())) {
                    current.setSelected(true);
                    selectedHandler.handle(current);
                } else {
                    current.setSelected(false);
                }
            }
        } else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
            selectedHandler.handle(null);
            for (int i = 0; i < hexagons.length; i++) {
                tiles[i / map.getWidth()][i % map.getWidth()].setSelected(false);
            }
        }

        //Recalculation not necessary, only one tile might have changed color
        updateMapView();
    }

    private void updateMapView() {
        for (BasicObserver mapViewModelObserver : mapViewModelObservers) {
            mapViewModelObserver.update();
        }
    }


    @Override
    public void update() {
        recalculate();
        updateMapView();
    }

    private void recalculate() {
        boolean force;
        for (int i = 0; i < hexagons.length; i++) {
            force = i == forceCalculate;
            hexagons[i] = new Hexagon(i / width, i % width, zoom, xOffset, yOffset, force);
        }
    }


    public void setOnSelect(TileSelectedHandler handler) {
        this.selectedHandler = handler;
    }
}
