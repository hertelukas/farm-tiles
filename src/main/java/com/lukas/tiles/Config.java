package com.lukas.tiles;


import com.lukas.tiles.text.LanguageObserver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class Config {
    //Observer
    private final transient List<LanguageObserver> languageObservers = new ArrayList<>();

    public void subscribe(LanguageObserver observer) {
        languageObservers.add(observer);
        observer.update(getLanguage());
    }

    private void updateLanguage() {
        for (LanguageObserver languageObserver : languageObservers) {
            languageObserver.update(getLanguage());
        }
    }

    //Fields
    private Language language = Language.English;

    //Getters and Setters
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
        updateLanguage();
    }

    public void setConfig(Config loadedConfig) {
        setLanguage(loadedConfig.getLanguage());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Config config = (Config) o;
        return language == config.language;
    }

    @Override
    public int hashCode() {
        return Objects.hash(language);
    }
}
