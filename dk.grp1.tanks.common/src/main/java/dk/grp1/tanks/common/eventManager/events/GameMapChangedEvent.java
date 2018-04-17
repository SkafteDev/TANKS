package dk.grp1.tanks.common.eventManager.events;

import dk.grp1.tanks.common.data.Entity;

public class GameMapChangedEvent extends Event {
    public GameMapChangedEvent(Entity source) {
        super(source);
    }
}
