package dk.grp1.tanks.common.eventManager.events;

import dk.grp1.tanks.common.data.Entity;

/**
 * A GameMapChanged Event marks that the game map has changed in shape.
 */
public class GameMapChangedEvent extends Event {
    public GameMapChangedEvent(Entity source) {
        super(source);
    }
}
