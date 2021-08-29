package com.lukas.tiles.generator;

import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.TileType;
import com.lukas.tiles.model.WorldMap;
import com.lukas.tiles.model.setup.MapSize;
import com.lukas.tiles.model.setup.MapType;

import java.util.*;

/**
 * Responsible for the generation fo maps.
 * Holds different ways to do so as static methods based on the map type.
 *
 * @see com.lukas.tiles.model.TileType
 */
public class MapGenerator {

    private static long seed;

    /**
     * Generates a random map based on a randomly generated seed.
     *
     * @param mapSize The size of the map.
     * @param mapType The type of the map.
     * @return Returns a random generated map.
     */
    public static Tile[][] generate(MapSize mapSize, MapType mapType) {
        long tempSeed = new Random().nextLong();
        return generate(mapSize, mapType, tempSeed);
    }

    /**
     * Generates a map based on a given seed.
     *
     * @param mapSize The size of the map.
     * @param mapType The type of the map.
     * @param seed    Seed for the creation of random values.
     * @return Returns a random generated map based on a specified seed.
     */
    public static Tile[][] generate(MapSize mapSize, MapType mapType, long seed) {
        MapGenerator.seed = seed;
        System.out.println("Using seed: " + seed);
        Tile[][] result;
        switch (mapType) {
            case Random -> result = generateRandom(mapSize.getWidth(), mapSize.getHeight());
            case Islands -> result = generateIslands(mapSize.getWidth(), mapSize.getHeight());
            case Pangea -> result = generatePangea(mapSize.getWidth(), mapSize.getHeight(), 0);
            default -> result = generateContinents(mapSize.getWidth(), mapSize.getHeight());
        }
        return result;
    }

    /**
     * Creates a completely random map, every tile is equally randomly chosen
     *
     * @param width  is the width of the array
     * @param height is the height of the array
     * @return the generated map
     */
    private static Tile[][] generateRandom(int width, int height) {
        Tile[][] result = new Tile[height][width];
        Random r = new Random(seed);

        int counter = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                result[i][j] = new Tile(TileType.values()[r.nextInt(TileType.values().length)], counter++);
            }
        }
        return result;
    }

    /**
     * Creates a 2d array with large continents, at least two and max four
     *
     * @param width  is the width of the array
     * @param height is the height of the array
     * @return the generated map
     */
    // TODO: 8/28/21 try generating of continents with perlin noise
    private static Tile[][] generateContinents(int width, int height) {
        WorldMap map = new WorldMap(generateWater(width, height));

        Random r = new Random(seed);

        int count = r.nextInt(2) + 2;
        int maxRadius = (int) (Math.sqrt(width * height) / 1.5);

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

    /**
     * Creates a 2d array with islands, at least two and around one island per 300 tiles
     *
     * @param width  is the width of the array
     * @param height is the height of the array
     * @return the generated map
     */
    private static Tile[][] generateIslands(int width, int height) {
        WorldMap map = new WorldMap(generateWater(width, height));

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

    /**
     * @param maxRadius is the maximal radius an island / continent grows from the center
     * @param amount    amount of islands to create
     * @param map       the map object that gets modified
     */
    private static void genericDistanceGenerator(int maxRadius, int amount, WorldMap map) {
        Random r = new Random(seed);
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

    /**
     * Generates a pangea map
     *
     * @param width  is the width of the array
     * @param height is the height of the array
     * @return the final tile array representing a map
     */
    private static Tile[][] generatePangea(int width, int height, int depth) {
        Tile[][] result = new Tile[height][width];

        NoiseGenerator noiseGenerator = new NoiseGenerator(seed);

        int counter = 0;
        int grassCounter = 0;
        int mountainCounter = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                double noise = noiseGenerator.noise(i, j);
                if (noise < -0.3) {
                    result[i][j] = new Tile(TileType.Water, counter++);
                } else if (noise < -0.2) {
                    result[i][j] = new Tile(TileType.Coastal, counter++);
                } else if (noise < 0.4) {
                    grassCounter++;
                    result[i][j] = new Tile(TileType.Grass, counter++);
                } else {
                    mountainCounter++;
                    result[i][j] = new Tile(TileType.Rock, counter++);
                }
            }
        }
        if (grassCounter < width * height / 1.75 || mountainCounter < width * height / 20) {
            return generatePangea(width, height, ++depth);
        }


        System.out.println(depth + " generations");
        return result;
    }

    /**
     * Generates a 2D tile array with only water tiles
     *
     * @param width  is the width of the array
     * @param height is the height of the array
     * @return a 2D tile array
     */
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
