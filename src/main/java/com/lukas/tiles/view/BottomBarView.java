package com.lukas.tiles.view;

import com.lukas.tiles.viewModel.BottomBarViewModel;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BottomBarView extends HBox {

    private final BottomBarViewModel bottomBarViewModel;

    public BottomBarView() {
        bottomBarViewModel = new BottomBarViewModel();
        Label label = new Label("Bottom Bar");
        this.getChildren().add(label);
    }
}
