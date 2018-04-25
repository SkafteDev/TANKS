package dk.grp1.tanks.roundendsystem.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.TurnPart;
import dk.grp1.tanks.common.services.IRoundService;

/**
 * Internal implementation of our example OSGi service
 */
public final class RoundService
        implements IRoundService {

    @Override
    public boolean isRoundOver(World world) {
        return false;
//        int count = 0;
//        for (Entity entity : world.getEntities()
//                ) {
//            TurnPart turnPart = entity.getPart(TurnPart.class);
//            if (turnPart != null) {
//                count++;
//            }
//
//        }
//
//        return count <= 1;
    }

    @Override
    public Entity getRoundWinner(World world) {
        if (!isRoundOver(world)) {
            return null;
        }
        for (Entity entity : world.getEntities()
                ) {
            TurnPart turnPart = entity.getPart(TurnPart.class);
            if (turnPart != null) {
                return entity;
            }
        }
        return null;

    }

    @Override
    public float getTimeRemaining() {
        return 0;
    }
}

