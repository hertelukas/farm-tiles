package com.lukas.tiles.view.game.tab;

import com.lukas.tiles.model.Farmer;
import com.lukas.tiles.model.building.Building;
import com.lukas.tiles.model.finance.UnmodifiableMoneyAccount;
import com.lukas.tiles.view.game.GameView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.ListChangeListener;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class OverviewView extends AbstractTabView {
    private final Farmer farmer;
    private final GameView gameView;

    public OverviewView(GameView gameView, Farmer farmer) {
        super(new SimpleStringProperty("Overview"));
        this.farmer = farmer;
        this.gameView = gameView;
        initialize();
    }

    @Override
    void initialize() {
        //Adding the table view
        TableView<Building> ownedBuildingsTable = new TableView<>();

        TableColumn<Building, String> colType = new TableColumn<>("Type");
        TableColumn<Building, UnmodifiableMoneyAccount> colMaintenance = new TableColumn<>("Maintenance cost");
        TableColumn<Building, String> colTileType = new TableColumn<>("Tile Type");
        TableColumn<Building, Integer> colTileId = new TableColumn<>("Tile ID");

        // TODO: 8/27/21 Better name for multiple language support
        colType.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getClass().getSimpleName()));
        colMaintenance.setCellValueFactory(param -> new ObservableValueBase<>() {
            @Override
            public UnmodifiableMoneyAccount getValue() {
                return param.getValue().getPrice();
            }
        });
        colTileType.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTile().getTileType().toString()));
        colTileId.setCellValueFactory(param -> new ObservableValueBase<>() {
            @Override
            public Integer getValue() {
                return param.getValue().getTile().getId();
            }
        });

        ownedBuildingsTable.getColumns().add(colType);
        ownedBuildingsTable.getColumns().add(colMaintenance);
        ownedBuildingsTable.getColumns().add(colTileType);
        ownedBuildingsTable.getColumns().add(colTileId);

        ownedBuildingsTable.getItems().addAll(farmer.getBuildings());
        ownedBuildingsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ownedBuildingsTable.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Building>) c -> gameView.showTile(c.getList().get(0).getTile()));
        this.getChildren().add(ownedBuildingsTable);
    }
}
