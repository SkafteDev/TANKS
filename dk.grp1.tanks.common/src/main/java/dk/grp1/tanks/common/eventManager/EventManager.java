package dk.grp1.tanks.common.eventManager;

import dk.grp1.tanks.common.eventManager.events.Event;

import java.util.HashMap;
import java.util.HashSet;

public class EventManager {


    private HashMap<Class<? extends Event>,HashSet<IEventCallback>> handlers;


    public EventManager() {
        this.handlers = new HashMap<>();

    }

    // Might contain a failure
    public void register(Class<? extends Event> eventType, IEventCallback callback){
        HashSet<IEventCallback> callbackSet = handlers.get(eventType);
        if (callbackSet == null){
            callbackSet = new HashSet<>();
            handlers.put(eventType,callbackSet);
        }

        callbackSet.add(callback);

    }

    public void unRegister(Class<? extends Event> eventType, IEventCallback callback){
        if (handlers.containsKey(eventType)){
            HashSet<IEventCallback> callbackSet = handlers.get(eventType);
            callbackSet.remove(callback);
        }
    }

    public <T extends Event> void addEvent(T event){
        for (IEventCallback callback : this.handlers.get(event.getClass())) {
            callback.processEvent(event);
        }
    }



}
