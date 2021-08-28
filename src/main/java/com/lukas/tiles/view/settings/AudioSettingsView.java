package com.lukas.tiles.view.settings;

import com.lukas.tiles.viewModel.SettingsViewModel;
import javafx.beans.property.StringProperty;

/**
 * Used for any audio settings
 */
public class AudioSettingsView extends AbstractSettingsView {

    /**
     * @param settingsViewModel that controls all different settings
     * @param title             of the current menu
     */
    public AudioSettingsView(SettingsViewModel settingsViewModel, StringProperty title) {
        super(settingsViewModel, title);
    }

    /**
     * initializes all UI components and wires them to the settingsViewModel
     */
    @Override
    void initialize() {

    }

}
