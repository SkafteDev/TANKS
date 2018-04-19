package playertests;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.player.internal.PlayerGamePlugin;
import org.junit.Assert;

public class PlayerPluginTest {
    @org.junit.Test
    public void playerGamePluginStartTest(){
        IGamePluginService enemyGamePlugin = new PlayerGamePlugin();
        World world = new World();
        GameData gameData = new GameData();
        enemyGamePlugin.start(world,gameData);
        Assert.assertFalse(world.getEntities().isEmpty());
    }
    @org.junit.Test(expected = IllegalArgumentException.class)
    public void playerGamePluginStartTestNullInputWorld(){
        IGamePluginService enemyGamePlugin = new PlayerGamePlugin();
        World world = null;
        GameData gameData = new GameData();
        enemyGamePlugin.start(world,gameData);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void playerGamePluginStartTestNullInputGameData(){
        IGamePluginService enemyGamePlugin = new PlayerGamePlugin();
        World world = new World();
        GameData gameData = null;
        enemyGamePlugin.start(world,gameData);
    }
    @org.junit.Test
    public void playerGamePluginStopTest(){
        IGamePluginService enemyGamePlugin = new PlayerGamePlugin();
        World world = new World();
        GameData gameData = new GameData();
        enemyGamePlugin.stop(world,gameData);
    }

    @org.junit.Test
    public void playerGamePluginStopAfterStartTest(){
        IGamePluginService enemyGamePlugin = new PlayerGamePlugin();
        World world = new World();
        GameData gameData = new GameData();
        enemyGamePlugin.start(world,gameData);
        enemyGamePlugin.stop(world,gameData);
        Assert.assertTrue(world.getEntities().isEmpty());
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void playerGamePluginStopTestNullInputWorld(){
        IGamePluginService enemyGamePlugin = new PlayerGamePlugin();
        World world = null;
        GameData gameData = new GameData();
        enemyGamePlugin.stop(world,gameData);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void playerGamePluginStopTestNullInputGameData(){
        IGamePluginService enemyGamePlugin = new PlayerGamePlugin();
        World world = new World();
        GameData gameData = null;
        enemyGamePlugin.stop(world,gameData);
    }

}
