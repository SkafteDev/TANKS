package dk.grp1.tanks.weapon.grenade.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.IWeapon;

public class GrenadePlugin implements IGamePluginService {
    @Override
    public void start(World world, GameData gameData) {
        IWeapon weapon = new GrenadeWeapon();
        gameData.addWeapon(weapon);
    }

    @Override
    public void stop(World world, GameData gameData) {
        for (Entity e : world.getEntities(Grenade.class)) {
            world.removeEntity(e);
        }
    }
}
