package dk.grp1.tanks.weapon.Flamethrower.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.IWeapon;

public class FlamethrowerPlugin implements IGamePluginService{

    private final IWeapon  weapon = new FlamethrowerWeapon();

    @Override
    public void start(World world, GameData gameData) {
        gameData.addWeapon(weapon);
    }

    @Override
    public void stop(World world, GameData gameData) {
        for (Entity entity : world.getEntities(Flamethrower.class)) {
            world.removeEntity(entity);
        }

        gameData.removeWeapon(weapon);
    }
}
