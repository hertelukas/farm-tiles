package com.lukas.farmtiles.viewModel;

import com.lukas.farmtiles.text.MenuText;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Service;

@Service
public class GameMenuViewModel {

    private final MenuText menuText;

    public Button btPlay;
    public Button btLoad;
    public Button btSettings;
    public Button btExit;

    public GameMenuViewModel(MenuText menuText) {
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

    }

    @FXML
    private void openSettings() {

    }

    @FXML
    private void load() {

    }

    @FXML
    private void exit() {
        Platform.exit();
    }
}
