package com.lukas.tiles.model.building;

import com.lukas.tiles.model.Money;

import java.io.Serial;

public class Farm extends Building {
    @Serial
    private static final long serialVersionUID = 6538625479050341086L;
    private final static long PRICE = 40000; //$400.00
    private final static int BUILD_TIME = 20;


    public Farm() {
        super(new Money(PRICE), BUILD_TIME);
    }
}
