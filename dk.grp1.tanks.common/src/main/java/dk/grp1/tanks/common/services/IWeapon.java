package dk.grp1.tanks.common.services;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;

public interface IWeapon {
    String getName();
    String getDescription();
    String getIconPath();
    String getShootSoundPath();
    void shoot(Entity actor, GameData gameData, float firePower, World world);
}
