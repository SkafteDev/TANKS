package dk.grp1.tanks.common.eventManager;

import dk.grp1.tanks.common.eventManager.events.Event;

import java.util.HashMap;
import java.util.HashSet;

/**
 * The EventManager uses the observer pattern to distribute events that has happened in the game.
 */
public class EventManager {


    private HashMap<Class<? extends Event>, HashSet<IEventCallback>> handlers;

    public EventManager() {
        this.handlers = new HashMap<>();

    }

    /**
     * Register a callback implementation to a given Class of event. Whenever such event is added the processEvent in
     *  the IEventCallback implementation will be called.
     * @param eventType
     * @param callback
     */
    public void register(Class<? extends Event> eventType, IEventCallback callback) {

        if (eventType == null || callback == null){
            throw new Error("CallBack or EventType is null");
        }

        HashSet<IEventCallback> callbackSet = handlers.get(eventType);
        if (callbackSet == null) {
            callbackSet = new HashSet<>();
            handlers.put(eventType, callbackSet);
        }

        callbackSet.add(callback);

    }

    /**
     * Unregister a callback from the EventManager. That means events of the given type will no longer call the
     *  processEvent method.
     * @param eventType
     * @param callback
     */
    public void unRegister(Class<? extends Event> eventType, IEventCallback callback) {

        if (eventType == null || callback == null){
            throw new Error("CallBack or EventType is null");
        }

        if (handlers.containsKey(eventType)) {
            HashSet<IEventCallback> callbackSet = handlers.get(eventType);
            callbackSet.remove(callback);
        }
    }

    /**
     * Adds an event to the EventManager. If any handlers of the event have been registered they will be immediately processed.
     * If not the event will never be processed or saved.
     * @param event
     * @param <T>
     */
    public <T extends Event> void addEvent(T event) {

        if (event == null){
            return;
        }

        if (this.handlers.containsKey(event.getClass())) {

            for (IEventCallback callback : this.handlers.get(event.getClass())) {
                callback.processEvent(event);
            }
        }
    }


}
