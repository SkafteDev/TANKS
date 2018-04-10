package dk.grp1.tanks.weapon.MadCat.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.IWeapon;

public class MadCatGamePlugin implements IGamePluginService {

    private final IWeapon weapon = new MadCatWeapon();

    @Override
    public void start(World world, GameData gameData) {
        gameData.addWeapon(weapon);
    }

    @Override
    public void stop(World world, GameData gameData) {
        for (Entity madCat : world.getEntities(MadCat.class)) {
            world.removeEntity(madCat);
        }

        gameData.removeWeapon(weapon);
    }
}
