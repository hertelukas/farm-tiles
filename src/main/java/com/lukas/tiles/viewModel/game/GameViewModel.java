package com.lukas.tiles.viewModel.game;

import com.lukas.tiles.SceneLoader;
import com.lukas.tiles.model.Game;
import com.lukas.tiles.model.Setup;
import com.lukas.tiles.view.game.EscapeMenu;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class GameViewModel {
    private final Game game;

    public GameViewModel(Setup setup) {
        this.game = Game.generate(setup);
    }

    public GameViewModel(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void handleKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ESCAPE)) {
            SceneLoader.getInstance().loadStage(new EscapeMenu(game));
        }
    }
}
