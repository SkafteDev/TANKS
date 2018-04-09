package dk.grp1.tanks.common.events;

import dk.grp1.tanks.common.data.Entity;

public class EndTurnEvent extends Event {

    public EndTurnEvent(Entity source) {
        super(source);
    }

}
