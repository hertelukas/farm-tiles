package com.lukas.tiles;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SceneLoader {

    private SceneLoader() {

    }

    private static class SceneLoaderHolder {
        private static final SceneLoader INSTANCE = new SceneLoader();
    }

    public static SceneLoader getInstance() {
        return SceneLoaderHolder.INSTANCE;
    }

    private Stage stage;
    private ConfigurableApplicationContext context;

    public void init(Stage stage, ConfigurableApplicationContext context) {

        if (this.stage != null) {
            throw new RuntimeException("Stage is already set");
        }
        this.stage = stage;
        this.context = context;
    }

    public void loadScene(String fxmlFile) throws IOException {
        if (stage == null) {
            throw new RuntimeException("Stage is not set. Set a stage first");
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        loader.setControllerFactory(context::getBean);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
    }
}
