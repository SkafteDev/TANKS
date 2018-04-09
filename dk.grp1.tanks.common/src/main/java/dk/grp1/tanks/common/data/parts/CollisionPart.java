package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.eventManager.events.Event;
import dk.grp1.tanks.common.eventManager.events.ExplosionAnimationEvent;
import dk.grp1.tanks.common.eventManager.events.ExplosionEvent;
import dk.grp1.tanks.common.eventManager.events.MapDestructionEvent;
import dk.grp1.tanks.common.utils.Vector2D;

/**
 * Created by danie on 12-03-2018.
 */
public class CollisionPart implements IEntityPart {

    private boolean canCollide;
    private boolean isHitEntity;
    private boolean isHitGameMap;
    private float timeSinceLastCollision;
    private float minTimeBetweenCollision;


    /**
     * Creates a collision part for an entity
     *
     * @param canCollide
     * @param minTimeBetweenCollision
     */
    public CollisionPart(boolean canCollide, float minTimeBetweenCollision) {
        this.canCollide = canCollide;
        this.minTimeBetweenCollision = minTimeBetweenCollision;
        this.isHitEntity = false;
        this.isHitGameMap = false;
        this.timeSinceLastCollision = 0;
    }

    public void processPart(Entity entity, GameData gameData, World world) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        DamagePart damagePart = entity.getPart(DamagePart.class);
        ExplosionTexturePart explosionTexturePart = entity.getPart(ExplosionTexturePart.class);
        CirclePart circlePart = entity.getPart(CirclePart.class);

        if ((this.isHitEntity() || this.isHitGameMap()) && positionPart != null && damagePart != null && explosionTexturePart != null) {
            Event animationEvent = new ExplosionAnimationEvent(entity, new Vector2D(positionPart.getX(), positionPart.getY()), explosionTexturePart, damagePart.getExplosionRadius());
            Event explosionEvent = new ExplosionEvent(entity, new Vector2D(positionPart.getX(), positionPart.getY()), damagePart.getExplosionRadius());
            Event mapDestructionEvent = new MapDestructionEvent(entity, new Vector2D(positionPart.getX(), positionPart.getY()), damagePart.getExplosionRadius());

            gameData.getEventManager().addEvent(animationEvent);
            gameData.getEventManager().addEvent(explosionEvent);
            gameData.getEventManager().addEvent(mapDestructionEvent);
            world.removeEntity(entity);
        } else if(this.isHitGameMap() && positionPart != null && circlePart != null && world.getGameMap().getHeight(new Vector2D(positionPart.getX(),positionPart.getY()))-2f > positionPart.getY()-circlePart.getRadius()){
            positionPart.setY(positionPart.getY()+3f);
        }
    }


    public boolean isHitGameMap() {
        return isHitGameMap;
    }

    public void setHitGameMap(boolean hitGameMap) {
        isHitGameMap = hitGameMap;
    }

    /**
     * returns if the entity can collide
     *
     * @return boolean
     */
    public boolean canCollide() {
        return canCollide;
    }

    /**
     * Sets if the entity can collide
     *
     * @param canCollide boolean
     */
    public void setCanCollide(boolean canCollide) {
        this.canCollide = canCollide;
    }

    /**
     * returns if the entity is hit
     *
     * @return
     */
    public boolean isHitEntity() {
        return isHitEntity;
    }

    /**
     * sets if the entity is hitEntity
     *
     * @param hitEntity
     */
    public void setHitEntity(boolean hitEntity) {
        isHitEntity = hitEntity;
    }


}
