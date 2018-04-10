package dk.grp1.tanks.common.events;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.eventManager.events.Event;

public class SoundEvent extends Event {

    private String path;
    public SoundEvent(Entity source, String path) {
        super(source);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
