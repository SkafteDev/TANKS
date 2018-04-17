package mapcollisiontests;

import dk.grp1.tanks.common.data.*;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.CollisionPart;
import dk.grp1.tanks.common.services.IPostEntityProcessingService;
import dk.grp1.tanks.mapcollision.internal.MapCollisionProcessing;
import org.junit.Assert;

import java.util.List;

public class MapCollisionProcessingTest {

    @org.junit.Test
    public void postProcessTestWithCollision(){
        IPostEntityProcessingService iPostEntityProcessingService = new MapCollisionProcessing();
        World world = new World();
        GameData gameData = new GameData();
        //Create GameMap
        IGameMapFunction gameMapFunction = new MapFunctionTest();
        GameMap gameMap = new GameMap(500,500);
        gameMap.addGameMapFunction(gameMapFunction);
        world.setGameMap(gameMap);

        //Create Entity
        Entity entity = new TestEntity();
        entity.add(new CollisionPart(true,0f));
        entity.add(new CirclePart(50,52,5));
        world.addEntity(entity);

        iPostEntityProcessingService.postProcess(world,gameData);
        CollisionPart collisionPart = entity.getPart(CollisionPart.class);

        Assert.assertTrue(collisionPart.isHitGameMap());
    }

    @org.junit.Test
    public void postProcessTestNoCollision(){
        IPostEntityProcessingService iPostEntityProcessingService = new MapCollisionProcessing();
        World world = new World();
        GameData gameData = new GameData();
        //Create GameMap
        IGameMapFunction gameMapFunction = new MapFunctionTest();
        GameMap gameMap = new GameMap(500,500);
        gameMap.addGameMapFunction(gameMapFunction);
        world.setGameMap(gameMap);

        //Create Entity
        Entity entity = new TestEntity();
        entity.add(new CollisionPart(true,0f));
        entity.add(new CirclePart(50,100,5));
        world.addEntity(entity);

        iPostEntityProcessingService.postProcess(world,gameData);
        CollisionPart collisionPart = entity.getPart(CollisionPart.class);

        Assert.assertFalse(collisionPart.isHitGameMap());
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void postProcessTestNullInput(){
        IPostEntityProcessingService iPostEntityProcessingService = new MapCollisionProcessing();
        World world = null;
        GameData gameData = null;
        iPostEntityProcessingService.postProcess(world,gameData);
    }

    @org.junit.Test
    public void postProcessTestNoGameMapFunctions(){
        IPostEntityProcessingService iPostEntityProcessingService = new MapCollisionProcessing();
        World world = new World();
        GameData gameData = new GameData();
        GameMap gameMap = new GameMap(500,500);
        world.setGameMap(gameMap);
        //Create Entity
        Entity entity = new TestEntity();
        entity.add(new CollisionPart(true,0f));
        entity.add(new CirclePart(50,100,5));
        world.addEntity(entity);

        iPostEntityProcessingService.postProcess(world,gameData);
        CollisionPart collisionPart = entity.getPart(CollisionPart.class);

        Assert.assertFalse(collisionPart.isHitGameMap());
    }
    @org.junit.Test(expected = IllegalStateException.class)
    public void postProcessTestNoGameMap(){
        IPostEntityProcessingService iPostEntityProcessingService = new MapCollisionProcessing();
        World world = new World();
        GameData gameData = new GameData();
        //Create Entity
        Entity entity = new TestEntity();
        entity.add(new CollisionPart(true,0f));
        entity.add(new CirclePart(50,100,5));
        world.addEntity(entity);
        iPostEntityProcessingService.postProcess(world,gameData);
    }

    private class TestEntity extends Entity {
    }
}
