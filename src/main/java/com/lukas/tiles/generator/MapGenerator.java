package com.lukas.tiles.generator;

import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.TileType;

import java.util.Random;

public class MapGenerator {
    public static Tile[][] generate(int width, int height) {
        Tile[][] result = new Tile[height][width];

        int counter = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result[i][j] = new Tile(new Random().nextBoolean() ? TileType.Grass : TileType.Water, counter++);
            }
        }
        return result;
    }
}
