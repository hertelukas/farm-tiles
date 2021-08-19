package com.lukas.tiles.viewModel;

import com.lukas.tiles.Config;
import com.lukas.tiles.Language;
import com.lukas.tiles.text.LanguageObserver;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoadViewModel implements LanguageObserver {

    public LoadViewModel(Config config) {
        config.subscribe(this);
    }

    private final StringProperty titleProperty = new SimpleStringProperty("Load Game");
    private final StringProperty back = new SimpleStringProperty("Back");

    @Override
    public void update(Language language) {
        switch (language) {
            case Deutsch -> {
                titleProperty.set("Spiel laden");
                back.set("Zurück");
            }
            case English -> {
                titleProperty.set("Load Game");
                back.set("Back");
            }
        }
    }

    public StringProperty titlePropertyProperty() {
        return titleProperty;
    }

    public String getTitleProperty() {
        return titleProperty.get();
    }
}
