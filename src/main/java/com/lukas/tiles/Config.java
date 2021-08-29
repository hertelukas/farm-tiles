package com.lukas.tiles;


import com.lukas.tiles.text.LanguageObserver;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Holds global application settings, implemented as service
 */
@Service
public class Config implements Serializable {
    @Serial
    private static final long serialVersionUID = 3463174116091515175L;
    private Language language = Language.English;

    //Observer
    private final transient List<LanguageObserver> languageObservers = new ArrayList<>();

    /**
     * @param observer that want to get notified about language changes
     */
    public void subscribe(LanguageObserver observer) {
        languageObservers.add(observer);
        observer.update(getLanguage());
    }

    private void updateLanguage() {
        for (LanguageObserver languageObserver : languageObservers) {
            languageObserver.update(getLanguage());
        }
    }

    //Getters and Setters

    /**
     * @return the current language
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * @param language that should be set for the entire application
     */
    public void setLanguage(Language language) {
        this.language = language;
        updateLanguage();
    }

    /**
     * This method updates all its fields based on a loaded config
     *
     * @param loadedConfig the config loaded from a serialized instance
     */
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
