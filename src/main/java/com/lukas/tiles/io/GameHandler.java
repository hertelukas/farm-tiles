package com.lukas.tiles.io;

import com.lukas.tiles.model.Game;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameHandler {

    public static boolean save(Game game) {
        if (game.getName() == null) {
            System.out.println("No game name found");
            return false;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(game.getName() + "_game.tiles");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(game);
            objectOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Game load(String name) throws ClassNotFoundException, IOException {
        FileInputStream fileInputStream = new FileInputStream(name + "_game.tiles");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Game result = (Game) objectInputStream.readObject();
        objectInputStream.close();
        return result;
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
