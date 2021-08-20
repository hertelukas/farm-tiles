package com.lukas.tiles.io;

import com.lukas.tiles.Config;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ConfigHandler {

    private ConfigHandler() {

    }

    private final static String LOCATION = "config.tiles";


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

    public static Config load() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(LOCATION);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Config result = (Config) objectInputStream.readObject();
        objectInputStream.close();
        return result;
    }

    public static String getLOCATION() {
        return LOCATION;
    }
}
