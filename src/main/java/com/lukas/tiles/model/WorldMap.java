package com.lukas.tiles.model;

import com.lukas.tiles.generator.MapGenerator;
import com.lukas.tiles.model.setup.MapSize;
import com.lukas.tiles.model.setup.MapType;
import com.lukas.tiles.view.BasicObserver;
import org.jetbrains.annotations.TestOnly;
import org.jetbrains.annotations.Unmodifiable;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Represents a savable map in the game
 */
public class WorldMap implements Serializable {
    @Serial
    private static final long serialVersionUID = 7454031335131925460L;
    private transient List<BasicObserver> mapObservers;

    private final Tile[][] tiles;
    private final int width;
    private final int height;

    /**
     * @param mapSize the size of the map
     * @param mapType the type of the map
     */
    public WorldMap(MapSize mapSize, MapType mapType) {
        mapObservers = new ArrayList<>();
        this.width = mapSize.getWidth();
        this.height = mapSize.getHeight();
        tiles = MapGenerator.generate(mapSize, mapType);
    }

    /**
     * @param tiles Creates a WorldMap based on a predefined array instead of generating a new map.
     */
    @TestOnly
    public WorldMap(Tile[][] tiles) {
        mapObservers = new ArrayList<>();
        this.tiles = tiles;
        height = tiles.length;
        width = tiles[0].length;
    }

    /**
     * Subscribe to get notified about changes
     *
     * @param mapObserver the object that wants to receive updates from the WorldMap
     */
    public void subscribe(BasicObserver mapObserver) {
        if (mapObservers == null) {
            mapObservers = new ArrayList<>();
        }
        mapObservers.add(mapObserver);
    }

    /**
     * @return The total size in tiles of the map.
     */
    public int getSize() {
        return width * height;
    }

    /**
     * @return The 2D array representing the map in tiles.
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * @return The height of the map (x-direction) in tiles.
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return The width of the map (y-direction) in tiles.
     */
    public int getWidth() {
        return width;
    }

    private void updateMap() {
        for (BasicObserver mapObserver : mapObservers) {
            mapObserver.update();
        }
    }

