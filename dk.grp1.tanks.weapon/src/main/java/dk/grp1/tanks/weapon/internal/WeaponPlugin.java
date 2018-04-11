package dk.grp1.tanks.weapon.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.weapon.Projectile;

public class WeaponPlugin implements IGamePluginService {
    @Override
    public void start(World world, GameData gameData) {

    }

    @Override
    public void stop(World world, GameData gameData) {

        for (Entity e: world.getEntities(Projectile.class)
             ) {
            world.removeEntity(e);

        }

    }
}
