package com.lukas.tiles;

import com.lukas.tiles.io.ConfigHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ConfigHandlerTest {


    @Test
    public void saveEmptyConfig() {
        Config config = new Config();
        ConfigHandler configHandler = new ConfigHandler();

        assertTrue(configHandler.save(config));
    }

    @Test
    public void saveSimpleConfig() {
        Config config = new Config();
        config.setLanguage(Language.Deutsch);

        ConfigHandler configHandler = new ConfigHandler();

        assertTrue(configHandler.save(config));
    }

    @Test
    public void saveAndLoadConfig() {
        Config config = new Config();
        config.setLanguage(Language.Deutsch);

        ConfigHandler configHandler = new ConfigHandler();

        assertTrue(configHandler.save(config));
        assertEquals(config, configHandler.load());
    }

    @AfterEach
    public void cleanUp() {
        System.out.println("Cleaning up IO Tests...");
        try {
            Files.delete(Path.of(ConfigHandler.getLOCATION()));
        } catch (IOException e) {
            System.out.println("Failed to clean up config files: " + e.getMessage());
        }
    }

}
