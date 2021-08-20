package com.lukas.tiles;

import com.lukas.tiles.io.GameHandler;
import com.lukas.tiles.model.Game;
import com.lukas.tiles.model.Setup;
import com.lukas.tiles.model.building.Farm;
import com.lukas.tiles.model.setup.Difficulty;
import com.lukas.tiles.model.setup.MapSize;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class GameHandlerTest {

    @Test
    public void saveGame() {
        Setup setup = new Setup();
        setup.setMapSize(MapSize.Medium);
        setup.setDifficulty(Difficulty.Medium);
        Game game = Game.generate(setup);
        game.setName("test");

        assertTrue(GameHandler.save(game));
    }

    @Test
    public void saveAndLoadGame() throws Exception {
        Setup setup = new Setup();
        setup.setMapSize(MapSize.Medium);
        setup.setDifficulty(Difficulty.Medium);
        Game game = Game.generate(setup);
        game.setName("test");

        assertTrue(GameHandler.save(game));

        assertEquals(game, GameHandler.load("test"));
    }

    @Test
    public void saveAndLoadGameWithBuildings() throws Exception {
        Setup setup = new Setup();
        setup.setMapSize(MapSize.Medium);
        setup.setDifficulty(Difficulty.Medium);
        Game game = Game.generate(setup);
        game.setName("test");
        game.getMap().getTiles()[0][0].setBuilding(new Farm());

        assertTrue(GameHandler.save(game));

        assertEquals(game, GameHandler.load("test"));
    }

    @Disabled
    @Test
    public void showsCorrectGames() {
        Setup setup = new Setup();

        //First game
        setup.setMapSize(MapSize.Medium);
        setup.setDifficulty(Difficulty.Medium);
        Game game = Game.generate(setup);
        game.setName("test");

        assertTrue(GameHandler.save(game));

        setup.setMapSize(MapSize.Small);
        setup.setDifficulty(Difficulty.Hard);

        Game game2 = Game.generate(setup);
        game2.setName("test2");

        assertTrue(GameHandler.save(game2));

        Set<String> saved = new HashSet<>();
        saved.add("test");
        saved.add("test2");

        assertEquals(saved, GameHandler.getGames());

        assertTrue(GameHandler.delete(game2));
    }

    @AfterEach
    public void cleanUp() {
        System.out.println("Cleaning up IO Tests...");
        try {
            Files.delete(Path.of("test_game.tiles"));
        } catch (IOException e) {
            System.out.println("Failed to clean up game files: " + e.getMessage());
        }
    }


}
