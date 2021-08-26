package com.lukas.tiles.model.building;

import com.lukas.tiles.model.Money;
import com.lukas.tiles.model.TileType;

import java.util.Set;


public enum BuildingEnum {
    FarmEnum(Farm::new, "Farm", Farm.price(), Farm.buildTime(), (standingOn, neighbours) -> standingOn == TileType.Grass),
    PortEnum(Port::new, "Port", Port.price(), Port.buildTime(), (standingOn, neighbours) -> standingOn == TileType.Coastal && neighbours.contains(TileType.Water));

    private final BuildingFactory factory;
    private final String name;
    private final Money price;
    private final int buildTime;
    private final BuildPredicate predicate;

    BuildingEnum(BuildingFactory factory, String name, Money price, int buildTime, BuildPredicate predicate) {
        this.factory = factory;
        this.name = name;
        this.price = price;
        this.buildTime = buildTime;
        this.predicate = predicate;
    }

    public Building instantiate() {
        return factory.create();
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

    public boolean canBeBuild(TileType standingOn, Set<TileType> neighbours) {
        return predicate.test(standingOn, neighbours);
    }
}
