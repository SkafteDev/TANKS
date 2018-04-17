package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IWeapon;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class InventoryPartTest {


    private List<IWeapon> weapons;
    private Map<IWeapon, Integer> weaponAmmo;
    private IWeapon currentWeapon;
    private InventoryPart ip;
    private IWeapon wep;
    private IWeapon wep2;


    @Test
    public void processPart() {
        wep = new IWeapon() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public String getIconPath() {
                return null;
            }

            @Override
            public void shoot(Entity actor, GameData gameData, float firePower, World world) {

            }
        };
        List<IWeapon> weapons = new ArrayList<>();
        weapons.add(wep);
        ip = new InventoryPart(weapons);
        ip.processPart(null,null,null);
        assertEquals(wep, ip.getCurrentWeapon());
    }

    @Test
    public void setCurrentWeapon() {
        wep = new IWeapon() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public String getIconPath() {
                return null;
            }

            @Override
            public void shoot(Entity actor, GameData gameData, float firePower, World world) {

            }
        };
        ip = new InventoryPart(null);
        ip.setCurrentWeapon(wep);
        assertEquals(wep, ip.getCurrentWeapon());
    }

    @Test
    public void setCurrentWeapon1() {
        wep = new IWeapon() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public String getIconPath() {
                return null;
            }

            @Override
            public void shoot(Entity actor, GameData gameData, float firePower, World world) {

            }
        };
        List<IWeapon> weapons = new ArrayList<>();
        weapons.add(wep);
        ip = new InventoryPart(weapons);
        ip.setCurrentWeapon(0);
        assertEquals(wep, ip.getCurrentWeapon());
    }

    @Test
    public void getCurrentWeapon() {
        wep = new IWeapon() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public String getIconPath() {
                return null;
            }

            @Override
            public void shoot(Entity actor, GameData gameData, float firePower, World world) {

            }
        };
        ip = new InventoryPart(null);
        ip.setCurrentWeapon(wep);
        assertEquals(wep, ip.getCurrentWeapon());
    }

    @Test
    public void decreaseAmmo() {
        //not in use
    }

    @Test
    public void getWeapons() {
        ip = new InventoryPart(null);
        assertNotNull(ip.getWeapons());


        wep = new IWeapon() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public String getIconPath() {
                return null;
            }

            @Override
            public void shoot(Entity actor, GameData gameData, float firePower, World world) {

            }
        };
        List<IWeapon> weapons = new ArrayList<>();
        weapons.add(wep);
        ip = new InventoryPart(weapons);
        assertEquals(weapons, ip.getWeapons());
    }

    @Test
    public void nextWeapon() {
        wep = new IWeapon() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public String getIconPath() {
                return null;
            }

            @Override
            public void shoot(Entity actor, GameData gameData, float firePower, World world) {

            }
        };
        wep2 = new IWeapon() {
            @Override
            public String getName() {
                return "wep2";
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public String getIconPath() {
                return null;
            }

            @Override
            public void shoot(Entity actor, GameData gameData, float firePower, World world) {

            }
        };
        List<IWeapon> weapons = new ArrayList<>();
        weapons.add(wep);
        weapons.add(wep2);
        ip = new InventoryPart(weapons);
        ip.setCurrentWeapon(0);
        ip.nextWeapon();
        assertEquals(wep2, ip.getCurrentWeapon());


        wep = new IWeapon() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public String getIconPath() {
                return null;
            }

            @Override
            public void shoot(Entity actor, GameData gameData, float firePower, World world) {

            }
        };
        wep2 = new IWeapon() {
            @Override
            public String getName() {
                return "wep2";
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public String getIconPath() {
                return null;
            }

            @Override
            public void shoot(Entity actor, GameData gameData, float firePower, World world) {

            }
        };
        weapons = new ArrayList<>();
        weapons.add(wep);
        weapons.add(wep2);
        ip = new InventoryPart(weapons);
        ip.setCurrentWeapon(1);
        ip.nextWeapon();
        assertEquals(wep, ip.getCurrentWeapon());
    }

    @Test
    public void previousWeapon() {
        wep = new IWeapon() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public String getIconPath() {
                return null;
            }

            @Override
            public void shoot(Entity actor, GameData gameData, float firePower, World world) {

            }
        };
        wep2 = new IWeapon() {
            @Override
            public String getName() {
                return "wep2";
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public String getIconPath() {
                return null;
            }

            @Override
            public void shoot(Entity actor, GameData gameData, float firePower, World world) {

            }
        };
        List<IWeapon> weapons = new ArrayList<>();
        weapons.add(wep);
        weapons.add(wep2);
        ip = new InventoryPart(weapons);
        ip.setCurrentWeapon(0);
        ip.previousWeapon();
        assertEquals(wep2, ip.getCurrentWeapon());


        wep = new IWeapon() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public String getIconPath() {
                return null;
            }

            @Override
            public void shoot(Entity actor, GameData gameData, float firePower, World world) {

            }
        };
        wep2 = new IWeapon() {
            @Override
            public String getName() {
                return "wep2";
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public String getIconPath() {
                return null;
            }

            @Override
            public void shoot(Entity actor, GameData gameData, float firePower, World world) {

            }
        };
        weapons = new ArrayList<>();
        weapons.add(wep);
        weapons.add(wep2);
        ip = new InventoryPart(weapons);
        ip.setCurrentWeapon(1);
        ip.previousWeapon();
        assertEquals(wep, ip.getCurrentWeapon());
    }

    @Test
    public void weaponAdded() {
        wep = new IWeapon() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public String getIconPath() {
                return null;
            }

            @Override
            public void shoot(Entity actor, GameData gameData, float firePower, World world) {

            }
        };
        ip = new InventoryPart(weapons);
        ip.weaponAdded(wep, null);
        assertTrue(ip.getWeapons().contains(wep));

        ip.weaponAdded(wep, null);
        assertTrue(ip.getWeapons().contains(wep) && ip.getWeapons().size() == 1);
    }

    @Test
    public void weaponRemoved() {
        wep = new IWeapon() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public String getIconPath() {
                return null;
            }

            @Override
            public void shoot(Entity actor, GameData gameData, float firePower, World world) {

            }
        };
        wep2 = new IWeapon() {
            @Override
            public String getName() {
                return "wep2";
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public String getIconPath() {
                return null;
            }

            @Override
            public void shoot(Entity actor, GameData gameData, float firePower, World world) {

            }
        };
        weapons = new ArrayList<>();
        weapons.add(wep);
        weapons.add(wep2);
        ip = new InventoryPart(weapons);
        ip.setCurrentWeapon(0);
        ip.weaponRemoved(wep, null);
        assertEquals(wep2, ip.getCurrentWeapon());

        wep = new IWeapon() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public String getIconPath() {
                return null;
            }

            @Override
            public void shoot(Entity actor, GameData gameData, float firePower, World world) {

            }
        };
        ip = new InventoryPart(null);
        ip.weaponRemoved(wep, null);
        assertEquals(null, ip.getCurrentWeapon());
    }
}