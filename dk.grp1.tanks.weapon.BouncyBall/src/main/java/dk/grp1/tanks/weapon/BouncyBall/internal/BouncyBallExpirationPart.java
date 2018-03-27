package dk.grp1.tanks.weapon.BouncyBall.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.DamagePart;
import dk.grp1.tanks.common.data.parts.ExpirationPart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.events.Event;
import dk.grp1.tanks.common.events.ExplosionEvent;
import dk.grp1.tanks.common.events.MapDestructionEvent;
import dk.grp1.tanks.common.utils.Vector2D;

public class BouncyBallExpirationPart extends ExpirationPart {

    public BouncyBallExpirationPart(float timeToLive){
        super.setRemainingLifeTime(timeToLive);
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {
        super.setRemainingLifeTime(super.getRemainingLifeTime() - gameData.getDelta());

        if (getRemainingLifeTime() == 0){
            world.removeEntity(entity);
        }
    }
}
