package explosiontest;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.explosionsystem.internal.ExplosionGamePlugin;

public class ExplosionGamePluginTest {

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void startTestNullInput(){
        World world = null;
        GameData gameData = null;
        IGamePluginService explosionGamePlugin = new ExplosionGamePlugin();
        explosionGamePlugin.start(world,gameData);

    }
    @org.junit.Test
    public void startTest(){
        World world = new World();
        GameData gameData = new GameData();
        IGamePluginService explosionGamePlugin = new ExplosionGamePlugin();
        explosionGamePlugin.start(world,gameData);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void stopTestNullInput(){
        World world = new World();
        GameData gameData = new GameData();
        IGamePluginService explosionGamePlugin = new ExplosionGamePlugin();

        explosionGamePlugin.start(world,gameData);

        world = null;
        gameData = null;
        explosionGamePlugin.stop(world,gameData);

    }
    @org.junit.Test(expected = IllegalStateException.class)
    public void stopTestBeforeStart(){
        World world = new World();
        GameData gameData = new GameData();
        IGamePluginService explosionGamePlugin = new ExplosionGamePlugin();
        explosionGamePlugin.stop(world,gameData);
    }

    @org.junit.Test
    public void stopTest(){
        World world = new World();
        GameData gameData = new GameData();
        IGamePluginService explosionGamePlugin = new ExplosionGamePlugin();
        explosionGamePlugin.start(world,gameData);
        explosionGamePlugin.stop(world,gameData);
    }

}
