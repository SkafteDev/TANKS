package dk.grp1.tanks.common.events;

import dk.grp1.tanks.common.data.Entity;

public class ShootingEvent extends Event {
    public ShootingEvent(Entity source) {
        super(source);
    }
}
