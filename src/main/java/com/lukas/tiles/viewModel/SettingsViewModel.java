package com.lukas.tiles.viewModel;

import com.lukas.tiles.*;
import com.lukas.tiles.io.ConfigHandler;
import com.lukas.tiles.text.SettingsText;
import com.lukas.tiles.view.SideMenu;
import com.lukas.tiles.view.menu.MenuEntry;
import com.lukas.tiles.view.settings.AbstractSettingsView;
import com.lukas.tiles.view.settings.AudioSettingsView;
import com.lukas.tiles.view.settings.GeneralSettingsView;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The ViewModel for the settings menu. Controls a fxml file
 * Responsible for controlling all button presses and displayed information
 */
@Service
public class SettingsViewModel {

    @FXML
    private HBox root;

    private final List<ConfigAction> configActions;

    private final List<AbstractSettingsView> settings;


    private final SettingsText settingsText;
    private final Config config;

    /**
     * @param settingsText text information in the correct language - autowired
     * @param config       the configuration file of the application - autowired
     */
    public SettingsViewModel(SettingsText settingsText, Config config) {
        this.settingsText = settingsText;
        this.config = config;
        configActions = new ArrayList<>();
        settings = new ArrayList<>();
    }

    @FXML
    private void initialize() {
        settings.add(new GeneralSettingsView(this, settingsText.generalProperty()));
        settings.add(new AudioSettingsView(this, settingsText.audioProperty()));
        initSideMenu();
    }

    private void initSideMenu() {
        SideMenu sideMenu = new SideMenu(settingsText.titleProperty());
        root.getChildren().add(0, sideMenu);

        sideMenu.addMenuEntry(new MenuEntry(this::loadSettings, settingsText.generalProperty()));
        sideMenu.addMenuEntry(new MenuEntry(this::loadSettings, settingsText.audioProperty()));


        sideMenu.addBottomEntry(new MenuEntry(index -> goBack(), settingsText.homeProperty()));
        sideMenu.addBottomEntry(new MenuEntry(index -> save(), settingsText.saveProperty()));
        sideMenu.addBottomEntry(new MenuEntry(index -> {
            save();
            goBack();
        }, settingsText.saveHomeProperty()));

        loadSettings(0);

    }

    /**
     * Change the language of the application
     *
     * @param language that should be set
     */
    public void setLanguage(Language language) {
        addConfigAction(() -> config.setLanguage(language));
    }

    private void addConfigAction(ConfigAction action) {
        configActions.add(action);
    }

    @FXML
    private void save() {
        //TODO show progress here

        for (ConfigAction configAction : configActions) {
            configAction.apply();
        }
        ConfigHandler.save(config);
    }

    private void loadSettings(int index) {
        if (root.getChildren().size() > 1) {
            root.getChildren().remove(1);
        }
        root.getChildren().add(settings.get(index));
    }

    private void goBack() {
        try {
            SceneLoader.getInstance().loadScene(FarmTilesApplication.getStartPage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @return the config object
     */
    public Config getConfig() {
        return config;
    }
}
