package com.lukas.tiles.viewModel;

import com.lukas.tiles.Config;
import com.lukas.tiles.Language;
import com.lukas.tiles.SceneLoader;
import com.lukas.tiles.model.Setup;
import com.lukas.tiles.model.setup.Difficulty;
import com.lukas.tiles.model.setup.MapSize;
import com.lukas.tiles.text.LanguageObserver;
import com.lukas.tiles.view.GameView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SetupViewModel implements LanguageObserver {
    private final Setup setup;

    public SetupViewModel(Config config) {
        config.subscribe(this);
        setup = new Setup();
        setup.setDifficulty(Difficulty.Easy);
    }

    private final StringProperty title = new SimpleStringProperty("Setup");
    private final StringProperty play = new SimpleStringProperty("Play");
    private final StringProperty mapSettings = new SimpleStringProperty("Map");
    private final StringProperty gameSettings = new SimpleStringProperty("Game");

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

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getPlay() {
        return play.get();
    }

    public StringProperty playProperty() {
        return play;
    }

    public void play() {
        SceneLoader.getInstance().loadScene(new GameView(setup));
    }

    public String getMapSettings() {
        return mapSettings.get();
    }

    public StringProperty mapSettingsProperty() {
        return mapSettings;
    }

    public String getGameSettings() {
        return gameSettings.get();
    }

    public StringProperty gameSettingsProperty() {
        return gameSettings;
    }


    //Actions
    public void setMapSize(MapSize selectedItem) {
        setup.setMapSize(selectedItem);
    }
}
