package tiles.viewModel;

import javafx.fxml.FXML;
import tiles.Config;
import tiles.Language;

public class GameMenuViewModel {
    @FXML
    private void play() {
        Config.getInstance().setLanguage(Language.GERMAN);
    }

    @FXML
    private void openSettings() {

    }

    @FXML
    private void load() {

    }

    @FXML
    private void exit() {

    }
}
