package dk.grp1.tanks.common.services;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.World;

public interface IWeapon {
    String getName();
    String getDescription();
    String getIconPath();
    String getShootSoundPath();
    void shoot(Entity actor, float firePower, World world);
}
