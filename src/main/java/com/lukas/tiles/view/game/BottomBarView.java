package com.lukas.tiles.view.game;

import com.lukas.tiles.model.Farmer;
import com.lukas.tiles.model.Scheduler;
import com.lukas.tiles.view.Style;
import com.lukas.tiles.viewModel.game.BottomBarViewModel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Visual representation of the bottom bar
 */
public class BottomBarView extends HBox {

    private final BottomBarViewModel bottomBarViewModel;

    /**
     * @param player    the current player
     * @param scheduler the game scheduler to display the current time
     */
    public BottomBarView(Farmer player, Scheduler scheduler) {
        bottomBarViewModel = new BottomBarViewModel(player, scheduler);
        this.getStylesheets().add(Style.getMainStyle());
        setupBindings();
    }

    private void setupBindings() {
        super.setAlignment(Pos.CENTER);
        super.setSpacing(Style.getHSpacing());
        super.setPadding(Style.getSmallPadding());


        Label cashLabel = new Label();
        cashLabel.textProperty().bind(bottomBarViewModel.moneyPropertyProperty());
        cashLabel.getStyleClass().add("h3");

        Label timeLabel = new Label();
        timeLabel.textProperty().bind(bottomBarViewModel.scheduleProperty().asString());

        this.getChildren().addAll(cashLabel, timeLabel);
    }
}
