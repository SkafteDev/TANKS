package dk.grp1.tanks.weapon.boxingglove.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.eventManager.events.*;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.Collection;

public class BoxingCollisionPart extends CollisionPart {
    private final float radius;
    private final float force;

    /**
     * Creates a collision part for an entity
     *
     * @param canCollide
     * @param minTimeBetweenCollision
     */
    public BoxingCollisionPart(boolean canCollide, float minTimeBetweenCollision, float radius, float force) {
        super(canCollide, minTimeBetweenCollision);
        this.radius = radius;
        this.force = force;
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        DamagePart damagePart = entity.getPart(DamagePart.class);
        ExplosionTexturePart explosionTexturePart = entity.getPart(ExplosionTexturePart.class);
        CirclePart circlePart = entity.getPart(CirclePart.class);

        if ((this.isHitEntity() || this.isHitGameMap()) && positionPart != null && damagePart != null) {
            if(explosionTexturePart != null) {
                Event animationEvent = new ExplosionAnimationEvent(entity, new Vector2D(positionPart.getX(), positionPart.getY()), explosionTexturePart, damagePart.getExplosionRadius());
                gameData.getEventManager().addEvent(animationEvent);
            }
            Event explosionEvent = new ExplosionEvent(entity, new Vector2D(positionPart.getX(), positionPart.getY()), damagePart.getExplosionRadius());
            Event mapDestructionEvent = new MapDestructionEvent(entity, new Vector2D(positionPart.getX(), positionPart.getY()), damagePart.getExplosionRadius());
            Event pushEvent = new PushEvent(entity,new Vector2D(positionPart.getX(), positionPart.getY()), force , radius);
            SoundPart soundPart = entity.getPart(SoundPart.class);
            if(soundPart != null){
                Event soundEvent = new SoundEvent(entity,soundPart.getOnHitSoundPath());
                gameData.getEventManager().addEvent(soundEvent);

            }
            gameData.getEventManager().addEvent(explosionEvent);
            gameData.getEventManager().addEvent(mapDestructionEvent);
            gameData.getEventManager().addEvent(pushEvent);


            world.removeEntity(entity);
        } else if(this.isHitGameMap() && positionPart != null && circlePart != null && world.getGameMap().getHeight(new Vector2D(positionPart.getX(),positionPart.getY()))-2f > positionPart.getY()-circlePart.getRadius()){
            positionPart.setY(positionPart.getY()+3f);
        }
    }



}
