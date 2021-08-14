package tiles;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tiles.viewModel.menu.GameMenuFactory;

public class MedievalTilesApplication extends Application {

    private final static String MENU = "/fxml/menu.fxml";

    @Override
    public void start(Stage primaryStage) {
        ApplicationFactory applicationFactory = new ApplicationFactory();
        applicationFactory.build();

        GameMenuFactory menuFactory = new GameMenuFactory();

        Parent root = menuFactory.getMenu(applicationFactory.getConfig(), MENU);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();

    }

    public static void startApplication(String[] args) {
        launch(args);
    }
}
