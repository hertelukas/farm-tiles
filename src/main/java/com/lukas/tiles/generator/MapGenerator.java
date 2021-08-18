package com.lukas.tiles.generator;

import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.TileType;

import java.util.Random;

public class MapGenerator {
    public static Tile[][] generate(int width, int height) {
        Tile[][] result = new Tile[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                result[i][j] = new Tile(new Random().nextBoolean() ? TileType.Gras : TileType.Water);
            }
        }

        return result;
    }
}
