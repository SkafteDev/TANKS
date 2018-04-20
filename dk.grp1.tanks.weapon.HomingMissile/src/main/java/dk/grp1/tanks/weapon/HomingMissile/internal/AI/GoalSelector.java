package dk.grp1.tanks.weapon.HomingMissile.internal.AI;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.List;

public class GoalSelector implements IGoalSelector{

    private final World world;
    private GameData gameData;
    private final Entity origin;
    private Entity homingMissile;
    private final int numberOfPoints = 512;

    public GoalSelector(World world, GameData gameData, Entity origin, Entity homingMissile){

        this.world = world;
        this.gameData = gameData;
        this.origin = origin;
        this.homingMissile = homingMissile;
    }

    public State calculateGoalState() {
        List<Vector2D> vertices = world.getGameMap().getVertices(0, gameData.getGameWidth(), numberOfPoints);
        Vector2D bestExplosion;
        int bestCount = 0;
        for (int i = 0; i < vertices.size(); i++) {
            int count = 0;
            float x = vertices.get(i).getX();
            float y = vertices.get(i).getY() + 1;
            for (Entity entity : world.getEntities()) {
                if (isClose(entity, x, y)) {
                    count++;
                }
            }
            if (count > bestCount) {
                bestCount = count;
                bestExplosion = new Vector2D(x, y);
            }
        }

        State goalState = new State(world.getGameMap(),)


    }

}
