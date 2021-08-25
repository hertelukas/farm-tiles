package com.lukas.tiles.model.building;

import com.lukas.tiles.model.Money;
import com.lukas.tiles.model.TileType;

import java.util.ArrayList;
import java.util.List;

public enum BuildingEnum {
    FarmEnum("Farm", Farm.price(), Farm.buildTime(), TileType.Grass),
    PortEnum("Port", Port.price(), Port.buildTime(), TileType.Water);

    private final String name;
    private final Money price;
    private final int buildTime;
    private final List<TileType> buildableOn;

    BuildingEnum(String name, Money price, int buildTime, TileType... buildableOn) {
        this.name = name;
        this.price = price;
        this.buildTime = buildTime;
        this.buildableOn = new ArrayList<>();
        this.buildableOn.addAll(List.of(buildableOn));
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

    public boolean canBeBuildOn(TileType tileType) {
        return buildableOn.contains(tileType);
    }
}
