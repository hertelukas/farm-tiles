package com.lukas.tiles.viewModel;

import com.lukas.tiles.SceneLoader;
import com.lukas.tiles.text.MenuText;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GameMenuViewModel {

    private final static String SETTINGS_PAGE = "/fxml/settings.fxml";


    private final MenuText menuText;

    public Button btPlay;
    public Button btLoad;
    public Button btSettings;
    public Button btExit;
    public Label lbFeedback;
    public ProgressBar pb;

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
        try {
            SceneLoader.getInstance().loadScene(SETTINGS_PAGE);
        } catch (IOException e) {
            lbFeedback.setDisable(false);
            lbFeedback.setText("Failed to load Settings");
        }
    }

    @FXML
    private void load() {

    }

    @FXML
    private void exit() {
        Platform.exit();
    }
}
