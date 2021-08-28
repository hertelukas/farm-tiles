package com.lukas.tiles.view.game;

import com.lukas.tiles.FarmTilesApplication;
import com.lukas.tiles.model.*;
import com.lukas.tiles.model.building.BuildingEnum;
import com.lukas.tiles.view.Style;
import com.lukas.tiles.viewModel.game.TileViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.Set;

public class TileView extends VBox {
    private final TileViewModel tileViewModel;

    public TileView(Tile tile, Game game) {
        this.tileViewModel = new TileViewModel(tile, game);
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

        Label feedback = new Label();

        if (tileViewModel.hasBuilding()) {
            this.getChildren().add(tileViewModel.getBuilding().getDescription());
        } else {
            TableView<BuildingEnum> buildingsTableView = generateBuildingsTable(tileViewModel.getTileType(), tileViewModel.getNeighbourTileTypes(), tileViewModel.getFarmer());

            Button button = new Button();
            //Disable button if nothing is selected
            button.visibleProperty().bind(buildingsTableView.getSelectionModel().selectedItemProperty().isNotNull());

            buildingsTableView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<BuildingEnum>) c -> {
                button.setText("Buy " + c.getList().get(0).getName());
                button.setDisable(c.getList().get(0).getPrice().getAmount() > tileViewModel.getPlayerMoney().getAmount());
                button.setOnAction(e -> {
                    if (!tileViewModel.buyBuilding(c.getList().get(0).instantiate(tileViewModel.getTile()))) {
                        feedback.setVisible(true);
                        feedback.setText("Failed to buy building");
                    } else {
                        this.getChildren().remove(button);
                        this.getChildren().remove(buildingsTableView);
                        //Insert description in front of the feedback label
                        this.getChildren().add(1, tileViewModel.getBuilding().getDescription());
                    }
                });
            });

            this.getChildren().addAll(buildingsTableView, button);
        }


        feedback.setVisible(false);

        this.getChildren().add(feedback);
    }

    private TableView<BuildingEnum> generateBuildingsTable(TileType type, Set<TileType> neighbours, Farmer farmer) {
        TableView<BuildingEnum> result = new TableView<>();
        result.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn<BuildingEnum, String> colName = new TableColumn<>("Name");
        TableColumn<BuildingEnum, Money> colPrice = new TableColumn<>("Price");
        TableColumn<BuildingEnum, Integer> colBuildDuration = new TableColumn<>("Duration");
        TableColumn<BuildingEnum, Money> colMaintenance = new TableColumn<>("Maintenance");

        result.getColumns().add(colName);
        result.getColumns().add(colPrice);
        result.getColumns().add(colBuildDuration);
        result.getColumns().add(colMaintenance);

        result.getItems().addAll(BuildingEnum.values());
        result.getItems().removeIf(buildingEnum -> !buildingEnum.canBeBuild(type, neighbours, farmer));

        colName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        colPrice.setCellValueFactory(param -> new ObservableValueBase<>() {
            @Override
            public Money getValue() {
                return param.getValue().getPrice();
            }
        });
        colBuildDuration.setCellValueFactory(new PropertyValueFactory<>("buildTime"));
        colMaintenance.setCellValueFactory(param -> new ObservableValueBase<>() {
            @Override
            public Money getValue() {
                return param.getValue().getMaintenance();
            }
        });

        return result;
    }
}
