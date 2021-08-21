package com.lukas.tiles.viewModel.game;

import com.lukas.tiles.FarmTilesApplication;
import com.lukas.tiles.SceneLoader;
import com.lukas.tiles.io.GameHandler;
import com.lukas.tiles.model.Game;

import java.io.IOException;

public class EscapeMenuViewModel {
    private final Game game;

    public EscapeMenuViewModel(Game game) {
        this.game = game;
    }

    public boolean save() {
        return GameHandler.save(game);
    }

    public void saveAndQuit() {
        save();
        // TODO: 8/21/21 might not be the best way to stop
        System.exit(0);
    }

    public void saveAndMenu() {
        save();
        try {
            SceneLoader.getInstance().loadScene(FarmTilesApplication.getStartPage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
