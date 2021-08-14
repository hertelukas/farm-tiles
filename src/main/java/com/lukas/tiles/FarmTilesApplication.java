package com.lukas.tiles;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FarmTilesApplication extends Application {

    private final static String START_PAGE = "/fxml/startPage.fxml";

    ConfigurableApplicationContext springContext;

    public static void startApplication(String[] args) {
        launch(FarmTilesApplication.class, args);
    }

    @Override
    public void init() {
        springContext = SpringApplication.run(FarmTilesApplication.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneLoader.getInstance().init(primaryStage, springContext);
        SceneLoader.getInstance().loadScene(START_PAGE);
    }

    @Override
    public void stop() {
        springContext.stop();
    }

}
