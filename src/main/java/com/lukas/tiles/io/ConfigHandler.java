package com.lukas.tiles.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lukas.tiles.Config;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ConfigHandler {

    private ConfigHandler() {

    }

    private final static String LOCATION = "config.tiles";

    private final static Gson gson = new Gson();


    public static boolean save(Config config) {
        try {
            Files.writeString(Path.of(LOCATION), gson.toJson(config));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Config load() throws IOException {
        return gson.fromJson(Files.newBufferedReader(Path.of(LOCATION)), Config.class);
    }

    public static String getLOCATION() {
        return LOCATION;
    }
}
