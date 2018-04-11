package dk.grp1.tanks.common.eventManager.events;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.utils.Vector2D;

public class ExplosionEvent extends Event {
    private Vector2D pointOfCollision;
    private float explosionRadius;


    public ExplosionEvent(Entity source, Vector2D pointOfCollision, float explosionRadius) {
        super(source);
        this.pointOfCollision = pointOfCollision;
        this.explosionRadius = explosionRadius;
    }

    public Vector2D getPointOfCollision() {
        return pointOfCollision;
    }

    public float getExplosionRadius() {
        return explosionRadius;
    }
}


