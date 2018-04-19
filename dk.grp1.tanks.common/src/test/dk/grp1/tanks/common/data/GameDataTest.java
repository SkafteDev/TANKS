package dk.grp1.tanks.common.data;

import dk.grp1.tanks.common.data.parts.InventoryPart;
import dk.grp1.tanks.common.eventManager.EventManager;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.common.services.IWeaponListener;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameDataTest {

    private GameData gameData;

    @Before
    public void setUp() throws Exception {
        gameData = new GameData(400, 400);
        gameData.setDelta(1);
    }

    @Test
    public void shouldEndTurn() {
        assertEquals(false, gameData.shouldEndTurn());
    }

    @Test
    public void setShouldEndTurn() {
        gameData.setShouldEndTurn(true);
        assertEquals(true, gameData.shouldEndTurn());
    }

    @Test
    public void getKeys() {
        assertNotNull(gameData.getKeys());
    }

    @Test
    public void setDelta() {
        assertEquals(1, gameData.getDelta(), 0.001);
        gameData.setDelta(5);
        assertEquals(5, gameData.getDelta(), 0.001);
    }

    @Test
    public void getDelta() {
        assertEquals(1, gameData.getDelta(), 0.001);
    }

    @Test
    public void setDisplayWidth() {
        assertEquals(400, gameData.getGameWidth(), 0.001);
        gameData.setGameWidth(500);
        assertEquals(500, gameData.getGameWidth(), 0.001);
    }

    @Test
    public void getGameWidth() {
        assertEquals(400, gameData.getGameWidth(), 0.001);
    }

    @Test
    public void getGameHeight() {
        assertEquals(400, gameData.getGameHeight(), 0.001);
    }

    @Test
    public void setDisplayHeight() {
        assertEquals(400, gameData.getGameHeight(), 0.001);
        gameData.setGameHeight(500);
        assertEquals(500, gameData.getGameHeight(), 0.001);
    }

    @Test
    public void getEventManager() {
        assertNotNull(gameData.getEventManager());
        assertTrue(gameData.getEventManager() instanceof EventManager);
    }

    @Test
    public void addWeapon() {
        InventoryPart ip = new InventoryPart(null);
        gameData.addWeaponListener(ip);
        IWeapon wep = new IWeapon() {
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
        assertEquals(0, ip.getWeapons().size());
        gameData.addWeapon(wep);
        assertEquals(1, ip.getWeapons().size());
        assertTrue(gameData.getWeapons().contains(wep));
    }

    @Test
    public void removeWeapon() {
        InventoryPart ip = new InventoryPart(null);
        gameData.addWeaponListener(ip);
        IWeapon wep = new IWeapon() {
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
        gameData.addWeapon(wep);
        assertEquals(1, ip.getWeapons().size());
        assertTrue(gameData.getWeapons().contains(wep));
        gameData.removeWeapon(wep);
        assertEquals(0, ip.getWeapons().size());
        assertFalse(gameData.getWeapons().contains(wep));
    }

    @Test
    public void addWeaponListener() {
        InventoryPart ip = new InventoryPart(null);
        gameData.addWeaponListener(ip);
        IWeapon wep = new IWeapon() {
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
        assertEquals(0, ip.getWeapons().size());
        gameData.addWeapon(wep);
        assertEquals(1, ip.getWeapons().size());
    }

    @Test
    public void removeWeponListener() {
        InventoryPart ip = new InventoryPart(null);
        gameData.addWeaponListener(ip);
        IWeapon wep = new IWeapon() {
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
        assertEquals(0, ip.getWeapons().size());
        gameData.removeWeponListener(ip);
        gameData.addWeapon(wep);
        assertEquals(0, ip.getWeapons().size());
    }

    @Test
    public void getWeapons() {
        ArrayList<IWeapon> wep = new ArrayList<>();
        wep.add(new IWeapon() {
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
        });
        wep.add(new IWeapon() {
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
        });
        wep.add(new IWeapon() {
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
        });
        wep.add(new IWeapon() {
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
        });

        for (IWeapon weapon : wep) {
            gameData.addWeapon(weapon);
        }

        for (IWeapon weapon : wep) {
            assertTrue(gameData.getWeapons().contains(weapon));
        }
    }
}