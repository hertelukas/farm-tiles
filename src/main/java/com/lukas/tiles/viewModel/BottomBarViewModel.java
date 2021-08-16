package com.lukas.tiles.viewModel;

import com.lukas.tiles.model.Player;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BottomBarViewModel implements PlayerObserver {
    private final StringProperty moneyProperty;

    //TODO this needs to subscribe to the player
    public BottomBarViewModel() {
        moneyProperty = new SimpleStringProperty("$0.00");
    }

    public String getMoneyProperty() {
        return moneyProperty.get();
    }

    public StringProperty moneyPropertyProperty() {
        return moneyProperty;
    }

    @Override
    public void update(Player player) {
        moneyProperty.set(player.getMoney().getAmountAsString());
    }
}