    /**
     * @param tile The tile that is getting searched for.
     * @return The (x,y)-coordinate of the tile.
     */
    public Coordinate getCoordinate(Tile tile) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] == tile) {
                    return new Coordinate(i, j);
                }
            }
        }
        return null;
    }

    /**
     * @return A Set of islands, an island is represented as set.
     */
    @Unmodifiable
    public Set<Set<Tile>> getIslands() {
        Set<Set<Tile>> result = new HashSet<>();

        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile.getTileType() != TileType.Water) {
                    boolean isAlreadyInSet = false;
                    for (Set<Tile> tileSet : result) {
                        if (tileSet.contains(tile)) {
                            isAlreadyInSet = true;
                            break;
                        }
                    }

                    if (!isAlreadyInSet) {
                        Set<Tile> newIsland = getIsland(tile);
                        result.add(newIsland);
                    }
                }
            }
        }

        return Collections.unmodifiableSet(result);
    }

    /**
     * @param tile A tile which should be searched for.
     * @return All tiles connected via land to a given tile.
     */
    @Unmodifiable
    public Set<Tile> getIsland(Tile tile) {
        //If not an island, return empty
        if (tile.getTileType() == TileType.Water) {
            return Collections.emptySet();
        }
        Set<Tile> result = new HashSet<>();

        Queue<Tile> tilesWithNeighbours = new ArrayDeque<>();
        tilesWithNeighbours.add(tile);
        result.add(tile);

        while (!tilesWithNeighbours.isEmpty()) {
            Set<Tile> neighbours = getAdjacent(tilesWithNeighbours.remove());
            for (Tile neighbour : neighbours) {
                if (neighbour.getTileType() != TileType.Water && !result.contains(neighbour)) {
                    tilesWithNeighbours.add(neighbour);
                    result.add(neighbour);
                }
            }
        }

        return Collections.unmodifiableSet(result);
    }

    /**
     * @param from The tile where it should be searched from.
     * @return An optional that contains the left tile of the given one, if there is one.
     */
    public Optional<Tile> getLeft(Tile from) {
        return getLeft(getCoordinate(from));
    }

    /**
     * @param coordinate The coordinate where it should be searched from.
     * @return An optional that contains the left tile of the given location, if there is one.
     */
    public Optional<Tile> getLeft(Coordinate coordinate) {
        if (coordinate.y() > 0) {
            return Optional.of(tiles[coordinate.x()][coordinate.y() - 1]);
        }
        return Optional.empty();
    }

    /**
     * @param from The tile where it should be searched from.
     * @return An optional that contains the right tile of the given one, if there is one.
     */
    public Optional<Tile> getRight(Tile from) {
        return getRight(getCoordinate(from));
    }

    /**
     * @param coordinate The coordinate where it should be searched from.
     * @return An optional that contains the right tile of the given location, if there is one.
     */
    public Optional<Tile> getRight(Coordinate coordinate) {
        if (coordinate.y() + 1 < width) {
            return Optional.of(tiles[coordinate.x()][coordinate.y() + 1]);
        }
        return Optional.empty();
    }

    /**
     * @param from The tile where it should be searched from.
     * @return An optional that contains the top left tile of the given one, if there is one.
     */
    public Optional<Tile> getTopLeft(Tile from) {
        return getTopLeft(getCoordinate(from));
    }

    /**
     * @param coordinate The coordinate where it should be searched from.
     * @return An optional that contains the top left tile of the given location, if there is one.
     */
    public Optional<Tile> getTopLeft(Coordinate coordinate) {
        if (coordinate.isEven()) {
            if (coordinate.y() > 0 && coordinate.x() > 0) {
                return Optional.of(tiles[coordinate.x() - 1][coordinate.y() - 1]);
            }
        } else {
            return Optional.of(tiles[coordinate.x() - 1][coordinate.y()]);
        }
        return Optional.empty();
    }

    /**
     * @param from The coordinate where it should be searched from.
     * @return An optional that contains the top right tile of the given one, if there is one.
     */
    public Optional<Tile> getTopRight(Tile from) {
        return getTopRight(getCoordinate(from));
    }

    /**
     * @param coordinate The coordinate where it should be searched from.
     * @return An optional that contains the top right tile of the given location, if there is one.
     */
    public Optional<Tile> getTopRight(Coordinate coordinate) {
        if (coordinate.isEven()) {
            if (coordinate.y() < width && coordinate.x() > 0) {
                return Optional.of(tiles[coordinate.x() - 1][coordinate.y()]);
            }
        } else {
            if (coordinate.y() + 1 < width) {
                return Optional.of(tiles[coordinate.x() - 1][coordinate.y() + 1]);
            }
        }
        return Optional.empty();
    }

    /**
     * @param from The tile where it should be searched from.
     * @return An optional that contains the bottom left tile of the given one, if there is one.
     */
    public Optional<Tile> getBottomLeft(Tile from) {
        return getBottomLeft(getCoordinate(from));
    }

    /**
     * @param coordinate The coordinate where it should be searched from.
     * @return An optional that contains the bottom left tile of the given location, if there is one.
     */
    public Optional<Tile> getBottomLeft(Coordinate coordinate) {
        if (coordinate.isEven()) {
            if (coordinate.y() > 0 && coordinate.x() + 1 < height) {
                return Optional.of(tiles[coordinate.x() + 1][coordinate.y() - 1]);
            }
        } else {
            if (coordinate.x() + 1 < height) {
                return Optional.of(tiles[coordinate.x() + 1][coordinate.y()]);
            }
        }
        return Optional.empty();
    }

    /**
     * @param from The coordinate where it should be searched from.
     * @return An optional that contains the bottom right tile of the given one, if there is one.
     */
    public Optional<Tile> getBottomRight(Tile from) {
        return getBottomRight(getCoordinate(from));
    }

    /**
     * @param coordinate The coordinate where it should be searched from.
     * @return An optional that contains the bottom right tile of the given location, if there is one.
     */
    public Optional<Tile> getBottomRight(Coordinate coordinate) {
        if (coordinate.isEven()) {
            if (coordinate.y() < width && coordinate.x() + 1 < height) {
                return Optional.of(tiles[coordinate.x() + 1][coordinate.y()]);
            }
        } else {
            if (coordinate.y() + 1 < width && coordinate.x() + 1 < height) {
                return Optional.of(tiles[coordinate.x() + 1][coordinate.y() + 1]);
            }
        }

        return Optional.empty();
    }

    /**
     * @param tiles A collection of tiles.
     * @return A set of all directly adjacent tiles to the given collection of tiles.
     */
    public Set<Tile> getAdjacent(Collection<Tile> tiles) {
        Set<Tile> result = new HashSet<>();

        for (Tile tile : tiles) {
            for (Tile tile1 : getAdjacent(tile)) {
                if (!tiles.contains(tile1)) {
                    result.add(tile1);
                }
            }
        }

        return Collections.unmodifiableSet(result);
    }

    /**
     * @return A set of adjacent tiles to a given tile.
     */
    @Unmodifiable
    public Set<Tile> getAdjacent(Tile tile) {
        Coordinate coordinate = getCoordinate(tile);
        if (coordinate != null) {
            return getAdjacent(coordinate);
        }
        return Collections.emptySet();
    }

    /**
     * @return A set of adjacent tiles to a given location.
     */
    @Unmodifiable
    public Set<Tile> getAdjacent(Coordinate coordinate) {
        Set<Tile> result = new HashSet<>();

        if (getTopLeft(coordinate).isPresent()) {
            result.add(getTopLeft(coordinate).get());
        }
        if (getTopRight(coordinate).isPresent()) {
            result.add(getTopRight(coordinate).get());
        }
        if (getLeft(coordinate).isPresent()) {
            result.add(getLeft(coordinate).get());
        }
        if (getRight(coordinate).isPresent()) {
            result.add(getRight(coordinate).get());
        }
        if (getBottomLeft(coordinate).isPresent()) {
            result.add(getBottomLeft(coordinate).get());
        }
        if (getBottomRight(coordinate).isPresent()) {
            result.add(getBottomRight(coordinate).get());
        }
        return Collections.unmodifiableSet(result);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorldMap worldMap = (WorldMap) o;
        System.out.println("Width: " + (width == worldMap.width));
        System.out.println("Height: " + (height == worldMap.height));
        System.out.println("Tiles: " + Arrays.deepEquals(tiles, worldMap.tiles));
        return width == worldMap.width && height == worldMap.height && Arrays.deepEquals(tiles, worldMap.tiles);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(width, height);
        result = 31 * result + Arrays.deepHashCode(tiles);
        return result;
    }
}
