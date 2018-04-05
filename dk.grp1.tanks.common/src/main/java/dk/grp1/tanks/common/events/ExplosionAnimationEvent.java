package dk.grp1.tanks.common.events;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.parts.ExplosionTexturePart;
import dk.grp1.tanks.common.utils.Vector2D;

public class ExplosionAnimationEvent extends Event {

    private Vector2D pointOfExplosion;
    private ExplosionTexturePart explosionTexturePart;
    private float explosionRadius;


    public ExplosionAnimationEvent(Entity source, Vector2D pointOfExplosion, ExplosionTexturePart explosionTexturePart, float explosionRadius) {
        super(source);
        this.explosionRadius = explosionRadius;
        this.pointOfExplosion = pointOfExplosion;
        this.explosionTexturePart = explosionTexturePart;
    }

    public float getExplosionRadius() {
        return explosionRadius;
    }

    public Vector2D getPointOfExplosion() {
        return pointOfExplosion;
    }

    public ExplosionTexturePart getExplosionTexturePart() {
        return explosionTexturePart;
    }
}
