package dk.grp1.tanks.common.services;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;

public interface IWeaponListener {
    
    void weaponAdded(IWeapon weapon, Object source);

    void weaponRemoved(IWeapon weapon, Object source);
}
