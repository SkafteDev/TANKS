package dk.grp1.tanks.weapon.ElVicto.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.IWeapon;

public class ElVictoPlugin implements IGamePluginService {

    private final IWeapon weapon = new ElVictoWeapon();

    @Override
    public void start(World world, GameData gameData) {
        gameData.addWeapon(weapon);

    }

    @Override
    public void stop(World world, GameData gameData) {
        for (Entity e : world.getEntities(ElVicto.class)) {
            world.removeEntity(e);
        }

        gameData.removeWeapon(weapon);
    }
}
