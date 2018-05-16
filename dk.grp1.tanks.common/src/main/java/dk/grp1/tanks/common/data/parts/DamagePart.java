package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;

public class DamagePart implements IEntityPart {
    private float explosionRadius;
    private float damage;

    public DamagePart(){

    }
    /**
     * Constructs an DamagePart used to indicate dmg and radius of explosion for the entity.
     * @param explosionRadius
     * @param damage
     */
    public DamagePart(float explosionRadius, float damage) {
        this.explosionRadius = explosionRadius;
        this.damage = damage;
    }

    /**
     * Returns the radius of explosion
     * @return
     */
    public float getExplosionRadius() {
        return explosionRadius;
    }

    /**
     * sets the radius of explosion
     * @param explosionRadius
     */
    public void setExplosionRadius(float explosionRadius) {
        this.explosionRadius = explosionRadius;
    }

    /**
     * returns the damage the explosion will cause
     * @return
     */
    public float getDamage() {
        return damage;
    }

    /**
     * sets the damage the explosion will cause
     * @param damage
     */
    public void setDamage(float damage) {
        this.damage = damage;
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {
        //Nothing to process
    }
}
