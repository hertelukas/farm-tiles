package com.lukas.tiles.io;

import com.google.gson.Gson;
import com.lukas.tiles.model.Game;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameHandler {
    private final static Gson gson = new Gson();

    public static boolean save(Game game) {
        if (game.getName() == null) {
            System.out.println("No game name found");
            return false;
        }
        try {
            Files.writeString(Path.of(game.getName() + "_game.tiles"), gson.toJson(game));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Game load(String name) throws IOException {
        return gson.fromJson(Files.newBufferedReader(Path.of(name + "_game.tiles")), Game.class);
    }

    public static boolean delete(Game game) {
        return delete(game.getName());
    }

    public static boolean delete(String game) {
        try {
            Files.delete(Path.of(game + "_game.tiles"));
            return true;
        } catch (IOException e) {
            System.out.println("Failed to clean up game files: " + e.getMessage());
            return false;
        }
    }

    public static Set<String> getGames() {

        return Stream.of(Objects.requireNonNull(new File(".").listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .filter(s -> s.endsWith("_game.tiles"))
                .map(s -> s.substring(0, s.length() - 11))
                .collect(Collectors.toSet());
    }
}
