package tiles;

import tiles.text.LanguageObserver;

import java.util.ArrayList;
import java.util.List;

public class Config {
    private Config() {

    }

    private static class ConfigHolder {
        private static final Config INSTANCE = new Config();
    }

    public static Config getInstance() {
        return ConfigHolder.INSTANCE;
    }

    //Observer
    private final List<LanguageObserver> languageObservers = new ArrayList<>();

    public void subscribe(LanguageObserver observer) {
        languageObservers.add(observer);
    }

    private void updateLanguage() {
        for (LanguageObserver languageObserver : languageObservers) {
            languageObserver.update(getLanguage());
        }
    }

    //Getters and Setters
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
        updateLanguage();
    }

    private Language language = Language.ENGLISH;
}
