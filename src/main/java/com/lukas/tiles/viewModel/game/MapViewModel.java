package com.lukas.tiles.viewModel.game;

import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.WorldMap;
import com.lukas.tiles.view.BasicObserver;
import com.lukas.tiles.view.game.TileSelectedHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * The ViewModel for the map view
 * Responsible for controlling all button presses, the layout of the tiles and other displayed information
 */
public class MapViewModel implements BasicObserver {
    private final WorldMap map;
    private final List<BasicObserver> mapViewModelObservers;

    private TileSelectedHandler selectedHandler;

    private double zoom = 1;
    private final static double MAX_ZOOM = 2;
    private final static double MIN_ZOOM = 0.5;
    private final static double SENSITIVITY = 240; //Higher is slower

    private final Tile[][] tiles;
    private final Hexagon[] hexagons;

    private double lastX;
    private double lastY;

    private double xOffset = 0;
    private double yOffset = 0;

    private int forceCalculate = -1;

    /**
     * Creates a new MapViewModel based on a given map
     *
     * @param map that the MapViewModel should represent
     */
    public MapViewModel(WorldMap map) {
        this.map = map;
        map.subscribe(this);


        mapViewModelObservers = new ArrayList<>();
        hexagons = new Hexagon[map.getSize()];

        tiles = map.getTiles();

        update();
    }

    /**
     * @return a one dimensional array of all hexagons
     */
    public Hexagon[] getHexagons() {
        return hexagons;
    }

    /**
     * @return a two-dimensional array of all tiles
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * @return the number of tiles on the y-axis
     */
    public int getWidth() {
        return map.getWidth();
    }

    /**
     * @return the number of tiles on the x-axis
     */
    public int getHeight() {
        return map.getHeight();
    }

    /**
     * Subscribe to get notified about changes
     *
     * @param observer the object that wants to receive updates from the MapViewModel
     */
    public void subscribe(BasicObserver observer) {
        mapViewModelObservers.add(observer);
    }

    /**
     * Handles all scroll events and recalculates the hexagons accordingly
     *
     * @param scrollEvent the scroll event that occurred
     * @param width       the width of the current view where it was called from
     * @param height      the height of the view where it was called from
     */
    public void handleScroll(ScrollEvent scrollEvent, double width, double height) {

        //Find the current hovered hexagon
        int hoveredHexagonIndex = -1;
        for (int i = 0; i < hexagons.length; i++) {
            if (hexagons[i].isInside(scrollEvent.getSceneY(), scrollEvent.getSceneX())) {
                hoveredHexagonIndex = i;
                break;
            }
        }
        //Ignore the scroll if we are outside the map
        if (hoveredHexagonIndex == -1) {
            return;
        }

        zoom += scrollEvent.getDeltaY() / SENSITIVITY;
        zoom = Math.max(MIN_ZOOM, Math.min(MAX_ZOOM, zoom));

        double oldX = hexagons[hoveredHexagonIndex].getX()[0];
        double oldY = hexagons[hoveredHexagonIndex].getY()[1];
        forceCalculate = hoveredHexagonIndex;
        recalculate();

        double newX = hexagons[hoveredHexagonIndex].getX()[0];
        double newY = hexagons[hoveredHexagonIndex].getY()[1];

        move((oldX - newX), (oldY - newY), width, height);
        forceCalculate = -1;

        recalculate();
        updateMapView();
    }

    /**
     * Handles all mouse drag events. Used for moving the map
     *
     * @param mouseEvent that has occurred
     * @param width      the width of the current view where it was called from
     * @param height     the height of the view where it was called from
     */
    public void handleDragged(MouseEvent mouseEvent, double width, double height) {
        move(mouseEvent.getSceneY() - lastX, mouseEvent.getSceneX() - lastY, width, height);

        lastY = mouseEvent.getSceneX();
        lastX = mouseEvent.getSceneY();

        recalculate();
        updateMapView();
    }

    private void move(double x, double y, double width, double height) {
        double horizontalFactor = Math.sqrt(3) * zoom * Hexagon.getRelativeScale();
        double verticalFactor = 1.5 * zoom * Hexagon.getRelativeScale();
        yOffset = Math.max(-1 * (map.getWidth() + 1) * horizontalFactor + width, Math.min(0.5 * horizontalFactor, yOffset + y));
        xOffset = Math.max(-1 * (map.getHeight() + 1) * verticalFactor + height, Math.min(0.5 * verticalFactor, xOffset + x));
    }

    /**
     * Handles all mouse presses
     *
     * @param mouseEvent that has occurred
     */
    public void mouseDown(MouseEvent mouseEvent) {
        lastY = mouseEvent.getSceneX();
        lastX = mouseEvent.getSceneY();
    }

    /**
     * Handles all mouse releases. This might indicate the end of a drag or a press on a tile
     *
     * @param mouseEvent that has occurred
     */
    public void mouseUp(MouseEvent mouseEvent) {
        if (lastY != mouseEvent.getSceneX() || lastX != mouseEvent.getSceneY()) {
            return; //Was a drag
        }

        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            for (int i = 0; i < hexagons.length; i++) {
                Tile current = tiles[i / map.getWidth()][i % map.getWidth()];
                if (hexagons[i].isInside(mouseEvent.getSceneY(), mouseEvent.getSceneX())) {
                    current.setSelected(true);
                    selectedHandler.handle(current);
                } else {
                    current.setSelected(false);
                }
            }
        } else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
            selectedHandler.handle(null);
            for (int i = 0; i < hexagons.length; i++) {
                tiles[i / map.getWidth()][i % map.getWidth()].setSelected(false);
            }
        }

        //Recalculation not necessary, only one tile might have changed color
        updateMapView();
    }

    private void updateMapView() {
        for (BasicObserver mapViewModelObserver : mapViewModelObservers) {
            mapViewModelObserver.update();
        }
    }

    /**
     * Recalculates the hexagons and notifies subscribers about a change
     */
    @Override
    public void update() {
        recalculate();
        updateMapView();
    }

    private void recalculate() {
        boolean force;
        for (int i = 0; i < hexagons.length; i++) {
            force = i == forceCalculate;
            hexagons[i] = new Hexagon(i / getWidth(), i % getWidth(), zoom, xOffset, yOffset, force);
        }
    }

    /**
     * @param handler performed action that should happen when a tile gets selected
     */
    public void setOnSelect(TileSelectedHandler handler) {
        this.selectedHandler = handler;
    }
}
