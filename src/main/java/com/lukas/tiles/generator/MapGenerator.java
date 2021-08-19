package com.lukas.tiles.generator;

import com.lukas.tiles.NoiseGenerator;
import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.TileType;
import com.lukas.tiles.model.setup.MapSize;
import com.lukas.tiles.model.setup.MapType;

import java.util.Random;

public class MapGenerator {

    public static Tile[][] generate(MapSize mapSize, MapType mapType) {
        Tile[][] result;
        switch (mapType) {
            case Random -> result = generateRandom(mapSize.getWidth(), mapSize.getHeight());
            case Islands -> result = generateIslands(mapSize.getWidth(), mapSize.getHeight());
            case Pangea -> result = generatePangea(mapSize.getWidth(), mapSize.getHeight(), 0);
            default -> result = generateContinents(mapSize.getWidth(), mapSize.getHeight());
        }

        return result;
    }

    private static Tile[][] generateRandom(int width, int height) {
        Tile[][] result = new Tile[height][width];
        Random r = new Random();

        int counter = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                result[i][j] = new Tile(TileType.values()[r.nextInt(TileType.values().length)], counter++);
            }
        }
        return result;
    }

    private static Tile[][] generateContinents(int width, int height) {
        Tile[][] result = new Tile[height][width];

        Random r = new Random();

        int counter = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                result[i][j] = new Tile(TileType.values()[r.nextInt(TileType.values().length)], counter++);
            }
        }
        return result;
    }

    private static Tile[][] generateIslands(int width, int height) {
        Tile[][] result = new Tile[height][width];

        Random r = new Random();

        return result;
    }

    private static Tile[][] generatePangea(int width, int height, int depth) {
        Tile[][] result = new Tile[height][width];

        NoiseGenerator noiseGenerator = new NoiseGenerator();

        int counter = 0;
        int grassCounter = 0;
        int mountainCounter = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                double noise = noiseGenerator.noise(i, j);
                if (noise < 0.05) {
                    result[i][j] = new Tile(TileType.Water, counter++);
                } else if (noise < 0.1) {
                    result[i][j] = new Tile(TileType.Coastal, counter++);
                } else if (noise < 0.5) {
                    grassCounter++;
                    result[i][j] = new Tile(TileType.Grass, counter++);
                } else {
                    mountainCounter++;
                    result[i][j] = new Tile(TileType.Rock, counter++);
                }
            }
        }

        if (grassCounter < width * height / 1.5 || mountainCounter < width * height / 20) {
            return generatePangea(width, height, ++depth);
        }

        System.out.println(depth + " generations");
        return result;
    }
}
