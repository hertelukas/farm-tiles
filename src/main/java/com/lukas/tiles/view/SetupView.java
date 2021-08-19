package com.lukas.tiles.view;

import com.lukas.tiles.Config;
import com.lukas.tiles.FarmTilesApplication;
import com.lukas.tiles.model.setup.MapSize;
import com.lukas.tiles.viewModel.SetupViewModel;
import com.sun.javafx.collections.ImmutableObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SetupView extends VBox {

    private final SetupViewModel setupViewModel;

    public SetupView(Config config) {
        this.getStylesheets().add(FarmTilesApplication.getMainStyle());
        setupViewModel = new SetupViewModel(config);
        initialize();
    }

    private void initialize() {
        //VBox setup
        this.setSpacing(Style.getVSpacing());
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(Style.getMARGIN());

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

        Button playButton = new Button();
        playButton.textProperty().bind(setupViewModel.playProperty());
        playButton.setOnAction(e -> setupViewModel.play());
        this.getChildren().add(playButton);
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


        return result;
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
        mapSizeChoiceBox.getSelectionModel().select(MapSize.Medium);
        mapSizeChoiceBox.setOnAction(e -> setupViewModel.setMapSize(mapSizeChoiceBox.getSelectionModel().getSelectedItem()));

        result.getChildren().addAll(label, mapSizeChoiceBox);
        return result;
    }


}
