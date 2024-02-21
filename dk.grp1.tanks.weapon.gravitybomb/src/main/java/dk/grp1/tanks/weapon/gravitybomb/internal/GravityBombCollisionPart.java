package dk.grp1.tanks.weapon.gravitybomb.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.eventManager.events.Event;
import dk.grp1.tanks.common.eventManager.events.ExplosionAnimationEvent;
import dk.grp1.tanks.common.eventManager.events.ExplosionEvent;
import dk.grp1.tanks.common.eventManager.events.SoundEvent;
import dk.grp1.tanks.common.utils.Vector2D;

public class GravityBombCollisionPart extends CollisionPart {

    private int hits = 0; //damage increases based on number of entities hit

    /**
     * Creates a collision part for an entity
     *
     * @param canCollide
     * @param minTimeBetweenCollision
     */
    public GravityBombCollisionPart(boolean canCollide, float minTimeBetweenCollision) {
        super(canCollide, minTimeBetweenCollision);
    }

    /**
     * Processes gravity bomb collision
     * @param entity
     * @param gameData
     * @param world
     */
    @Override
    public void processPart(Entity entity, GameData gameData, World world){
        if (isHitGameMap() || isHitEntity()){

            for (Entity entity2: world.getEntities()){
                if (entity != entity2 && hasCollided(entity, entity2)){
                    gravityPull(entity, entity2);
                    hits++; //damage scales off hits
                }

            }
            makeEvents(entity,gameData);
            world.removeEntity(entity);
        }
    }

    /**
     * Create explosion events, but no map destruction event
     * @param entity
     * @param gameData
     */
    private void makeEvents(Entity entity, GameData gameData) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        DamagePart damagePart = entity.getPart(DamagePart.class);
        ExplosionTexturePart explosionTexturePart = entity.getPart(ExplosionTexturePart.class);

        //Explode if collision with map or entity
        if ((this.isHitEntity() || this.isHitGameMap()) && positionPart != null && damagePart != null && explosionTexturePart != null) {
            Event animationEvent = new ExplosionAnimationEvent(entity, new Vector2D(positionPart.getX(), positionPart.getY()), explosionTexturePart, damagePart.getExplosionRadius());
            Event explosionEvent = new ExplosionEvent(entity, new Vector2D(positionPart.getX(), positionPart.getY()), damagePart.getExplosionRadius());
            SoundPart soundPart = entity.getPart(SoundPart.class);
            if (soundPart != null) { //play sound if there is a sound part
                Event soundEvent = new SoundEvent(entity, soundPart.getOnHitSoundPath());
                gameData.getEventManager().addEvent(soundEvent);

            }
            gameData.getEventManager().addEvent(animationEvent);
            for (int i = 0; i < hits; i++) { //Damage is proportional with number of enemies hit.
                gameData.getEventManager().addEvent(explosionEvent);
            }
        }
    }

    /**
     * Moves the position of all hit entities to the explosion's center
     * @param bullet
     * @param e
     */
    private void gravityPull(Entity bullet, Entity e){
        PositionPart positionPart = e.getPart(PositionPart.class);
        PositionPart bulletPos = bullet.getPart(PositionPart.class);

        if (positionPart.getX() > bulletPos.getX()){
            positionPart.setX(bulletPos.getX());
        } else {
            positionPart.setX(bulletPos.getX());
        }

    }

    /**
     * Circle collision of explosion and other entities
     * @param entity1
     * @param entity2
     * @return
     */
    private boolean hasCollided(Entity entity1, Entity entity2){
        CirclePart circlePart1 = entity1.getPart(CirclePart.class);
        DamagePart damagePart = entity1.getPart(DamagePart.class);
        CirclePart circlePart2 = entity2.getPart(CirclePart.class);

        //no collision if no circle part
        if (circlePart1 == null || circlePart2 == null || entity1.getClass().equals(entity2.getClass())){
            return false;
        }

        float distX = circlePart1.getCentreX() - circlePart2.getCentreX();
        float distY = circlePart1.getCentreY() - circlePart2.getCentreY();
        float distance = (float)(Math.sqrt(distX*distX + distY*distY));
        return distance < (damagePart.getExplosionRadius() + circlePart2.getRadius());
    }
}
