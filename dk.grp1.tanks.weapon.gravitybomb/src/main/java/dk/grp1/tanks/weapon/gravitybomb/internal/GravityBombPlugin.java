package dk.grp1.tanks.weapon.gravitybomb.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.IWeapon;

public class GravityBombPlugin implements IGamePluginService {

    private final IWeapon weapon = new GravityBombWeapon();

    @Override
    public void start(World world, GameData gameData) {
        gameData.addWeapon(weapon);
    }

    @Override
    public void stop(World world, GameData gameData) {
        for (Entity e : world.getEntities(GravityBomb.class)) {
            world.removeEntity(e);
        }

        gameData.removeWeapon(weapon);

    }
}
