package com.lukas.farmtiles;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Objects;

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
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(START_PAGE)));
        fxmlLoader.setControllerFactory(springContext::getBean);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    @Override
    public void stop() {
        springContext.stop();
    }
}
