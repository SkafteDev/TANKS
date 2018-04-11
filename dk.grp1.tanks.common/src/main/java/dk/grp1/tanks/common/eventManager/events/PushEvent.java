package dk.grp1.tanks.common.eventManager.events;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.utils.Vector2D;

public class PushEvent extends Event {

    private Vector2D pointOfExplosion;
    private float force;
    private float pushRadius;
    public PushEvent(Entity source, Vector2D pointOfExplosion, float force, float pushRadius) {
        super(source);
        this.pointOfExplosion = pointOfExplosion;
        this.force = force;
        this.pushRadius = pushRadius;
    }

    public float getForce() {
        return force;
    }

    public Vector2D getPointOfExplosion() {
        return pointOfExplosion;
    }

    public float getPushRadius() {
        return pushRadius;
    }
}
