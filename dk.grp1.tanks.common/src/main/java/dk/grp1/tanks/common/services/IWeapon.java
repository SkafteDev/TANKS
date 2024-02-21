package dk.grp1.tanks.common.services;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;

public interface IWeapon {
    /**
     * Get the name of the Weapon
     * @return
     */
    String getName();

    /**
     * Get the description of the Weapon
     * @return
     */
    String getDescription();

    /**
     * Get the path to the icon of the weapon
     * @return
     */
    String getIconPath();


    /**
     * Shoot a projectile of this type.
     * @param actor
     * @param gameData
     * @param firePower
     * @param world
     */
    void shoot(Entity actor, GameData gameData, float firePower, World world);
}
