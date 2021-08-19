package com.lukas.tiles.generator;

import com.lukas.tiles.NoiseGenerator;
import com.lukas.tiles.model.Coordinate;
import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.TileType;
import com.lukas.tiles.model.WorldMap;
import com.lukas.tiles.model.setup.MapSize;
import com.lukas.tiles.model.setup.MapType;
import org.springframework.boot.web.reactive.context.GenericReactiveWebApplicationContext;

import java.util.ArrayDeque;
import java.util.Queue;
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
        WorldMap map = new WorldMap(generateWater(width, height));

        Random r = new Random();

        int count = Math.max(2, (int) Math.pow(width * height, 0.2));
        System.out.println("Generating " + count);

        for (int i = 0; i < count; i++) {
            int x = r.nextInt(height);
            int y = r.nextInt(width);
            boolean startsWithRock = r.nextBoolean();
            map.getTiles()[x][y].setTileType(startsWithRock ? TileType.Rock : TileType.Grass);

            Queue<Tile> tiles = new ArrayDeque<>();
            tiles.add(map.getTiles()[x][y]);

            double rockLikelihood = Math.pow(width * height, 0.5);

            while (!tiles.isEmpty()) {
                for (Tile tile : map.getAdjacent(tiles.remove())) {
                    if (tile.getTileType() != TileType.Water) {
                        continue;
                    }
                    if (rockLikelihood < 0.1) {
                        tile.setTileType(TileType.Coastal);
                    } else {
                        tile.setTileType(r.nextDouble() < Math.min(0.2, rockLikelihood / 20) ? TileType.Rock : TileType.Grass);
                    }
                    if (r.nextDouble() < rockLikelihood) {
                        tiles.add(tile);
                    }
                }
                rockLikelihood /= 1.05;
            }
        }

        return map.getTiles();
    }

    private static Tile[][] generateIslands(int width, int height) {
        WorldMap map = new WorldMap(generateWater(width, height));

        Random r = new Random();

        int count = (int) Math.pow(width * height, 0.4);

        for (int i = 0; i < count; i++) {
            int x = r.nextInt(height);
            int y = r.nextInt(width);
            boolean startsWithRock = r.nextBoolean();
            map.getTiles()[x][y].setTileType(startsWithRock ? TileType.Rock : TileType.Grass);

            Queue<Tile> tiles = new ArrayDeque<>();
            tiles.add(map.getTiles()[x][y]);

            double rockLikelihood = Math.sqrt(count) / 8;

            while (!tiles.isEmpty()) {
                for (Tile tile : map.getAdjacent(tiles.remove())) {
                    if (tile.getTileType() != TileType.Water) {
                        continue;
                    }
                    if (rockLikelihood < 0.05) {
                        tile.setTileType(TileType.Coastal);
                    } else {
                        tile.setTileType(r.nextDouble() < rockLikelihood / 2 ? TileType.Rock : TileType.Grass);
                    }
                    if (r.nextDouble() < rockLikelihood) {
                        tiles.add(tile);
                    }
                }
                rockLikelihood /= 1.4;
            }
        }

        return map.getTiles();
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

    private static Tile[][] generateWater(int width, int height) {
        Tile[][] result = new Tile[height][width];
        int counter = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (result[i][j] == null) {
                    result[i][j] = new Tile(TileType.Water, counter++);
                }
            }
        }

        return result;
    }
}
