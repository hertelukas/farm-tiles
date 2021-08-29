package com.lukas.tiles.model.building;

import com.lukas.tiles.model.Farmer;
import com.lukas.tiles.model.Money;
import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.TileType;

import java.util.Set;

/**
 * A list of all buildings and there requirements
 */
public enum BuildingEnum {
    /**
     * Entry representing a farm
     */
    FarmEnum(Farm::new, "Farm", Farm.price(), Farm.buildTime(), Farm.getMaintenance(), (standingOn, neighbours, farmer) -> standingOn == TileType.Grass && farmer.isHasHeadquarter()),
    /**
     * Entry representing a port
     */
    PortEnum(Port::new, "Port", Port.price(), Port.buildTime(), Port.getMaintenance(), (standingOn, neighbours, farmer) -> standingOn == TileType.Coastal && neighbours.contains(TileType.Water) && farmer.isHasHeadquarter()),
    /**
     * Entry representing a HQ
     */
    HeadquarterEnum(Headquarter::new, "Headquarter", Headquarter.price(), Headquarter.buildTime(), Headquarter.getMaintenance(), (standingOn, neighbours, farmer) -> standingOn == TileType.Grass && !farmer.isHasHeadquarter());

    private final BuildingFactory factory;
    private final String name;
    private final Money price;
    private final Money maintenance;
    private final int buildTime;
    private final BuildPredicate predicate;

    /**
     * @param factory     that instantiates the building
     * @param name        of the building
     * @param price       of the building
     * @param buildTime   of the building in seconds
     * @param maintenance basic maintenance cost that gets subtracted every game-update
     * @param predicate   whether the building can be placed on a given tile by a given farmer
     */
    BuildingEnum(BuildingFactory factory, String name, Money price, int buildTime, Money maintenance, BuildPredicate predicate) {
        this.factory = factory;
        this.name = name;
        this.price = price;
        this.maintenance = maintenance;
        this.buildTime = buildTime;
        this.predicate = predicate;
    }

    /**
     * @param tile where the building is supposed to stand on
     * @return an instance of the building
     */
    public Building instantiate(Tile tile) {
        return factory.create(tile);
    }

    /**
     * @return the name of the building
     */
    public String getName() {
        return name;
    }

    /**
     * @return the price of the building
     */
    public Money getPrice() {
        return price;
    }

    /**
     * @return the time for the building to get build in seconds
     */
    public int getBuildTime() {
        return buildTime;
    }

    /**
     * @return the basic maintenance cost for a given building
     */
    public Money getMaintenance() {
        return maintenance;
    }

    /**
     * @param standingOn the tileType where the building should be placed on
     * @param neighbours the neighbouring tiles
     * @param farmer     the farmer who tries to build a given building
     * @return whether a building can be build there
     */
    public boolean canBeBuild(TileType standingOn, Set<TileType> neighbours, Farmer farmer) {
        return predicate.test(standingOn, neighbours, farmer);
    }
}
