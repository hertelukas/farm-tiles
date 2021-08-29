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

/**
 * The ViewModel for the game
 * Responsible for controlling all button presses and displayed information
 */
public class GameViewModel {
    private final Game game;
    private final List<BasicObserver> observers;
    private boolean showingTab = false;

    /**
     * Generates a new GameViewModel and a new game based on the information specified
     *
     * @param setup the user generated setup which contains all game setup information
     */
    public GameViewModel(Setup setup) {
        this.game = Game.generate(setup);
        observers = new ArrayList<>();
    }

    /**
     * Generates a new GameViewModel based on an existing game
     *
     * @param game the game that should be loaded
     */
    public GameViewModel(Game game) {
        this.game = game;
        observers = new ArrayList<>();
    }

    /**
     * Subscribe to get notified about changes
     *
     * @param observer the object that wants to receive updates from the GameViewModel
     */
    public void subscribe(BasicObserver observer) {
        observers.add(observer);
    }

    /**
     * @return the currently displayed game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Handles all key presses
     *
     * @param event the key event which should be handled
     */
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

    /**
     * Closes the tab view if visible, shows it if not
     */
    public void toggleTab() {
        showingTab = !showingTab;
        promoteUpdate();
    }

    /**
     * @return if the tab view is currently opened
     */
    public boolean isShowingTab() {
        return showingTab;
    }

    /**
     * @return the current game scheduler
     */
    public Scheduler getGameScheduler() {
        return game.getScheduler();
    }

    /**
     * Notifies all subscribers about an update
     */
    private void promoteUpdate() {
        for (BasicObserver observer : observers) {
            observer.update();
        }
    }
}
