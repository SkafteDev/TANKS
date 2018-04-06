package dk.grp1.tanks.DeadWeight.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IGamePluginService;

public class DeadWeightPlugin implements IGamePluginService {

    @Override
    public void start(World world, GameData gameData) {

    }

    @Override
    public void stop(World world, GameData gameData) {
        for (Entity entity: world.getEntities(DeadWeight.class)){
            world.removeEntity(entity);
        }
    }
}
