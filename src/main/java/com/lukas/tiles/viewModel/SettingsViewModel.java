package com.lukas.tiles.viewModel;

import com.lukas.tiles.Config;
import com.lukas.tiles.ConfigAction;
import com.lukas.tiles.Language;
import com.lukas.tiles.text.SettingsText;
import com.sun.javafx.collections.ImmutableObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettingsViewModel {

    @FXML
    private Label lblTitle;
    List<ConfigAction> configActions;

    @FXML
    private ChoiceBox<Language> languageChoiceBox;

    private final Config config;
    private final SettingsText settingsText;

    public SettingsViewModel(Config config, SettingsText settingsText) {
        this.config = config;
        this.settingsText = settingsText;
        configActions = new ArrayList<>();
    }

    @FXML
    private void initialize() {
        setupBindings();
        initLanguageChoiceBox();
    }

    private void setupBindings() {
        lblTitle.textProperty().bind(settingsText.titleProperty());
    }

    private void initLanguageChoiceBox() {
        languageChoiceBox.setItems(new ImmutableObservableList<>(Language.values()));
        languageChoiceBox.getSelectionModel().select(config.getLanguage());
    }

    @FXML
    private void setLanguage() {
        configActions.add(() -> config.setLanguage(languageChoiceBox.getSelectionModel().getSelectedItem()));
    }

    @FXML
    private void save() {
        for (ConfigAction configAction : configActions) {
            configAction.apply();
        }
    }

}
