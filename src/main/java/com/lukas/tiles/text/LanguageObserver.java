package com.lukas.tiles.text;

import com.lukas.tiles.Language;

/**
 * Should be implemented by all ModeViews that have to keep track of the language
 */
public interface LanguageObserver {
    void update(Language language);
}
