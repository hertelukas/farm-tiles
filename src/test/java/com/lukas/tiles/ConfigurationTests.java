package com.lukas.tiles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigurationTests {

    @Test
    public void changeToGerman() {
        Config config = new Config();
        config.setLanguage(Language.Deutsch);
        assertEquals(config.getLanguage(), Language.Deutsch);
    }
}
