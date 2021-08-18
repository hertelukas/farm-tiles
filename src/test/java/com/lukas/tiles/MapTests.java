package com.lukas.tiles;

import com.lukas.tiles.model.Coordinate;
import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.TileType;
import com.lukas.tiles.model.WorldMap;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MapTests {

    @Test
    public void testAdjacencyEven() {
        Tile[][] tiles = new Tile[5][5];

        Tile oneOne = new Tile(TileType.Water);
        Tile oneTwo = new Tile(TileType.Water);
        Tile twoOne = new Tile(TileType.Water);
        Tile twoTwo = new Tile(TileType.Water);
        Tile twoThree = new Tile(TileType.Water);
        Tile threeOne = new Tile(TileType.Water);
        Tile threeTwo = new Tile(TileType.Water);

        tiles[1][1] = oneOne;
        tiles[1][2] = oneTwo;
        tiles[2][1] = twoOne;
        tiles[2][2] = twoTwo;
        tiles[2][3] = twoThree;
        tiles[3][1] = threeOne;
        tiles[3][2] = threeTwo;

        WorldMap worldMap = new WorldMap(tiles);
        Set<Tile> neighbours = worldMap.getAdjacent(new Coordinate(2, 2));
        assertEquals(6, neighbours.size());
        for (Tile neighbour : neighbours) {
            assertNotNull(neighbour);
        }
    }

    @Test
    public void testAdjacencyUneven() {
        Tile[][] tiles = new Tile[5][5];

        Tile zeroOne = new Tile(TileType.Water);
        Tile zeroTwo = new Tile(TileType.Water);
        Tile oneZero = new Tile(TileType.Water);
        Tile oneOne = new Tile(TileType.Water);
        Tile oneTwo = new Tile(TileType.Water);
        Tile twoOne = new Tile(TileType.Water);
        Tile twoTwo = new Tile(TileType.Water);

        tiles[0][1] = zeroOne;
        tiles[0][2] = zeroTwo;
        tiles[1][0] = oneZero;
        tiles[1][1] = oneOne;
        tiles[1][2] = oneTwo;
        tiles[2][1] = twoOne;
        tiles[2][2] = twoTwo;

        WorldMap worldMap = new WorldMap(tiles);
        Set<Tile> neighbours = worldMap.getAdjacent(new Coordinate(1, 1));
        assertEquals(6, neighbours.size());
        for (Tile neighbour : neighbours) {
            assertNotNull(neighbour);
        }
    }

    @Test
    public void testLeftSuccessful() {
        Tile[][] tiles = new Tile[2][2];

        Tile left = new Tile(TileType.Water);
        Tile from = new Tile(TileType.Water);

        tiles[0][0] = left;
        tiles[0][1] = from;
        WorldMap worldMap = new WorldMap(tiles);
        assertSame(left, worldMap.getLeft(from).orElseThrow());
        assertSame(left, worldMap.getLeft(new Coordinate(0, 1)).orElseThrow());
    }

    @Test
    public void testLeftUnsuccessful() {
        Tile[][] tiles = new Tile[2][2];

        Tile from = new Tile(TileType.Water);

        tiles[0][0] = from;
        WorldMap worldMap = new WorldMap(tiles);
        assertTrue(worldMap.getLeft(from).isEmpty());
        assertTrue(worldMap.getLeft(new Coordinate(0, 0)).isEmpty());
    }

    @Test
    public void testRightSuccessful() {
        Tile[][] tiles = new Tile[2][2];

        Tile from = new Tile(TileType.Water);
        Tile right = new Tile(TileType.Water);

        tiles[0][0] = from;
        tiles[0][1] = right;
        WorldMap worldMap = new WorldMap(tiles);
        assertSame(right, worldMap.getRight(from).orElseThrow());
        assertSame(right, worldMap.getRight(new Coordinate(0, 0)).orElseThrow());
    }

    @Test
    public void testRightUnsuccessful() {
        Tile[][] tiles = new Tile[2][2];

        Tile from = new Tile(TileType.Water);

        tiles[1][1] = from;
        WorldMap worldMap = new WorldMap(tiles);
        assertTrue(worldMap.getRight(from).isEmpty());
        assertTrue(worldMap.getRight(new Coordinate(1, 1)).isEmpty());
    }

    @Test
    public void testTopRightSuccessful() {
        Tile[][] tiles = new Tile[2][2];

        Tile from = new Tile(TileType.Water);
        Tile right = new Tile(TileType.Water);

        tiles[1][0] = from;
        tiles[0][1] = right;
        WorldMap worldMap = new WorldMap(tiles);
        assertSame(right, worldMap.getTopRight(from).orElseThrow());
        assertSame(right, worldMap.getTopRight(new Coordinate(1, 0)).orElseThrow());
    }

    @Test
    public void testTopRightUnsuccessful() {
        Tile[][] tiles = new Tile[2][2];

        Tile from = new Tile(TileType.Water);
        Tile from2 = new Tile(TileType.Water);
        Tile from3 = new Tile(TileType.Water);

        tiles[0][1] = from;
        tiles[0][0] = from2;
        tiles[1][1] = from3;
        WorldMap worldMap = new WorldMap(tiles);
        assertTrue(worldMap.getTopRight(from).isEmpty());
        assertTrue(worldMap.getTopRight(new Coordinate(0, 1)).isEmpty());

        assertTrue(worldMap.getTopRight(from2).isEmpty());
        assertTrue(worldMap.getTopRight(new Coordinate(0, 0)).isEmpty());

        assertTrue(worldMap.getTopRight(from3).isEmpty());
    }
}
