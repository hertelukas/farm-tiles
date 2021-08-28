package com.lukas.tiles.io;

import com.lukas.tiles.model.Game;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handles the saving and loading of a game
 */
public class GameHandler {

    /**
     * @param game that should be saved
     * @return whether the operation was successful
     */
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

    /**
     * @param name of the game
     * @return the game that was saved with this name
     * @throws ClassNotFoundException if the game class can't be found
     * @throws IOException            when the loading fails
     */
    public static Game load(String name) throws ClassNotFoundException, IOException {
        FileInputStream fileInputStream = new FileInputStream(name + "_game.tiles");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Game result = (Game) objectInputStream.readObject();
        objectInputStream.close();
        return result;
    }

    /**
     * Delete a game based on the reference to the game
     *
     * @param game the game that should get deleted
     * @return whether the operation was successful
     */
    public static boolean delete(Game game) {
        return delete(game.getName());
    }

    /**
     * Delete a game based on the name
     *
     * @param game name of the game that should get deleted
     * @return whether the operation was successful
     */
    public static boolean delete(String game) {
        try {
            Files.delete(Path.of(game + "_game.tiles"));
            return true;
        } catch (IOException e) {
            System.out.println("Failed to clean up game files: " + e.getMessage());
            return false;
        }
    }

    /**
     * @return a list of all saved games as string
     */
    public static Set<String> getGames() {

        return Stream.of(Objects.requireNonNull(new File(".").listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .filter(s -> s.endsWith("_game.tiles"))
                .map(s -> s.substring(0, s.length() - 11))
                .collect(Collectors.toSet());
    }
}
