package com.lukas.tiles.text;

import com.lukas.tiles.Config;
import com.lukas.tiles.Language;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.stereotype.Service;

/**
 * All text data for the settingss
 */
@Service
public class SettingsText implements LanguageObserver {

    public SettingsText(Config config) {
        config.subscribe(this);
    }

    @Override
    public void update(Language language) {
        switch (language) {
            case English -> {
                title.set("Settings");
                general.set("General");
                home.set("Home");
                saveHome.set("Save & Home");
                save.set("Save");
            }
            case Deutsch -> {
                title.set("Einstellungen");
                general.set("Allgemein");
                home.set("Zurück");
                saveHome.set("Speicher & Zurück");
                save.set("Speichern");
            }
        }
    }

    private final StringProperty title = new SimpleStringProperty("Settings");
    private final StringProperty general = new SimpleStringProperty("General");
    private final StringProperty audio = new SimpleStringProperty("Audio");
    private final StringProperty save = new SimpleStringProperty("Save");
    private final StringProperty home = new SimpleStringProperty("Home");
    private final StringProperty saveHome = new SimpleStringProperty("Save & Home");

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getGeneral() {
        return general.get();
    }

    public StringProperty generalProperty() {
        return general;
    }

    public String getAudio() {
        return audio.get();
    }

    public StringProperty audioProperty() {
        return audio;
    }

    public String getHome() {
        return home.get();
    }

    public StringProperty homeProperty() {
        return home;
    }

    public String getSaveHome() {
        return saveHome.get();
    }

    public StringProperty saveHomeProperty() {
        return saveHome;
    }

    public String getSave() {
        return save.get();
    }

    public StringProperty saveProperty() {
        return save;
    }
}
