package com.lukas.tiles.model.building;

import com.lukas.tiles.model.Farmer;
import com.lukas.tiles.model.Money;
import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.TileType;

import java.util.Set;


public enum BuildingEnum {
    FarmEnum(Farm::new, "Farm", Farm.price(), Farm.buildTime(), Farm.getMaintenance(), (standingOn, neighbours, farmer) -> standingOn == TileType.Grass && farmer.isHasHeadquarter()),
    PortEnum(Port::new, "Port", Port.price(), Port.buildTime(), Port.getMaintenance(), (standingOn, neighbours, farmer) -> standingOn == TileType.Coastal && neighbours.contains(TileType.Water) && farmer.isHasHeadquarter()),
    HeadquarterEnum(Headquarter::new, "Headquarter", Headquarter.price(), Headquarter.buildTime(), Headquarter.getMaintenance(), (standingOn, neighbours, farmer) -> standingOn == TileType.Grass && !farmer.isHasHeadquarter());

    private final BuildingFactory factory;
    private final String name;
    private final Money price;
    private final Money maintenance;
    private final int buildTime;
    private final BuildPredicate predicate;

    BuildingEnum(BuildingFactory factory, String name, Money price, int buildTime, Money maintenance, BuildPredicate predicate) {
        this.factory = factory;
        this.name = name;
        this.price = price;
        this.maintenance = maintenance;
        this.buildTime = buildTime;
        this.predicate = predicate;
    }

    public Building instantiate(Tile tile) {
        return factory.create(tile);
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public int getBuildTime() {
        return buildTime;
    }

    public Money getMaintenance() {
        return maintenance;
    }

    public boolean canBeBuild(TileType standingOn, Set<TileType> neighbours, Farmer farmer) {
        return predicate.test(standingOn, neighbours, farmer);
    }
}
