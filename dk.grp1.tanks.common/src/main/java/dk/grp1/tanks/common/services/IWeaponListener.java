package dk.grp1.tanks.common.services;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;

/**
 * Listener interface for weapon observers
 */
public interface IWeaponListener {

    /**
     * Weapon added notification
     * @param weapon
     * @param source
     */
    void weaponAdded(IWeapon weapon, Object source);

    /**
     * Weapon removed notification
     * @param weapon
     * @param source
     */
    void weaponRemoved(IWeapon weapon, Object source);
}
