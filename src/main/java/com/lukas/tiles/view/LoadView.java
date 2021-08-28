package com.lukas.tiles.view;

import com.lukas.tiles.Config;
import com.lukas.tiles.viewModel.LoadViewModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * A visual representation of the load games menu
 */
public class LoadView extends VBox {
    private final LoadViewModel loadViewModel;

    /**
     * @param config Configuration file of the game
     */
    public LoadView(Config config) {
        loadViewModel = new LoadViewModel(config);
        initialize();
    }

    private void initialize() {
        this.getStylesheets().add(Style.getMainStyle());

        //VBox setup
        this.setSpacing(Style.getVSpacing());
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(Style.getPADDING());

        //Content setup
        Label label = new Label();
        label.textProperty().bind(loadViewModel.titlePropertyProperty());
        label.getStyleClass().add("h1");
        this.getChildren().add(label);

        this.getChildren().addAll(loadViewModel.generateLoadMenu());

        Button button = new Button();
        button.textProperty().bind(loadViewModel.backProperty());
        button.setOnAction(loadViewModel::goBack);
        this.getChildren().add(button);

        Label feedback = new Label();
        feedback.visibleProperty().bind(loadViewModel.feedbackTextProperty().isNotEmpty());
        feedback.textProperty().bind(loadViewModel.feedbackTextProperty());

        this.getChildren().add(feedback);
    }
}

