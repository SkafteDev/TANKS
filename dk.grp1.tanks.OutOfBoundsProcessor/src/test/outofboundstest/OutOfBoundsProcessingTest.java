package outofboundstest;

import dk.grp1.tanks.OutOfBoundsProcessor.internal.OutOfBoundsProcessingSystem;
import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameMap;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.services.IPostEntityProcessingService;
import org.junit.Assert;

public class OutOfBoundsProcessingTest {

    @org.junit.Test
    public void outOfBoundsPostProcessingTestEntityOutOfBounds() {
        IPostEntityProcessingService iPostEntityProcessingService = new OutOfBoundsProcessingSystem();
        World world = new World();
        GameData gameData = new GameData();

        //Create Entity out of bounds
        Entity entity = new TestEntity();
        entity.add(new PositionPart(-10,50,0f));
        world.addEntity(entity);
        Assert.assertEquals(1,world.getEntities().size());
        //Expect entity to disappear from world after process
        iPostEntityProcessingService.postProcess(world,gameData);
        Assert.assertTrue(world.getEntities().isEmpty());
    }

    @org.junit.Test
    public void outOfBoundsPostProcessingTestEntityNotOutOfBounds() {
        IPostEntityProcessingService iPostEntityProcessingService = new OutOfBoundsProcessingSystem();
        World world = new World();
        GameData gameData = new GameData();

        //Create Entity out of bounds
        Entity entity = new TestEntity();
        entity.add(new PositionPart(10,50,0f));
        world.addEntity(entity);
        Assert.assertEquals(1,world.getEntities().size());
        //Expect entity to disappear from world after process
        iPostEntityProcessingService.postProcess(world,gameData);
        Assert.assertFalse(world.getEntities().isEmpty());
    }
    @org.junit.Test
    public void outOfBoundsPostProcessingTestNoEntity() {
        IPostEntityProcessingService iPostEntityProcessingService = new OutOfBoundsProcessingSystem();
        World world = new World();
        GameData gameData = new GameData();
        iPostEntityProcessingService.postProcess(world,gameData);
        Assert.assertTrue(world.getEntities().isEmpty());
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void outOfBoundsPostProcessingTestNullInputWorld() {
        IPostEntityProcessingService iPostEntityProcessingService = new OutOfBoundsProcessingSystem();
        World world = null;
        GameData gameData = new GameData();
        iPostEntityProcessingService.postProcess(world,gameData);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void outOfBoundsPostProcessingTestNullInputGameData() {
        IPostEntityProcessingService iPostEntityProcessingService = new OutOfBoundsProcessingSystem();
        World world = new World();
        GameData gameData = null;
        iPostEntityProcessingService.postProcess(world,gameData);
    }

    private class TestEntity extends Entity {
    }
}
