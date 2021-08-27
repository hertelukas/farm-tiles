package com.lukas.tiles.view.game.tab;

import com.lukas.tiles.FarmTilesApplication;
import com.lukas.tiles.view.SideMenu;
import com.lukas.tiles.view.Style;
import com.lukas.tiles.view.game.GameView;
import com.lukas.tiles.view.menu.MenuEntry;
import com.lukas.tiles.viewModel.game.GameViewModel;
import com.lukas.tiles.viewModel.game.TabViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.List;


public class TabView extends HBox {

    private final TabViewModel tabViewModel;
    private final List<AbstractTabView> abstractTabViews;
    private final GameViewModel gameViewModel;
    private final GameView gameView;

    public TabView(GameView gameView, GameViewModel gameViewModel) {
        abstractTabViews = new ArrayList<>();
        tabViewModel = new TabViewModel();
        this.gameViewModel = gameViewModel;
        this.gameView = gameView;

        this.getStylesheets().add(FarmTilesApplication.getMainStyle());

        this.setSpacing(Style.getVSpacing());
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(Style.getPADDING());
        HBox.setHgrow(this, Priority.ALWAYS);

        initialize();

    }

    private void initialize() {
        //Load the different tabViews
        abstractTabViews.add(new OverviewView(gameView, gameViewModel.getGame().getFarmers().get(0)));
        abstractTabViews.add(new ShopView());
        abstractTabViews.add(new FinanceView(gameViewModel.getGame().getFarmers().get(0), gameViewModel.getGame().getScheduler().counterProperty()));

        //Side menu
        SideMenu sideMenu = new SideMenu(new SimpleStringProperty("Menu"));
        getChildren().add(0, sideMenu);

        sideMenu.addMenuEntry(new MenuEntry(this::loadView, new SimpleStringProperty("Overview")));
        sideMenu.addMenuEntry(new MenuEntry(this::loadView, new SimpleStringProperty("Shop")));
        sideMenu.addMenuEntry(new MenuEntry(this::loadView, new SimpleStringProperty("Finance")));

        sideMenu.addBottomEntry(new MenuEntry(index -> close(), new SimpleStringProperty("Close")));

        loadView(0);

    }

    private void loadView(int index) {
        if (this.getChildren().size() > 1) {
            this.getChildren().remove(1);
        }
        this.getChildren().add(abstractTabViews.get(index));
    }

    private void close() {
        gameViewModel.toggleTab();
    }

}
