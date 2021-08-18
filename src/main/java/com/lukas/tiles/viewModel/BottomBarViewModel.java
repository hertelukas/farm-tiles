package com.lukas.tiles.viewModel;

import com.lukas.tiles.model.Player;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BottomBarViewModel implements PlayerObserver {
    private final StringProperty moneyProperty;

    public BottomBarViewModel(Player player) {
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
    public void update(Player player) {
        moneyProperty.set(player.getMoney().toString());
    }
}
