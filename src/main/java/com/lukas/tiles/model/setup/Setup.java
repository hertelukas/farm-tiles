package com.lukas.tiles.model.setup;

/**
 * Represents the game settings defined by a user
 */
public class Setup {
    private int farmers = 5;
    private FarmerColor farmerColor = FarmerColor.getDefault();
    private Difficulty difficulty;
    private MapSize mapSize = MapSize.getDefault();
    private MapType mapType = MapType.getDefault();
    private long seed;
    private boolean seedIsSet = false;

    private String name;

    public int getFarmers() {
        return farmers;
    }

    public void setFarmers(int farmers) {
        if (farmers < 1) {
            throw new IllegalArgumentException("#Farmers has to be positive");
        }
        this.farmers = farmers;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public MapSize getMapSize() {
        return mapSize;
    }

    public void setMapSize(MapSize mapSize) {
        this.mapSize = mapSize;
    }

    public MapType getMapType() {
        return mapType;
    }

    public void setMapType(MapType mapType) {
        this.mapType = mapType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param seed Sets a seed for the map generation
     */
    public void setSeed(long seed) {
        this.seedIsSet = true;
        this.seed = seed;
    }

    /**
     * @return The current seed. Check if a custom seed is set with {@link #seedIsSet}
     */
    public long getSeed() {
        return seed;
    }

    /**
     * @return Whether a custom seed is set.
     */
    public boolean isSeedIsSet() {
        return seedIsSet;
    }

    /**
     * @param farmerColor the color the user should have
     */
    public void setColor(FarmerColor farmerColor) {
        this.farmerColor = farmerColor;
    }

    /**
     * @return the color a user chose
     */
    public FarmerColor getColor() {
        return farmerColor;
    }

    @Override
    public String toString() {
        return "Setup{" +
                "farmers=" + farmers +
                ", farmerColor=" + farmerColor +
                ", difficulty=" + difficulty +
                ", mapSize=" + mapSize +
                ", mapType=" + mapType +
                ", name='" + name + '\'' +
                '}';
    }
}
