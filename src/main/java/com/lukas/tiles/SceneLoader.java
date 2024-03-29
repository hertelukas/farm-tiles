package com.lukas.tiles;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

/**
 * The SceneLoader is realised as a singleton.
 * <p>
 * Responsible for loading different scenes.
 * Can load fxml files, stages and layouts.
 */
public class SceneLoader {

    Scene mainScene;
    BorderPane root;

    private SceneLoader() {

    }

    private static class SceneLoaderHolder {
        private static final SceneLoader INSTANCE = new SceneLoader();
    }

    /**
     * @return The Singleton instance of the SceneLoader
     */
    public static SceneLoader getInstance() {
        return SceneLoaderHolder.INSTANCE;
    }

    private Stage stage;
    private ConfigurableApplicationContext context;

    /**
     * @param stage   the main stage
     * @param context the springboot context
     */
    public void init(Stage stage, ConfigurableApplicationContext context) {

        if (this.stage != null) {
            throw new RuntimeException("Stage is already set");
        }
        this.stage = stage;
        this.context = context;

        root = new BorderPane();
        mainScene = new Scene(root);

        stage.setScene(mainScene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(new KeyCharacterCombination("F", KeyCombination.CONTROL_DOWN));
        stage.show();
    }

    /**
     * @param fxmlFile the fxml file to be loaded in the center, old children get removed
     * @throws IOException whether the loading fails
     */
    public void loadScene(String fxmlFile) throws IOException {
        if (stage == null) {
            throw new RuntimeException("Stage is not set. Set a stage first");
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        loader.setControllerFactory(context::getBean);
        Parent item = loader.load();

        root.getChildren().removeIf(node -> true);
        root.setCenter(item);
    }

    /**
     * @param item that should be displayed in the center, old children get removed
     */
    public void loadScene(Parent item) {
        root.getChildren().removeIf(node -> true);
        root.setCenter(item);
    }

    /**
     * @param stage The stage that should be shown as a child
     */
    public void loadStage(Stage stage) {
        stage.show();
    }

    /**
     * @return Returns the main stage of the application
     */
    public Stage getStage() {
        return stage;
    }
}
