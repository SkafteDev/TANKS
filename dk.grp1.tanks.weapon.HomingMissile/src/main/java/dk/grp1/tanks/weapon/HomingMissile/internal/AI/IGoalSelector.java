package dk.grp1.tanks.weapon.HomingMissile.internal.AI;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.World;

public interface IGoalSelector {

    /**
     * Finds the best position for the homing missile to explode
     * Maximizing number of enemy entities that will be hit
     * If multiple places are equally good, the first one found is returned.
     * @return
     */
    public State calculateGoalState();
}
