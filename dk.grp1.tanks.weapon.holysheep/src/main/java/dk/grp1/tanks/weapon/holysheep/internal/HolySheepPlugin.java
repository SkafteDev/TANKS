package dk.grp1.tanks.weapon.holysheep.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.IWeapon;

public class HolySheepPlugin implements IGamePluginService {

    private final IWeapon weapon = new HolySheepWeapon();

    @Override
    public void start(World world, GameData gameData) {
        gameData.addWeapon(weapon);
    }

    @Override
    public void stop(World world, GameData gameData) {
        gameData.removeWeapon(weapon);
        for (Entity entity : world.getEntities(HolySheep.class)) {
            world.removeEntity(entity);
        }
    }
}
