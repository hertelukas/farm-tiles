package com.lukas.tiles.text;

import com.lukas.tiles.Config;
import com.lukas.tiles.Language;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.stereotype.Service;

/**
 * All text data for the main menu
 */
@Service
public class MenuText implements LanguageObserver {

    public MenuText(Config config) {
        config.subscribe(this);
    }

    @Override
    public void update(Language language) {
        switch (language) {
            case English -> {
                setPlay("Play");
                setLoad("Load");
                setSettings("Settings");
                setExit("Exit");
            }
            case Deutsch -> {
                setPlay("Spielen");
                setLoad("Laden");
                setSettings("Einstellungen");
                setExit("Beenden");
            }
        }
    }

    private final StringProperty play = new SimpleStringProperty("Play");
    private final StringProperty load = new SimpleStringProperty("Load");
    private final StringProperty settings = new SimpleStringProperty("Settings");
    private final StringProperty exit = new SimpleStringProperty("Exit");

    public StringProperty playProperty() {
        return play;
    }

    private void setPlay(String play) {
        this.play.set(play);
    }

    public StringProperty loadProperty() {
        return load;
    }

    private void setLoad(String load) {
        this.load.set(load);
    }

    public StringProperty settingsProperty() {
        return settings;
    }

    private void setSettings(String settings) {
        this.settings.set(settings);
    }

    public StringProperty exitProperty() {
        return exit;
    }

    private void setExit(String exit) {
        this.exit.set(exit);
    }
}
