package com.lukas.farmtiles.viewModel;

import com.lukas.farmtiles.Config;
import com.lukas.farmtiles.Language;
import com.lukas.farmtiles.text.MenuText;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Service;

@Service
public class GameMenuViewModel {

    private final Config config;
    private final MenuText menuText;

    public Button btPlay;
    public Button btLoad;
    public Button btSettings;
    public Button btExit;

    public GameMenuViewModel(Config config, MenuText menuText) {
        this.config = config;
        this.menuText = menuText;
    }

    @FXML
    public void initialize() {
        btPlay.textProperty().bind(menuText.playProperty());
        btLoad.textProperty().bind(menuText.loadProperty());
        btSettings.textProperty().bind(menuText.settingsProperty());
        btExit.textProperty().bind(menuText.exitProperty());
    }

    @FXML
    private void play() {
        config.setLanguage(Language.GERMAN);
    }

    @FXML
    private void openSettings() {

    }

    @FXML
    private void load() {

    }

    @FXML
    private void exit() {

    }
}
