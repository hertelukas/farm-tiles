package com.lukas.tiles.viewModel.game;

import com.lukas.tiles.FarmTilesApplication;
import com.lukas.tiles.SceneLoader;
import com.lukas.tiles.io.GameHandler;
import com.lukas.tiles.model.Game;

import java.io.IOException;

/**
 * The ViewModel for the escape menu
 * Responsible for controlling all button presses and displayed information
 */
public class EscapeMenuViewModel {
    private final Game game;

    /**
     * Creates a new EscapeMenuViewModel based on the currently played game
     *
     * @param game that should be saved in case of saving
     */
    public EscapeMenuViewModel(Game game) {
        this.game = game;
    }

    /**
     * Only saves the game
     *
     * @return whether the saving was successful
     */
    public boolean save() {
        return GameHandler.save(game);
    }

    // TODO: 8/29/21 react to failed saves

    /**
     * Saves the game and quits the whole application
     */
    public void saveAndQuit() {
        save();
        // TODO: 8/21/21 might not be the best way to stop
        System.exit(0);
    }

    /**
     * Saves the game and loads the main menu
     */
    public void saveAndMenu() {
        save();
        try {
            SceneLoader.getInstance().loadScene(FarmTilesApplication.getStartPage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
