package com.lukas.tiles.generator;

import com.lukas.tiles.NoiseGenerator;
import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.TileType;
import com.lukas.tiles.model.WorldMap;
import com.lukas.tiles.model.setup.MapSize;
import com.lukas.tiles.model.setup.MapType;

import java.util.*;

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

        //One island per 300 tiles
        int count = r.nextInt(2) + 2;
        int maxRadius = (int) Math.sqrt(width * height) / 2;

        System.out.println("Generating " + count + " continents with a radius of " + maxRadius);

        int counter = 0;
        while (map.getIslands().size() < 2 || map.getIslands().size() > 4) {
            map = new WorldMap(generateWater(width, height));
            genericDistanceGenerator(maxRadius, count, map);
            counter++;
            //Emergency loop stopper -> trying to generate smaller continents
            maxRadius = r.nextBoolean() ? maxRadius - 1 : maxRadius;
        }


        System.out.println("Needed " + counter + " tries");
        return map.getTiles();
    }

    private final static int MAX_ISLAND_RADIUS = 5;

    private static Tile[][] generateIslands(int width, int height) {
        WorldMap map = new WorldMap(generateWater(width, height));

        Random r = new Random();

        //One island per 300 tiles
        int count = Math.max(2, width * height / 300);

        int counter = 0;
        while (map.getIslands().size() < count * 0.8) {
            map = new WorldMap(generateWater(width, height));
            genericDistanceGenerator(MAX_ISLAND_RADIUS, count, map);
            counter++;
        }
        System.out.println("Needed " + counter + " tries");

        return map.getTiles();
    }

    private static void genericDistanceGenerator(int maxRadius, int amount, WorldMap map) {
        Random r = new Random();
        for (int i = 0; i < amount; i++) {
            //Get coordinates for next random island
            int x = r.nextInt(map.getHeight());
            int y = r.nextInt(map.getWidth());

            //Random mountain in the middle
            boolean startsWithRock = r.nextBoolean();

            //Set the center
            map.getTiles()[x][y].setTileType(startsWithRock ? TileType.Rock : TileType.Grass);

            //Queue with all neighbours
            Queue<Tile> remainingTiles = new ArrayDeque<>();
            Set<Tile> blob = new HashSet<>();
            blob.add(map.getTiles()[x][y]);

            int radius = maxRadius;

            while (radius > 1) {
                remainingTiles.addAll(map.getAdjacent(blob));
                while (!remainingTiles.isEmpty()) {
                    Tile temp = remainingTiles.remove();
                    if (r.nextDouble() < (double) radius / maxRadius) {
                        temp.setTileType(r.nextDouble() < (double) (radius / 3) / maxRadius ? TileType.Rock : TileType.Grass);
                        blob.add(temp);
                    }
                }
                radius--;
            }

            remainingTiles.addAll(map.getAdjacent(blob));

            while (!remainingTiles.isEmpty()) {
                Tile temp = remainingTiles.remove();
                temp.setTileType(TileType.Coastal);
                blob.add(temp);
            }

        }
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
                if (noise < -0.4) {
                    result[i][j] = new Tile(TileType.Water, counter++);
                } else if (noise < -0.3) {
                    result[i][j] = new Tile(TileType.Coastal, counter++);
                } else if (noise < 0.2) {
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
