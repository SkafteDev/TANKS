package dk.grp1.tanks.common.eventManager;

import dk.grp1.tanks.common.eventManager.events.Event;

public interface IEventCallback {
    /**
     * Processes the given event.
     * @param event
     */
    void processEvent(Event event);
}
