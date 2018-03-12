package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;

/**
 * Created by danie on 12-03-2018.
 */
public class CollisionPart implements IEntityPart {

    private boolean canCollide;
    private boolean isHit;
    private float timeSinceLastCollision;
    private float minTimeBetweenCollision;

    /**
     * Creates a collision part for an entity
     * @param canCollide
     * @param minTimeBetweenCollision
     */
    public CollisionPart(boolean canCollide, float minTimeBetweenCollision) {
        this.canCollide = canCollide;
        this.minTimeBetweenCollision = minTimeBetweenCollision;
        this.isHit = false;
        this.timeSinceLastCollision = 0;
    }

    /**
     * returns if the entity can collide
     * @return boolean
     */
    public boolean canCollide() {
        return canCollide;
    }

    /**
     * Sets if the entity can collide
     * @param canCollide boolean
     */
    public void setCanCollide(boolean canCollide) {
        this.canCollide = canCollide;
    }

    /**
     * returns if the entity is hit
     * @return
     */
    public boolean isHit() {
        return isHit;
    }

    /**
     * sets if the entity is hit
     * @param hit
     */
    public void setHit(boolean hit) {
        isHit = hit;
    }

    public void processPart(Entity entity, GameData gameData) {

    }
}
