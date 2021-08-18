package com.lukas.tiles.model;

import com.lukas.tiles.viewModel.MapObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameMap {
    private final List<MapObserver> mapObservers;

    private Tile[][] tiles;

    public GameMap() {
        mapObservers = new ArrayList<>();
        tiles = mapCreator(10, 10);
    }

    public void subscribe(MapObserver mapObserver) {
        mapObservers.add(mapObserver);
    }

    private void updateMap() {
        for (MapObserver mapObserver : mapObservers) {
            mapObserver.update();
        }
    }

    private static Tile[][] mapCreator(int width, int height) {
        Tile[][] result = new Tile[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                result[i][j] = new Tile(new Random().nextBoolean() ? TileType.Gras : TileType.Water);
            }
        }

        return result;
    }
}
