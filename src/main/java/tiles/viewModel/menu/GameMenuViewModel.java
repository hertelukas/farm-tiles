package tiles.viewModel.menu;

import javafx.fxml.FXML;
import tiles.config.Config;
import tiles.config.Language;

public final class GameMenuViewModel {

    private final Config config;

    public GameMenuViewModel(Config config) {
        this.config = config;
    }

    @FXML
    private void play() {
        config.setLanguage(Language.GERMAN);
    }

    @FXML
    private void openSettings() {
        config.setLanguage(Language.ENGLISH);
    }

    @FXML
    private void load() {

    }

    @FXML
    private void exit() {

    }
}
