package dk.grp1.tanks.weapon.HomingMissile.internal.AI;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.World;

public class GoalSelector implements IGoalSelector{

    private final World world;
    private final Entity origin;
    private final float explosionRadius;

    public GoalSelector(World world, Entity origin, float explosionRadius){

        this.world = world;
        this.origin = origin;
        this.explosionRadius = explosionRadius;
    }

    public State calculateGoalState(){

        throw new UnsupportedOperationException();
    }
}
