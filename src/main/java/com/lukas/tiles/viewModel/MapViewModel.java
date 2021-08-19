package com.lukas.tiles.viewModel;

import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.WorldMap;
import com.lukas.tiles.view.MapViewModelObserver;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.util.ArrayList;
import java.util.List;

public class MapViewModel implements MapObserver {
    private final WorldMap map;
    private final List<MapViewModelObserver> mapViewModelObservers;

    private double zoom = 1;
    private final static double MAX_ZOOM = 2;
    private final static double MIN_ZOOM = 0.5;
    private final static double SENSITIVITY = 240; //Higher is slower

    private final Tile[][] tiles;
    private final int width;
    private final int height;
    private final Hexagon[] hexagons;

    private boolean isPressed = false;
    private double lastX;
    private double lastY;

    private double xOffset = 0;
    private double yOffset = 0;

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

    public void subscribe(MapViewModelObserver observer) {
        mapViewModelObservers.add(observer);
    }

    public void handleScroll(ScrollEvent scrollEvent) {
        zoom += scrollEvent.getDeltaY() / SENSITIVITY;
        zoom = Math.max(MIN_ZOOM, Math.min(MAX_ZOOM, zoom));
        recalculate();
        updateMapView();
    }

    public void handleKey(KeyEvent keyEvent) {
        System.out.println("Pressed key");
    }

    public void handleDragged(MouseEvent mouseEvent, double width, double height) {

        // FIXME: 8/19/21 -150 in the end doesn't seem right... very different on different scene sizes
        yOffset = Math.max(-1 * map.getWidth() * Math.sqrt(3) * zoom * Hexagon.getRelativeScale() + width - 150, Math.min(50, yOffset + mouseEvent.getSceneX() - lastY));
        xOffset = Math.max(-1 * map.getHeight() * 1.5 * zoom * Hexagon.getRelativeScale() + height - 150, Math.min(50, xOffset + mouseEvent.getSceneY() - lastX));

        lastY = mouseEvent.getSceneX();
        lastX = mouseEvent.getSceneY();

        recalculate();
        updateMapView();
    }

    public void mouseDown(MouseEvent mouseEvent) {
        lastY = mouseEvent.getSceneX();
        lastX = mouseEvent.getSceneY();
    }

    private void updateMapView() {
        for (MapViewModelObserver mapViewModelObserver : mapViewModelObservers) {
            mapViewModelObserver.update();
        }
    }


    @Override
    public void update() {
        recalculate();
        updateMapView();
    }

    private void recalculate() {
        for (int i = 0; i < hexagons.length; i++) {
            hexagons[i] = new Hexagon(i / width, i % width, zoom, xOffset, yOffset);
        }
    }


}
