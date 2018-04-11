package dk.grp1.tanks.weapon.boxingglove.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.IWeapon;

public class BoxingGamePlugin implements IGamePluginService {
    private final IWeapon boxingWeapon = new BoxingWeapon();
    @Override
    public void start(World world, GameData gameData) {
        gameData.addWeapon(boxingWeapon);
    }

    @Override
    public void stop(World world, GameData gameData) {
        gameData.removeWeapon(boxingWeapon);
        for (Entity e : world.getEntities(BoxingGlove.class)) {
            world.removeEntity(e);
        }

    }
}
