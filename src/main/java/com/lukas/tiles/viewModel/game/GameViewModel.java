package com.lukas.tiles.viewModel.game;

import com.lukas.tiles.SceneLoader;
import com.lukas.tiles.model.Game;
import com.lukas.tiles.model.Scheduler;
import com.lukas.tiles.model.setup.Setup;
import com.lukas.tiles.view.BasicObserver;
import com.lukas.tiles.view.game.EscapeMenu;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;


public class GameViewModel {
    private final Game game;
    private final List<BasicObserver> observers;
    private boolean showingTab = false;

    public GameViewModel(Setup setup) {
        this.game = Game.generate(setup);
        observers = new ArrayList<>();
    }

    public GameViewModel(Game game) {
        this.game = game;
        observers = new ArrayList<>();
    }

    public void subscribe(BasicObserver observer) {
        observers.add(observer);
    }

    public Game getGame() {
        return game;
    }

    public void handleKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ESCAPE)) {
            if (showingTab) {
                toggleTab();
            } else {
                SceneLoader.getInstance().loadStage(new EscapeMenu(game));
            }
            return;
        }

        // FIXME: 8/26/21 tab seems to work only when no other windows are opened
        if (event.getCode().equals(KeyCode.TAB)) {
            toggleTab();
        }
    }

    public void toggleTab() {
        showingTab = !showingTab;
        promoteUpdate();
    }

    public boolean isShowingTab() {
        return showingTab;
    }

    public Scheduler getGameScheduler() {
        return game.getScheduler();
    }

    private void promoteUpdate() {
        for (BasicObserver observer : observers) {
            observer.update();
        }
    }
}
