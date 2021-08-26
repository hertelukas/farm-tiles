package com.lukas.tiles.view.game.tab;

import com.lukas.tiles.model.Farmer;
import com.lukas.tiles.model.building.Building;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;

public class OverviewView extends AbstractTabView {
    private final Farmer farmer;

    public OverviewView(Farmer farmer) {
        super(new SimpleStringProperty("Overview"));
        this.farmer = farmer;
        initialize();
    }

    @Override
    void initialize() {
        for (Building building : farmer.getBuildings()) {
            this.getChildren().add(new Label(building.getClass().getSimpleName()));
        }
    }
}
