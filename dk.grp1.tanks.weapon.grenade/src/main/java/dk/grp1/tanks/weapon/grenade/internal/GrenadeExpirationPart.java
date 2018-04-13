package dk.grp1.tanks.weapon.grenade.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.eventManager.events.*;
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
            SoundPart sound = entity.getPart(SoundPart.class);

            if (positionPart != null && damagePart != null && explosionTexturePart != null) {
                Event animationEvent = new ExplosionAnimationEvent(entity, new Vector2D(positionPart.getX(), positionPart.getY()), explosionTexturePart, damagePart.getExplosionRadius());
                Event explosionEvent = new ExplosionEvent(entity, new Vector2D(positionPart.getX(), positionPart.getY()), damagePart.getExplosionRadius());
                Event mapDestructionEvent = new MapDestructionEvent(entity,new Vector2D(positionPart.getX(),positionPart.getY()),damagePart.getExplosionRadius());
                Event shakeEvent = new ShakeEvent(entity,damagePart.getExplosionRadius() * 5);
                gameData.getEventManager().addEvent(explosionEvent);
                gameData.getEventManager().addEvent(animationEvent);
                gameData.getEventManager().addEvent(mapDestructionEvent);
                world.removeEntity(entity);
                gameData.getEventManager().addEvent(shakeEvent);
                gameData.getEventManager().addEvent(new SoundEvent(entity, sound.getOnHitSoundPath()));
            }
        }
    }
}
