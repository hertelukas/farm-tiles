package com.lukas.tiles.viewModel;

import com.lukas.tiles.Config;
import com.lukas.tiles.FarmTilesApplication;
import com.lukas.tiles.Language;
import com.lukas.tiles.SceneLoader;
import com.lukas.tiles.io.GameHandler;
import com.lukas.tiles.model.Game;
import com.lukas.tiles.text.LanguageObserver;
import com.lukas.tiles.view.game.GameView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class LoadViewModel implements LanguageObserver {

    public LoadViewModel(Config config) {
        config.subscribe(this);
    }

    private final StringProperty titleProperty = new SimpleStringProperty("Load Game");
    private final StringProperty back = new SimpleStringProperty("Back");

    @Override
    public void update(Language language) {
        switch (language) {
            case Deutsch -> {
                titleProperty.set("Spiel laden");
                back.set("Zurück");
            }
            case English -> {
                titleProperty.set("Load Game");
                back.set("Back");
            }
        }
    }

    public StringProperty titlePropertyProperty() {
        return titleProperty;
    }

    public StringProperty backProperty() {
        return back;
    }

    public void goBack(Event event) {
        try {
            SceneLoader.getInstance().loadScene(FarmTilesApplication.getStartPage());
        } catch (IOException e) {
            // TODO: 8/19/21 handle exception
            e.printStackTrace();
        }
    }

    // TODO: 8/20/21 exception handling
    public Set<HBox> generateLoadMenu() {
        Set<HBox> rows = new HashSet<>();
        Set<String> games = GameHandler.getGames();
        for (String game : games) {
            HBox row = new HBox();
            row.setAlignment(Pos.CENTER);
            Button button = new Button(game);
            button.setOnAction(event -> {
                try {
                    Game loadedGame = GameHandler.load(game);
                    SceneLoader.getInstance().loadScene(new GameView(loadedGame));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

            Button delete = new Button("❌");
            delete.setOnAction(event -> {
                try {
                    GameHandler.delete(game);
                    row.setVisible(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            row.getChildren().addAll(button, delete);
            rows.add(row);
        }
        return rows;
    }
}
