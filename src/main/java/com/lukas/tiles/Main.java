package com.lukas.tiles;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Entry point for the application, starts a new Farm Tiles Application
 */
public class Main {

    private static Logger logger = Logger.getLogger("");
    private static FileHandler fh;

    /**
     * Starts the Farm Tiles Applications
     *
     * @param args An array of arguments, currently getting ignored.
     */
    public static void main(String[] args) {
        //Setting up the logger
        try {
            fh = new FileHandler("log.tiles");
            logger.addHandler(fh);
        } catch (IOException e) {
            logger.warning("Failed to set FileHandler as logger: " + e.getMessage());
        }

        logger.info("Starting application...");

        FarmTilesApplication.startApplication(args);
    }
}
