package dk.grp1.tanks.common.eventManager.events;


import dk.grp1.tanks.common.data.Entity;

import java.io.Serializable;

/**
 * Event is the abstract class that all other events inherit from.
 */
public abstract class Event implements Serializable{
    private final Entity source;

    public Event(Entity source) {
        this.source = source;
    }

    public Entity getSource() {
        return source;
    }
}
