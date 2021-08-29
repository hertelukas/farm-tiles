package com.lukas.tiles.viewModel;

import com.lukas.tiles.Config;
import com.lukas.tiles.Language;
import com.lukas.tiles.SceneLoader;
import com.lukas.tiles.model.setup.*;
import com.lukas.tiles.text.LanguageObserver;
import com.lukas.tiles.view.game.GameView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The ViewModel for the setup.
 * Responsible for controlling all button presses and displayed information
 */
public class SetupViewModel implements LanguageObserver {
    private final Setup setup;

    /**
     * @param config the config object - autowired
     */
    public SetupViewModel(Config config) {
        config.subscribe(this);
        setup = new Setup();
        setup.setDifficulty(Difficulty.Easy);

        gameName.addListener((observable, oldValue, newValue) -> setup.setName(newValue));
    }

    private final StringProperty title = new SimpleStringProperty("Setup");
    private final StringProperty play = new SimpleStringProperty("Play");
    private final StringProperty mapSettings = new SimpleStringProperty("Map");
    private final StringProperty gameSettings = new SimpleStringProperty("Game");
    private final StringProperty gameName = new SimpleStringProperty();
    private final StringProperty nameHelper = new SimpleStringProperty("Name...");

    /**
     * Updates its content based on the specified language
     *
     * @param language the newly chosen language
     */
    @Override
    public void update(Language language) {
        switch (language) {
            case Deutsch -> {
                title.set("Einrichten");
                play.set("Spielen");
                mapSettings.set("Karte");
                gameSettings.set("Spieleinstellungen");
            }
            case English -> {
                title.set("Setup");
                play.set("Play");
                mapSettings.set("Map");
                gameSettings.set("Game");
            }
        }
    }

    /**
     * @return the title of the setup view as string
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * @return the title of the setup view
     */
    public StringProperty titleProperty() {
        return title;
    }

    /**
     * @return the text in the play button
     */
    public StringProperty playProperty() {
        return play;
    }

    /**
     * @return the text for the map settings title
     */
    public StringProperty mapSettingsProperty() {
        return mapSettings;
    }

    /**
     * @return the text for the game settings title
     */
    public StringProperty gameSettingsProperty() {
        return gameSettings;
    }

    /**
     * This updates the game name, bidirectional
     *
     * @return the property controlling the current game name
     */
    public StringProperty gameNameProperty() {
        return gameName;
    }

    /**
     * @return a help text to be shown in a JavaFX TextField
     */
    public StringProperty nameHelperProperty() {
        return nameHelper;
    }

    //Actions

    /**
     * Handles the play button press
     */
    public void play() {
        SceneLoader.getInstance().loadScene(new GameView(setup));
    }

    /**
     * Change the map size
     *
     * @param selectedItem the chosen map size
     */
    public void setMapSize(MapSize selectedItem) {
        setup.setMapSize(selectedItem);
    }

    /**
     * Change the map type
     *
     * @param selectedItem the chosen map type
     */
    public void setMapType(MapType selectedItem) {
        setup.setMapType(selectedItem);
    }

    /**
     * Set the color representing the player
     *
     * @param color the chosen color
     */
    public void setColor(FarmerColor color) {
        setup.setColor(color);
    }

    /**
     * @param seed Sets a seed for the map generation
     */
    public void setSeed(long seed) {
        setup.setSeed(seed);
    }
}
