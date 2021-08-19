package com.lukas.tiles.viewModel;

import com.lukas.tiles.model.Farmer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BottomBarViewModel implements FarmerObserver {
    private final StringProperty moneyProperty;

    public BottomBarViewModel(Farmer player) {
        moneyProperty = new SimpleStringProperty("$0.00");

        player.subscribe(this);
    }

    public String getMoneyProperty() {
        return moneyProperty.get();
    }

    public StringProperty moneyPropertyProperty() {
        return moneyProperty;
    }

    @Override
    public void update(Farmer player) {
        moneyProperty.set(player.getMoney().toString());
    }
}
