package com.lukas.tiles.view;

import com.lukas.tiles.model.Player;
import com.lukas.tiles.viewModel.BottomBarViewModel;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BottomBarView extends HBox {

    private final BottomBarViewModel bottomBarViewModel;

    public BottomBarView(Player player) {
        bottomBarViewModel = new BottomBarViewModel(player);
        setupBindings();
    }

    private void setupBindings() {
        Label cashLabel = new Label();
        cashLabel.textProperty().bind(bottomBarViewModel.moneyPropertyProperty());
        this.getChildren().add(cashLabel);
    }
}
