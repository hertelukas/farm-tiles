package com.lukas.farmtiles.text;

import com.lukas.farmtiles.Config;
import com.lukas.farmtiles.Language;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.stereotype.Service;

@Service
public class MenuText implements LanguageObserver {

    public MenuText(Config config) {
        config.subscribe(this);
    }

    @Override
    public void update(Language language) {
        switch (language) {
            case ENGLISH -> {
                setPlay("Play");
                setLoad("Load");
                setSettings("Settings");
                setExit("Exit");
            }
            case GERMAN -> {
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

    public String getPlay() {
        return play.get();
    }

    private void setPlay(String play) {
        this.play.set(play);
    }

    public StringProperty loadProperty() {
        return load;
    }

    public String getLoad() {
        return load.get();
    }

    private void setLoad(String load) {
        this.load.set(load);
    }

    public StringProperty settingsProperty() {
        return settings;
    }

    public String getSettings() {
        return settings.get();
    }

    private void setSettings(String settings) {
        this.settings.set(settings);
    }

    public StringProperty exitProperty() {
        return exit;
    }

    public String getExit() {
        return exit.get();
    }

    private void setExit(String exit) {
        this.exit.set(exit);
    }
}
