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

    public int getSize() {
        return width * height;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    private void updateMap() {
        for (BasicObserver mapObserver : mapObservers) {
            mapObserver.update();
        }
    }

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

        return result;
    }

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

    public Optional<Tile> getLeft(Tile from) {
        return getLeft(getCoordinate(from));
    }

    public Optional<Tile> getLeft(Coordinate coordinate) {
        if (coordinate.y() > 0) {
            return Optional.of(tiles[coordinate.x()][coordinate.y() - 1]);
        }
        return Optional.empty();
    }

    public Optional<Tile> getRight(Tile from) {
        return getRight(getCoordinate(from));
    }

    public Optional<Tile> getRight(Coordinate coordinate) {
        if (coordinate.y() + 1 < width) {
            return Optional.of(tiles[coordinate.x()][coordinate.y() + 1]);
        }
        return Optional.empty();
    }

    public Optional<Tile> getTopLeft(Tile from) {
        return getTopLeft(getCoordinate(from));
    }

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

    public Optional<Tile> getTopRight(Tile from) {
        return getTopRight(getCoordinate(from));
    }

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

    public Optional<Tile> getBottomLeft(Tile from) {
        return getBottomLeft(getCoordinate(from));
    }

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

    public Optional<Tile> getBottomRight(Tile from) {
        return getBottomRight(getCoordinate(from));
    }

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

    @Unmodifiable
    public Set<Tile> getAdjacent(Tile tile) {
        Coordinate coordinate = getCoordinate(tile);
        if (coordinate != null) {
            return getAdjacent(coordinate);
        }
        return Collections.emptySet();
    }

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
