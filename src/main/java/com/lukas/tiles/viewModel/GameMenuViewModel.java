package com.lukas.tiles.viewModel;

import com.lukas.tiles.Config;
import com.lukas.tiles.SceneLoader;
import com.lukas.tiles.io.ConfigHandler;
import com.lukas.tiles.text.MenuText;
import com.lukas.tiles.view.GameView;
import com.lukas.tiles.view.LoadView;
import com.lukas.tiles.view.SetupView;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
    private final Config config;

    public Button btPlay;
    public Button btLoad;
    public Button btSettings;
    public Button btExit;
    public Label lbFeedback;
    public ProgressBar pb;

    public GameMenuViewModel(Config config, MenuText menuText) {
        this.menuText = menuText;
        this.config = config;
    }

    @FXML
    public void initialize() {
        setupBindings();
        loadConfig();
    }

    private void loadConfig() {
        lbFeedback.setVisible(true);
        pb.setVisible(true);

        lbFeedback.setText("Loading config...");

        Task<Config> task = new Task<>() {
            @Override
            protected Config call() throws Exception {
                updateProgress(0.4d, 1.0d);
                Config temp = ConfigHandler.load();
                updateProgress(1, 1);
                config.setConfig(temp);
                return temp;
            }

            @Override
            protected void succeeded() {
                updateProgress(1, 1);
                lbFeedback.setVisible(false);
                pb.setVisible(false);
            }

            @Override
            protected void failed() {
                pb.setVisible(false);
                lbFeedback.setText("Failed to load game files!");
            }
        };

        pb.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    private void setupBindings() {
        btPlay.textProperty().bind(menuText.playProperty());
        btLoad.textProperty().bind(menuText.loadProperty());
        btSettings.textProperty().bind(menuText.settingsProperty());
        btExit.textProperty().bind(menuText.exitProperty());
    }

    @FXML
    private void play() {
        SceneLoader.getInstance().loadScene(new SetupView(config));
    }

    @FXML
    private void openSettings() {
        try {
            SceneLoader.getInstance().loadScene(SETTINGS_PAGE);
        } catch (IOException e) {
            lbFeedback.setVisible(true);
            lbFeedback.setText("Failed to load Settings");
            e.printStackTrace();
        }
    }

    @FXML
    private void load() {
        SceneLoader.getInstance().loadScene(new LoadView(config));
    }

    @FXML
    private void exit() {
        Platform.exit();
    }
}
