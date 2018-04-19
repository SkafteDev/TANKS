package maptests;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameMap;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.gamemap.internal.GameMapPlugin;
import org.junit.Assert;
import org.junit.Test;

public class GameMapPluginTest {
    @Test
    public void testStart(){
        World world = new World();
        GameData gd = new GameData();
        GameMapPlugin plugin = new GameMapPlugin();
        Assert.assertTrue(world.getGameMap() == null);
        plugin.start(world,gd);
        Assert.assertTrue(world.getGameMap() != null);
        Assert.assertFalse(world.getGameMap().getGameMapFunctions().isEmpty());

    }

    @Test
    public void testStop(){
        World world = new World();
        GameData gd = new GameData();
        GameMapPlugin plugin = new GameMapPlugin();
        plugin.start(world,gd);
        plugin.stop(world,gd);
        Assert.assertTrue(world.getGameMap().getGameMapFunctions().isEmpty());

    }
}
