package com.lukas.tiles.model;

import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Is responsible for scheduling a single game
 * <p>
 * Has an internal counter and keeps track of ScheduledObjects.
 *
 * @see com.lukas.tiles.model.ScheduledObject
 */
public class Scheduler implements Serializable {
    @Serial
    private static final long serialVersionUID = -7623687222760332639L;
    private long counter = 0;
    private long latestUpdate = 0;
    private transient LongProperty counterProperty;

    private final Map<Long, List<ScheduledObject>> finishObserver;

    /**
     * Instantiates a new Scheduler.
     */
    public Scheduler() {
        finishObserver = new HashMap<>();
        counterProperty = new SimpleLongProperty(counter);
        start();
    }

    /**
     * Creates a new TimerTask that gets updated every second.
     * <p>
     * Every ScheduledObject gets notified that a new build step occurred.
     * Furthermore, the counter gets increased by one.
     */
    private void start() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                counter++;
                Platform.runLater(() -> counterProperty.set(counter));

                //Make one build for future updates
                for (long i = counter; i <= latestUpdate; i++) {
                    if (finishObserver.containsKey(i) && finishObserver.get(i) != null) {
                        for (ScheduledObject scheduledObject : finishObserver.get(i)) {
                            scheduledObject.buildStep();
                        }
                    }
                }

                if (finishObserver.containsKey(counter) && finishObserver.get(counter) != null) {
                    for (ScheduledObject scheduledObject : finishObserver.get(counter)) {
                        scheduledObject.setFinished();
                    }
                }

                //Remove the list that was now finished
                finishObserver.remove(counter);
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    /**
     * @param object Add an object which has to keep track of a build progress.
     */
    public void addObject(ScheduledObject object) {
        //Update the new latest object
        if (counter + object.getBuildTime() > latestUpdate) {
            latestUpdate = counter + object.getBuildTime();
            System.out.println("Latest update is now: " + latestUpdate);
        }
        //If there is no list with this time, make new list
        if (finishObserver.get(counter + object.getBuildTime()) == null) {
            List<ScheduledObject> objects = new ArrayList<>();
            objects.add(object);
            finishObserver.put(counter + object.getBuildTime(), objects);
        } else { //Else we can add it directly to this list
            finishObserver.get(counter + object.getBuildTime()).add(object);
        }
    }

    /**
     * @return get the current counter
     */
    public LongProperty counterProperty() {
        return counterProperty;
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        counterProperty = new SimpleLongProperty(counter);
        start();
    }
}
