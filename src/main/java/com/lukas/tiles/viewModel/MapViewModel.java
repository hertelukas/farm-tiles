package com.lukas.tiles.viewModel;

import com.lukas.tiles.model.GameMap;
import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.TileType;
import com.lukas.tiles.view.MapViewModelObserver;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapViewModel implements MapObserver {
    private final GameMap map;
    private final List<MapViewModelObserver> mapViewModelObservers;

    private double zoom = 1;
    private final static double MAX_ZOOM = 3;
    private final static double MIN_ZOOM = 0.5;
    private final static double SENSITIVITY = 360; //Higher is slower

    private final Map<Tile, Hexagon> tilePolygonMap;

    public MapViewModel(GameMap map) {
        this.map = map;
        map.subscribe(this);
        mapViewModelObservers = new ArrayList<>();
        tilePolygonMap = new HashMap<>();
        update();
    }

    public void subscribe(MapViewModelObserver observer) {
        mapViewModelObservers.add(observer);
    }

    public java.util.Map<Tile, Hexagon> getTilePolygonMap() {
        return tilePolygonMap;
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
        for (Hexagon value : tilePolygonMap.values()) {
            System.out.println("Recalculating polygon...");
        }
    }
}
