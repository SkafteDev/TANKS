package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IWeapon;

import java.util.*;

public class InventoryPart implements IEntityPart {

    private List<IWeapon> weapons;
    private Map<IWeapon, Integer> weaponAmmo;
    private IWeapon currentWeapon;


    public InventoryPart() {
        weapons = new ArrayList<>();
        weaponAmmo = new HashMap<>();
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {

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

    public void setWeapons(List<IWeapon> weapons){
        this.weapons = weapons;

        if (!this.weapons.isEmpty()) {
            this.currentWeapon = this.weapons.get(0);
        }
        //TODO: Ammo handling
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
