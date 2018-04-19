package enemytests;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.enemy.internal.EnemyGamePlugin;
import org.junit.Assert;

public class EnemyPluginTest {

    @org.junit.Test
    public void enemyGamePluginStartTest(){
        IGamePluginService enemyGamePlugin = new EnemyGamePlugin();
        World world = new World();
        GameData gameData = new GameData();
        enemyGamePlugin.start(world,gameData);
        Assert.assertFalse(world.getEntities().isEmpty());
    }
    @org.junit.Test(expected = IllegalArgumentException.class)
    public void enemyGamePluginStartTestNullInputWorld(){
        IGamePluginService enemyGamePlugin = new EnemyGamePlugin();
        World world = null;
        GameData gameData = new GameData();
        enemyGamePlugin.start(world,gameData);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void enemyGamePluginStartTestNullInputGameData(){
        IGamePluginService enemyGamePlugin = new EnemyGamePlugin();
        World world = new World();
        GameData gameData = null;
        enemyGamePlugin.start(world,gameData);
    }
    @org.junit.Test
    public void enemyGamePluginStopTest(){
        IGamePluginService enemyGamePlugin = new EnemyGamePlugin();
        World world = new World();
        GameData gameData = new GameData();
        enemyGamePlugin.stop(world,gameData);
    }
    
    @org.junit.Test
    public void enemyGamePluginStopAfterStartTest(){
        IGamePluginService enemyGamePlugin = new EnemyGamePlugin();
        World world = new World();
        GameData gameData = new GameData();
        enemyGamePlugin.start(world,gameData);
        enemyGamePlugin.stop(world,gameData);
        Assert.assertTrue(world.getEntities().isEmpty());
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void enemyGamePluginStopTestNullInputWorld(){
        IGamePluginService enemyGamePlugin = new EnemyGamePlugin();
        World world = null;
        GameData gameData = new GameData();
        enemyGamePlugin.stop(world,gameData);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void enemyGamePluginStopTestNullInputGameData(){
        IGamePluginService enemyGamePlugin = new EnemyGamePlugin();
        World world = new World();
        GameData gameData = null;
        enemyGamePlugin.stop(world,gameData);
    }

}
