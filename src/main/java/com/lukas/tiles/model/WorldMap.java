package com.lukas.tiles.model;

import com.lukas.tiles.generator.MapGenerator;
import com.lukas.tiles.viewModel.MapObserver;
import org.jetbrains.annotations.TestOnly;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

public class WorldMap {
    private final List<MapObserver> mapObservers;

    private final Tile[][] tiles;
    private final int width;
    private final int height;

    public WorldMap(int width, int height) {
        mapObservers = new ArrayList<>();
        this.width = width;
        this.height = height;
        tiles = MapGenerator.generate(width, height);
    }

    @TestOnly
    public WorldMap(Tile[][] tiles) {
        mapObservers = new ArrayList<>();
        this.tiles = tiles;
        height = tiles.length;
        width = tiles[0].length;
    }

    public void subscribe(MapObserver mapObserver) {
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
        for (MapObserver mapObserver : mapObservers) {
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
}
