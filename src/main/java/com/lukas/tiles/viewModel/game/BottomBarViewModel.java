package com.lukas.tiles.viewModel.game;

import com.lukas.tiles.model.Farmer;
import com.lukas.tiles.model.Scheduler;
import javafx.beans.property.*;

/**
 * The ViewModel for the Bottom Bar
 * Responsible to control all items
 */
public class BottomBarViewModel implements FarmerObserver {
    private final StringProperty moneyProperty;
    private final LongProperty scheduleProperty;

    /**
     * @param player    the farmer information that should be displayed
     * @param scheduler the game scheduler to update the time information
     */
    public BottomBarViewModel(Farmer player, Scheduler scheduler) {
        moneyProperty = new SimpleStringProperty("$0.00");

        scheduleProperty = new SimpleLongProperty(0);
        scheduleProperty.bind(scheduler.counterProperty());
        player.subscribe(this);
    }

    /**
     * @return the current money owned by the observed farmer
     */
    public StringProperty moneyPropertyProperty() {
        return moneyProperty;
    }

    /**
     * @return the schedule property that displays the current time
     */
    public LongProperty scheduleProperty() {
        return scheduleProperty;
    }

    /**
     * This method gets called when the subscribed farmer has any changes
     * Push notification variant of the observer pattern
     *
     * @param player information about the subscribed farmer
     */
    @Override
    public void update(Farmer player) {
        moneyProperty.set(player.getMoney().toString());
    }
}
