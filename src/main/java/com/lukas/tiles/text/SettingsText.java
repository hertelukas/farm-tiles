package com.lukas.tiles.text;

import com.lukas.tiles.Config;
import com.lukas.tiles.Language;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.stereotype.Service;

@Service
public class SettingsText implements LanguageObserver {

    public SettingsText(Config config) {
        config.subscribe(this);
    }

    @Override
    public void update(Language language) {
        switch (language) {
            case ENGLISH -> {
                title.set("Settings");
            }
            case GERMAN -> {
                title.set("Einstellungen");
            }
        }
    }

    private final StringProperty title = new SimpleStringProperty("Settings");

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }
}
