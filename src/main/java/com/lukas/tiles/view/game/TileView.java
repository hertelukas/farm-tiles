package com.lukas.tiles.view.game;

import com.lukas.tiles.model.Game;
import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.building.BuildingEnum;
import com.lukas.tiles.view.BasicObserver;
import com.lukas.tiles.view.Style;
import com.lukas.tiles.viewModel.game.TileViewModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

/**
 * The tileView is the visual representation of a tile
 */
public class TileView extends VBox implements BasicObserver {
    private final TileViewModel tileViewModel;
    private Button buyButton;
    private TableView<BuildingEnum> buildingsTableView;
    private boolean isShowingBuyMenu = false;

    /**
     * Instantiates a new tile view and initializes all components
     *
     * @param tile that should be represented
     * @param game the current game
     */
    public TileView(Tile tile, Game game) {
        this.tileViewModel = new TileViewModel(tile, game);
        this.tileViewModel.subscribe(this);
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
        label.textProperty().bind(tileViewModel.getName());
        label.getStyleClass().add("h1");
        this.getChildren().add(label);


        if (tileViewModel.hasBuilding()) {
            this.getChildren().add(tileViewModel.getBuilding().getDescription());
        } else {
            isShowingBuyMenu = true;
            buildingsTableView = tileViewModel.generateBuildingsTable();

            buyButton = new Button();
            buyButton.textProperty().bind(tileViewModel.buyButtonTextProperty());
            buyButton.disableProperty().bind(tileViewModel.canBeBoughtProperty().not());

            //Disable button if nothing is selected
            buyButton.visibleProperty().bind(buildingsTableView.getSelectionModel().selectedItemProperty().isNotNull());


            this.getChildren().addAll(buildingsTableView, buyButton);
        }

        Label feedback = new Label();
        feedback.textProperty().bind(tileViewModel.feedbackTextProperty());
        feedback.visibleProperty().bind(tileViewModel.feedbackTextProperty().isNotEmpty());

        this.getChildren().add(feedback);
    }

    /**
     * Implements the handling of the changes occurred by an update of the TileViewModel
     */
    @Override
    public void update() {
        if (buyButton == null) {
            System.out.println("Button is null, can not be updated. Initialize has to be called first");
            return;
        }

        if (isShowingBuyMenu && tileViewModel.hasBuilding()) {
            isShowingBuyMenu = false;
            this.getChildren().remove(buyButton);
            this.getChildren().remove(buildingsTableView);
            this.getChildren().add(1, tileViewModel.getBuilding().getDescription());
        }

        buyButton.setOnAction(tileViewModel.getOnBuyButtonPress());
    }


}
