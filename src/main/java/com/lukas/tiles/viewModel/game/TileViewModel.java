package com.lukas.tiles.viewModel.game;

import com.lukas.tiles.io.GameHandler;
import com.lukas.tiles.model.*;
import com.lukas.tiles.model.building.Building;
import com.lukas.tiles.model.building.BuildingEnum;
import com.lukas.tiles.model.building.Headquarter;
import com.lukas.tiles.view.BasicObserver;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The ViewModel for a tile.
 * <p>
 * Responsible for controlling all button presses and displayed information.
 */
public class TileViewModel {
    private final Tile tile;
    private final Game game;
    private final StringProperty buyButtonText = new SimpleStringProperty();
    private final StringProperty feedbackText = new SimpleStringProperty();
    private final BooleanProperty canBeBought = new SimpleBooleanProperty(false);
    private EventHandler<ActionEvent> onBuyButtonPress;
    private final List<BasicObserver> observers;


    /**
     * Creates a new TileViewModel based on a given tile and game.
     *
     * @param tile Tile that should be represented
     * @param game Tile that should be represented
     */
    public TileViewModel(Tile tile, Game game) {
        this.tile = tile;
        this.game = game;
        observers = new ArrayList<>();
    }

    /**
     * @param observer The subscriber, who wants to get notified if the state of the TileViewModel changes
     */
    public void subscribe(BasicObserver observer) {
        observers.add(observer);
    }

    /**
     * @return get the name of a given tile
     */
    public StringProperty getName() {
        return tile.nameProperty();
    }

    /**
     * Handle the buy process of a building on a given tile.
     *
     * @param building that should be build
     * @return whether the transaction was successful
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean buyBuilding(Building building) {
        //Check if enough money
        if (game.getFarmers().get(0).buyBuilding(building)) {
            tile.setOwner(game.getFarmers().get(0));
            tile.setBuilding(building);
            game.startBuilding(building);
            if (building instanceof Headquarter) {
                game.getFarmers().get(0).setHeadquarter();
            }
            GameHandler.save(game);
            return true;
        }
        //Payment failed
        return false;
    }

    /**
     * @return get the building build on this tile
     */
    public Building getBuilding() {
        return tile.getBuilding();
    }

    /**
     * @return whether a building is being build or stands on this tile
     */
    public boolean hasBuilding() {
        return getBuilding() != null;
    }

    /**
     * @return a set of adjacent tile types
     */
    public Set<TileType> getNeighbourTileTypes() {
        Set<TileType> result = new HashSet<>();
        for (Tile tile1 : game.getMap().getAdjacent(tile)) {
            result.add(tile1.getTileType());
        }

        return result;
    }

    /**
     * @return the money instance of the current player
     */
    public Money getPlayerMoney() {
        return getFarmer().getMoney();
    }

    /**
     * @return the current player
     */
    public Farmer getFarmer() {
        return game.getFarmers().get(0);
    }

    /**
     * @return A table of all possible buildings on this tile
     */
    public TableView<BuildingEnum> generateBuildingsTable() {
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
        result.getItems().removeIf(buildingEnum -> !buildingEnum.canBeBuild(tile.getTileType(), getNeighbourTileTypes(), this.getFarmer()));

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

        //Set on selection behaviour
        result.getSelectionModel().getSelectedItems().addListener((ListChangeListener<BuildingEnum>) c -> {
            BuildingEnum selected = c.getList().get(0);
            buyButtonText.set("Buy " + selected.getName());
            canBeBought.set(selected.getPrice().getAmount() <= getPlayerMoney().getAmount());
            onBuyButtonPress = event -> {
                if (!buyBuilding(selected.instantiate(tile))) {
                    feedbackText.set("Failed to buy building");
                } else {
                    //Promote update to notify the view about the new building
                    promoteUpdate();
                }
            };
            //Promote the update that the selection has changed
            promoteUpdate();
        });

        return result;
    }

    /**
     * @return Text for more information.
     */
    public StringProperty feedbackTextProperty() {
        return feedbackText;
    }

    /**
     * @return Text in the buy button.
     */
    public StringProperty buyButtonTextProperty() {
        return buyButtonText;
    }

    /**
     * @return Whether the selected item can be bought or not.
     */
    public BooleanProperty canBeBoughtProperty() {
        return canBeBought;
    }

    private void promoteUpdate() {
        for (BasicObserver observer : observers) {
            observer.update();
        }
    }

    /**
     * @return the action that should be performed on the buy button press
     */
    public EventHandler<ActionEvent> getOnBuyButtonPress() {
        return onBuyButtonPress;
    }
}
