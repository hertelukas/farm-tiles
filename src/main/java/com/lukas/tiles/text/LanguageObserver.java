package com.lukas.tiles.text;

import com.lukas.tiles.Language;

/**
 * Should be implemented by all ViewModels that have to keep track of the language
 * Implements the push notification variant of the observer pattern
 */
public interface LanguageObserver {
    void update(Language language);
}
