package com.lukas.tiles.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.jetbrains.annotations.Range;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Superclass of all objects that take time to get build
 */
public abstract class ScheduledObject implements Serializable {
    @Serial
    private static final long serialVersionUID = 7393432237356459445L;
    private final int buildTime;
    private int timeBuild;
    private transient DoubleProperty progress = new SimpleDoubleProperty(0);
    private boolean finished;

    /**
     * @param buildTime of the object in seconds
     */
    protected ScheduledObject(int buildTime) {
        this.buildTime = buildTime;
        finished = false;
    }

    /**
     * @return get the time needed to build this object
     */
    public int getBuildTime() {
        return buildTime;
    }

    /**
     * @return whether the object is fully generated in the game
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * set this object to be fully generated in the game
     */
    public void setFinished() {
        this.finished = true;
    }

    /**
     * progress one step in the generation of the object
     */
    public void buildStep() {
        timeBuild++;
        progress.set((double) timeBuild / buildTime);
    }

    /**
     * @return the current progress between 0 and 1
     */
    public @Range(from = 0, to = 1) DoubleProperty progressProperty() {
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
