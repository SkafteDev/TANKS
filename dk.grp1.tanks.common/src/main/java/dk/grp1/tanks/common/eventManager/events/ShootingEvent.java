package dk.grp1.tanks.common.eventManager.events;

import dk.grp1.tanks.common.data.Entity;

public class ShootingEvent extends Event {
    private float firepower;
    public ShootingEvent(Entity source, float firepower) {
        super(source);
        this.firepower = firepower;
    }

    public float getFirepower(){
        return this.firepower;
    }
}
