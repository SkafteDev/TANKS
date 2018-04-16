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
     *
     * @param explosionRadius
     * @param damage
     */
    public DamagePart(float explosionRadius, float damage) {
        this.explosionRadius = explosionRadius;
        this.damage = damage;
    }

    /**
     *
     * @return
     */
    public float getExplosionRadius() {
        return explosionRadius;
    }

    /**
     *
     * @param explosionRadius
     */
    public void setExplosionRadius(float explosionRadius) {
        this.explosionRadius = explosionRadius;
    }

    /**
     *
     * @return
     */
    public float getDamage() {
        return damage;
    }

    /**
     *
     * @param damage
     */
    public void setDamage(float damage) {
        this.damage = damage;
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {

    }
}
