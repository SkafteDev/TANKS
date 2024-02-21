package dk.grp1.tanks.weapon.Teleporter.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CollisionPart;
import dk.grp1.tanks.common.data.parts.ExplosionTexturePart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.data.parts.SoundPart;
import dk.grp1.tanks.common.eventManager.events.*;
import dk.grp1.tanks.common.utils.Vector2D;

public class TeleportCollisionPart extends CollisionPart {

    private Entity parent;

    /**
     * Creates a collision part for an entity
     *
     * @param canCollide
     * @param minTimeBetweenCollision
     */
    public TeleportCollisionPart(boolean canCollide, float minTimeBetweenCollision, Entity parent) {
        super(canCollide, minTimeBetweenCollision);
        this.parent = parent;
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {

        // if collision is detected, move parent to the entity location
        if (this.isHitGameMap()){
            PositionPart parentPos = parent.getPart(PositionPart.class);
            PositionPart projPos = entity.getPart(PositionPart.class);
            parentPos.setX(projPos.getX());
            parentPos.setY(projPos.getY());

            // fire explosion, sound, shake and animation events
            Event explosionEvent = new ExplosionEvent(entity, new Vector2D(projPos.getX(), projPos.getY()), 10);
            Event shakeEvent = new ShakeEvent(entity,10 * 5);
            SoundPart soundPart = entity.getPart(SoundPart.class);
            if(soundPart != null){
                Event soundEvent = new SoundEvent(entity,soundPart.getOnHitSoundPath());
                gameData.getEventManager().addEvent(soundEvent);

            }

            ExplosionTexturePart explosionTexturePart = entity.getPart(ExplosionTexturePart.class);
            if(explosionTexturePart != null) {
                Event animationEvent = new ExplosionAnimationEvent(entity, new Vector2D(projPos.getX(), projPos.getY()), explosionTexturePart, 10);
                gameData.getEventManager().addEvent(animationEvent);
            }

            gameData.getEventManager().addEvent(explosionEvent);
            gameData.getEventManager().addEvent(shakeEvent);

            world.removeEntity(entity);
        }
    }
}
