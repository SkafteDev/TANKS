package dk.grp1.tanks.weapon.HomingMissile.internal.AI;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.World;

public interface IGoalSelector {
    public State calculateGoalState(World world, Entity origin);
}
