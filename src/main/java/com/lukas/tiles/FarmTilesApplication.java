package com.lukas.tiles;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.logging.Logger;

/**
 * The main springboot application, starts a JavaFX application
 */
@SpringBootApplication
public class FarmTilesApplication extends Application {

    private final static String START_PAGE = "/fxml/startPage.fxml";
    private static Logger logger = Logger.getLogger("FarmTilesApplication");

    ConfigurableApplicationContext springContext;

    public static void startApplication(String[] args) {
        launch(FarmTilesApplication.class, args);
    }

    /**
     * Initializes the application and loads all fonts
     */
    @Override
    public void init() {
        springContext = SpringApplication.run(FarmTilesApplication.class);
        Font.loadFont(getClass().getResourceAsStream("/fxml/fonts/static/Raleway-Regular.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("/fxml/fonts/static/Raleway-Medium.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("/fxml/fonts/static/Raleway-Bold.ttf"), 16);

        logger.info("Loaded all fonts.");
    }

    /**
     * Initial startup of the application
     *
     * @param primaryStage the stage that will be displayed
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Farm Tiles");
        SceneLoader.getInstance().init(primaryStage, springContext);
        SceneLoader.getInstance().loadScene(START_PAGE);
    }

    /**
     * Handles the stopping of the application
     */
    @Override
    public void stop() {
        springContext.stop();
        System.exit(0); // TODO: 8/29/21 this might not be the best way
    }

    /**
     * @return the location of the fxml file for the main men
     */
    public static String getStartPage() {
        return START_PAGE;
    }
}
