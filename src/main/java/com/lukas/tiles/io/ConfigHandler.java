package com.lukas.tiles.io;

import com.google.gson.Gson;
import com.lukas.tiles.Config;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ConfigHandler {

    private final static String LOCATION = "config.tiles";

    private final Gson gson;

    public ConfigHandler() {
        this.gson = new Gson();
    }


    public boolean save(Config config) {
        try {
            Files.writeString(Path.of(LOCATION), gson.toJson(config));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Config load() {
        try {
            return gson.fromJson(Files.newBufferedReader(Path.of(LOCATION)), Config.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getLOCATION() {
        return LOCATION;
    }
}
