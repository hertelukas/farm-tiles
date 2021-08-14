package tiles.viewModel.menu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tiles.config.Config;
import tiles.text.MenuText;

import java.io.IOException;
import java.util.Objects;

public class GameMenuFactory {

    private GameMenuViewModel gameMenuViewModel;

    public Parent getMenu(Config config, String fxmlLocation) {
        MenuText menuText = new MenuText(config);
        gameMenuViewModel = new GameMenuViewModel(config);

        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(fxmlLocation)));
            loader.getNamespace().put("menuText", menuText);
            return loader.load();
        } catch (IOException e) {
            VBox vBox = new VBox();
            Label label = new Label("Failed to load application");
            vBox.getChildren().add(label);
            return vBox;
        }
    }

    public GameMenuViewModel getGameMenuViewModel() {
        return gameMenuViewModel;
    }
}
