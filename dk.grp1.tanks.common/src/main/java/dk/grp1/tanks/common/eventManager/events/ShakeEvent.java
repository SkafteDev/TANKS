package dk.grp1.tanks.common.eventManager.events;

import dk.grp1.tanks.common.data.Entity;

/**
 * A shake event marks that the screen should shake.
 */
public class ShakeEvent extends Event {
    private float intensity;
    public ShakeEvent(Entity source, float intensity) {
        super(source);
        this.intensity = intensity;
    }

    public float getIntensity() {
        return intensity;
    }
}
