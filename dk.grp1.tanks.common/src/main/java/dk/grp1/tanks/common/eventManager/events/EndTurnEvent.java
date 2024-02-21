package dk.grp1.tanks.common.eventManager.events;

import dk.grp1.tanks.common.data.Entity;

/**
 * EndTurnEvents are thrown to show that a turn is done, and should be passed on to the next.
 */
public class EndTurnEvent extends Event {

    public EndTurnEvent(Entity source) {
        super(source);
    }

}
