package dk.grp1.tanks.weapon.grenade.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.DamagePart;
import dk.grp1.tanks.common.data.parts.ExpirationPart;
import dk.grp1.tanks.common.data.parts.ExplosionTexturePart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.eventManager.events.Event;
import dk.grp1.tanks.common.eventManager.events.ExplosionAnimationEvent;
import dk.grp1.tanks.common.eventManager.events.ExplosionEvent;
import dk.grp1.tanks.common.eventManager.events.MapDestructionEvent;
import dk.grp1.tanks.common.utils.Vector2D;

public class GrenadeExpirationPart extends ExpirationPart {

    public GrenadeExpirationPart(float timeToLive){
        super.setRemainingLifeTime(timeToLive);
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {
        super.setRemainingLifeTime(super.getRemainingLifeTime() - gameData.getDelta());

        if (getRemainingLifeTime() == 0){
            PositionPart positionPart = entity.getPart(PositionPart.class);
            DamagePart damagePart = entity.getPart(DamagePart.class);
            ExplosionTexturePart explosionTexturePart = entity.getPart(ExplosionTexturePart.class);

            if (positionPart != null && damagePart != null && explosionTexturePart != null) {
                Event animationEvent = new ExplosionAnimationEvent(entity, new Vector2D(positionPart.getX(), positionPart.getY()), explosionTexturePart, damagePart.getExplosionRadius());
                Event explosionEvent = new ExplosionEvent(entity, new Vector2D(positionPart.getX(), positionPart.getY()), damagePart.getExplosionRadius());
                Event mapDestructionEvent = new MapDestructionEvent(entity,new Vector2D(positionPart.getX(),positionPart.getY()),damagePart.getExplosionRadius());
                gameData.addEvent(explosionEvent);
                gameData.addEvent(animationEvent);
                gameData.addEvent(mapDestructionEvent);
                world.removeEntity(entity);
            }
        }
    }
}
