package dk.grp1.tanks.weapon.nuke.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.IWeapon;

public class NukeGamePlugin implements IGamePluginService {
    private IWeapon nukeWeapon;

    @Override
    public void start(World world, GameData gameData) {
        nukeWeapon = new NukeWeapon();
        gameData.addWeapon(nukeWeapon);
    }

    @Override
    public void stop(World world, GameData gameData) {
        for (Entity e : world.getEntities(Nuke.class)) {
            world.removeEntity(e);
        }
        gameData.removeWeapon(nukeWeapon);
    }
}
