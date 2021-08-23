package com.lukas.tiles.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public abstract class ScheduledObject implements Serializable {
    @Serial
    private static final long serialVersionUID = 7393432237356459445L;
    private final int buildTime;
    private int timeBuild;
    private transient DoubleProperty progress = new SimpleDoubleProperty(0);
    private boolean finished;

    protected ScheduledObject(int buildTime) {
        this.buildTime = buildTime;
        finished = false;
    }

    public int getBuildTime() {
        return buildTime;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void buildStep() {
        timeBuild++;
        progress.set((double) timeBuild / buildTime);
    }

    public DoubleProperty progressProperty() {
        return progress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduledObject that = (ScheduledObject) o;
        return buildTime == that.buildTime && finished == that.finished;
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildTime, finished);
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        if (!finished) {
            progress = new SimpleDoubleProperty((double) timeBuild / buildTime);
        }
    }
}
