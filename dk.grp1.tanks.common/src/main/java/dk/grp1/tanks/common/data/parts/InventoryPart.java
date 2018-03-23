package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.services.IWeapon;

import java.util.*;

public class InventoryPart implements IEntityPart {

    private Set<IWeapon> weapons;
    private Map<IWeapon, Integer> weaponAmmo;
    private IWeapon currentWeapon;


    public InventoryPart() {
        weapons = new HashSet<>();
        weaponAmmo = new HashMap<>();
    }

    @Override
    public void processPart(Entity entity, GameData gameData) {

    }

    public void setCurrentWeapon(IWeapon weapon) {
        this.currentWeapon = weapon;
    }

    public IWeapon getCurrentWeapon() {
        return this.currentWeapon;
    }

    public void decreaseAmmo() {
        Integer currentWeaponAmmo = weaponAmmo.get(currentWeapon);
        currentWeaponAmmo--;
        weaponAmmo.put(currentWeapon, currentWeaponAmmo);

        if (currentWeaponAmmo <= 0) {
            for (IWeapon weapon : weapons) {
                if (weaponAmmo.get(weapon) > 0) {
                    currentWeapon = weapon;
                    return;
                }
            }
        }
    }

    /**
     *
     * For declarative services // dependency injection
     *
     */
    public void addWeapon(IWeapon weapon) {
        this.weapons.add(weapon);
    }

    public void removeWeapon(IWeapon weapon) {
        this.weapons.remove(weapon);
    }
}
