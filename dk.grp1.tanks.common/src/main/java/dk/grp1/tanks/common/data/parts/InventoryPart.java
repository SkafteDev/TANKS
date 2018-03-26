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
        if (currentWeapon == null){
            currentWeapon = weapons.get(0);
        }
    }

    public void setCurrentWeapon(IWeapon weapon) {
        this.currentWeapon = weapon;
    }
    public void setCurrentWeapon(int i) {
        this.currentWeapon = weapons.get(i);
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
        System.out.println("Weapon added!");
    }

    public void removeWeapon(IWeapon weapon) {
        this.weapons.remove(weapon);
        System.out.println("Weapon removed!");
    }

    public void nextWeapon() {
        int i = weapons.indexOf(this.getCurrentWeapon());
        i++;

        i = i % weapons.size();

        this.currentWeapon = weapons.get(i);
    }


    public void previousWeapon(){
        int i = weapons.indexOf(this.currentWeapon);
        i--;

        if (i == -1){
            this.currentWeapon = weapons.get(weapons.size()-1);
        } else{
            this.currentWeapon = weapons.get(i);
        }
    }
}
