package dk.grp1.tanks.weapon.boxingglove.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IGamePluginService;

public class BoxingGamePlugin implements IGamePluginService {
    @Override
    public void start(World world, GameData gameData) {
        gameData.addWeapon(new BoxingWeapon());
    }

    @Override
    public void stop(World world, GameData gameData) {
        for (Entity e : world.getEntities(BoxingGlove.class)) {
            world.removeEntity(e);
        }

    }
}
