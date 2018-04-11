package dk.grp1.tanks.common.eventManager;

import dk.grp1.tanks.common.eventManager.events.Event;

public interface IEventCallback {

    void processEvent(Event event);
}
