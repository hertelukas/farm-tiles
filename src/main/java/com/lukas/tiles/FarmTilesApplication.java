package com.lukas.tiles;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FarmTilesApplication extends Application {

    private final static String START_PAGE = "/fxml/startPage.fxml";
    private final static String MAIN_STYLE = "/fxml/stylesheets/main.css";

    ConfigurableApplicationContext springContext;

    public static void startApplication(String[] args) {
        launch(FarmTilesApplication.class, args);
    }

    @Override
    public void init() {
        springContext = SpringApplication.run(FarmTilesApplication.class);
        Font.loadFont(getClass().getResourceAsStream("/fxml/fonts/static/Raleway-Regular.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("/fxml/fonts/static/Raleway-Medium.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("/fxml/fonts/static/Raleway-Bold.ttf"), 16);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Farm Tiles");
        SceneLoader.getInstance().init(primaryStage, springContext);
        SceneLoader.getInstance().loadScene(START_PAGE);
    }

    @Override
    public void stop() {
        springContext.stop();
    }

    public static String getStartPage() {
        return START_PAGE;
    }

    public static String getMainStyle() {
        return MAIN_STYLE;
    }
}
