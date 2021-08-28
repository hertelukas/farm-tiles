package com.lukas.tiles.view;

import com.lukas.tiles.Config;
import com.lukas.tiles.FarmTilesApplication;
import com.lukas.tiles.SceneLoader;
import com.lukas.tiles.model.setup.FarmerColor;
import com.lukas.tiles.model.setup.MapSize;
import com.lukas.tiles.model.setup.MapType;
import com.lukas.tiles.viewModel.SetupViewModel;
import com.sun.javafx.collections.ImmutableObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Visual representation of the setup. Lets the user configure a new game
 */
public class SetupView extends VBox {

    private final SetupViewModel setupViewModel;
    Button playButton;

    /**
     * Creates a new SetupView and instantiates a SetupViewModel
     *
     * @param config Configuration file of the game
     */
    public SetupView(Config config) {
        setupViewModel = new SetupViewModel(config);
        initialize();
    }

    private void initialize() {
        this.getStylesheets().add(Style.getMainStyle());

        //VBox setup
        this.setSpacing(Style.getVSpacing());
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(Style.getPADDING());

        playButton = new Button();
        playButton.textProperty().bind(setupViewModel.playProperty());
        playButton.setOnAction(e -> setupViewModel.play());

        Label feedback = new Label();
        feedback.setVisible(false);

        //Content setup
        Label label = new Label();
        label.textProperty().bind(setupViewModel.titleProperty());
        label.getStyleClass().add("h1");
        this.getChildren().add(label);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(Style.getHSpacing());
        VBox mapSettingsVBox = generateMapSettingsVBox();
        VBox gameSettingsVBox = generateGameSettingsVBox();

        hBox.getChildren().addAll(mapSettingsVBox, new Separator(Orientation.VERTICAL), gameSettingsVBox);
        this.getChildren().add(hBox);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            try {
                SceneLoader.getInstance().loadScene(FarmTilesApplication.getStartPage());
            } catch (IOException ioException) {
                feedback.setText("Failed to load main menu");
                feedback.setVisible(true);
                ioException.printStackTrace();
            }
        });

        this.getChildren().addAll(playButton, backButton, feedback);
    }

    private VBox generateMapSettingsVBox() {
        VBox result = new VBox();
        result.setAlignment(Pos.TOP_CENTER);
        result.setSpacing(Style.getVSpacing());

        //Title
        Label label = new Label();
        label.textProperty().bind(setupViewModel.mapSettingsProperty());
        label.getStyleClass().add("h2");

        //Size choice Box
        ChoiceBox<MapSize> mapSizeChoiceBox = new ChoiceBox<>();
        mapSizeChoiceBox.setItems(new ImmutableObservableList<>(MapSize.values()));
        mapSizeChoiceBox.getSelectionModel().select(MapSize.getDefault());
        mapSizeChoiceBox.setOnAction(e -> setupViewModel.setMapSize(mapSizeChoiceBox.getSelectionModel().getSelectedItem()));

        ChoiceBox<MapType> mapTypeChoiceBox = new ChoiceBox<>();
        mapTypeChoiceBox.setItems(new ImmutableObservableList<>(MapType.values()));
        mapTypeChoiceBox.getSelectionModel().select(MapType.getDefault());
        mapTypeChoiceBox.setOnAction(e -> setupViewModel.setMapType(mapTypeChoiceBox.getSelectionModel().getSelectedItem()));

        ChoiceBox<FarmerColor> colorPicker = new ChoiceBox<>();
        colorPicker.setItems(new ImmutableObservableList<>(FarmerColor.values()));
        colorPicker.getSelectionModel().select(FarmerColor.getDefault());
        colorPicker.setOnAction(e -> setupViewModel.setColor(colorPicker.getSelectionModel().getSelectedItem()));

        result.getChildren().addAll(label, mapSizeChoiceBox, mapTypeChoiceBox, colorPicker);
        return result;
    }

    private VBox generateGameSettingsVBox() {
        VBox result = new VBox();
        result.setAlignment(Pos.TOP_CENTER);
        result.setSpacing(Style.getVSpacing());

        //Title
        Label label = new Label();
        label.textProperty().bind(setupViewModel.gameSettingsProperty());
        label.getStyleClass().add("h2");
        result.getChildren().add(label);

        TextField nameInput = new TextField();
        nameInput.promptTextProperty().bind(setupViewModel.nameHelperProperty());
        nameInput.textProperty().bindBidirectional(setupViewModel.gameNameProperty());
        result.getChildren().add(nameInput);
        playButton.disableProperty().bind(setupViewModel.gameNameProperty().isEmpty());
        return result;
    }

}
