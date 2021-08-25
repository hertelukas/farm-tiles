package com.lukas.tiles.viewModel.game;

import com.lukas.tiles.model.Farmer;
import com.lukas.tiles.model.Scheduler;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BottomBarViewModel implements FarmerObserver {
    private final StringProperty moneyProperty;
    private final DoubleProperty scheduleProperty;

    public BottomBarViewModel(Farmer player, Scheduler scheduler) {
        moneyProperty = new SimpleStringProperty("$0.00");

        scheduleProperty = new SimpleDoubleProperty(0);
        scheduleProperty.bind(scheduler.counterProperty());
        player.subscribe(this);
    }

    public String getMoneyProperty() {
        return moneyProperty.get();
    }

    public StringProperty moneyPropertyProperty() {
        return moneyProperty;
    }

    public DoubleProperty scheduleProperty() {
        return scheduleProperty;
    }

    @Override
    public void update(Farmer player) {
        moneyProperty.set(player.getMoney().toString());
    }
}
