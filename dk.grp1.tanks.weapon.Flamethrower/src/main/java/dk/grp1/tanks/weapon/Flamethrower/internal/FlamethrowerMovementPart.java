package dk.grp1.tanks.weapon.Flamethrower.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.IEntityPart;
import dk.grp1.tanks.common.data.parts.MovementPart;
import dk.grp1.tanks.common.utils.Vector2D;

public class FlamethrowerMovementPart extends MovementPart {

    public FlamethrowerMovementPart(Vector2D velocity, float maxSpeed) {
        super(velocity, maxSpeed);
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {

    }
}
