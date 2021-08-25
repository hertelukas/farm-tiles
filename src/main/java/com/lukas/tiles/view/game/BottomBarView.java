package com.lukas.tiles.view.game;

import com.lukas.tiles.model.Farmer;
import com.lukas.tiles.model.Scheduler;
import com.lukas.tiles.view.Style;
import com.lukas.tiles.viewModel.game.BottomBarViewModel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BottomBarView extends HBox {

    private final BottomBarViewModel bottomBarViewModel;

    public BottomBarView(Farmer player, Scheduler scheduler) {
        bottomBarViewModel = new BottomBarViewModel(player, scheduler);
        setupBindings();
    }

    private void setupBindings() {
        super.setAlignment(Pos.CENTER);
        super.setSpacing(Style.getHSpacing());


        Label cashLabel = new Label();
        cashLabel.textProperty().bind(bottomBarViewModel.moneyPropertyProperty());

        Label timeLabel = new Label();
        timeLabel.textProperty().bind(bottomBarViewModel.scheduleProperty().asString());

        this.getChildren().addAll(cashLabel, timeLabel);
    }
}
