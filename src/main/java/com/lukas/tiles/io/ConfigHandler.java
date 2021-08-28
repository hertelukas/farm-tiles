package com.lukas.tiles.io;

import com.lukas.tiles.Config;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Handles the saving and loading of the config file
 */
@Service
public class ConfigHandler {

    private ConfigHandler() {

    }

    private final static String LOCATION = "config.tiles";


    /**
     * @param config that should be saved
     * @return whether the save operation was successful
     */
    public static boolean save(Config config) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(LOCATION);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(config);
            objectOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return the loaded config file
     * @throws IOException            when an IOException occurs
     * @throws ClassNotFoundException when the config class could not be found
     */
    public static Config load() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(LOCATION);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Config result = (Config) objectInputStream.readObject();
        objectInputStream.close();
        return result;
    }

    /**
     * @return the location where the config file gets saved to
     */
    public static String getLOCATION() {
        return LOCATION;
    }
}
