package collisiontest;

import dk.grp1.tanks.EntityCollision.internal.EntityCollisionPostProcessingSystem;
import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.CollisionPart;
import dk.grp1.tanks.common.services.IPostEntityProcessingService;
import org.junit.Assert;

public class EntityCollisionPostProcessingTest {
    private class TestEntity1 extends Entity{}
    private class TestEntity2 extends Entity{}

    @org.junit.Test
    public void processCollisionIntersecting(){
        IPostEntityProcessingService entityCollisionProcessing = new EntityCollisionPostProcessingSystem();
        World world = new World();
        GameData gameData = null;
        float posX1 = 50;
        float posY1 = 50;
        float radius1 = 5;
        Entity entity1 = new TestEntity1();
        entity1.add(new CollisionPart(true,0f));
        entity1.add(new CirclePart(posX1,posY1,radius1));
        float posX2 = 50;
        float posY2 = 50;
        float radius2 = 5;
        Entity entity2 = new TestEntity2();
        entity2.add(new CollisionPart(true,0f));
        entity2.add(new CirclePart(posX2,posY2,radius2));

        world.addEntity(entity1);
        world.addEntity(entity2);

        entityCollisionProcessing.postProcess(world,gameData);

        CollisionPart collisionPart1 = entity1.getPart(CollisionPart.class);
        CollisionPart collisionPart2 = entity2.getPart(CollisionPart.class);

        Assert.assertTrue(collisionPart1.isHitEntity());
        Assert.assertTrue(collisionPart2.isHitEntity());
    }

    @org.junit.Test
    public void processCollisionIntersectingSameClass(){
        IPostEntityProcessingService entityCollisionProcessing = new EntityCollisionPostProcessingSystem();
        World world = new World();
        GameData gameData = null;
        float posX1 = 50;
        float posY1 = 50;
        float radius1 = 5;
        Entity entity1 = new TestEntity1();
        entity1.add(new CollisionPart(true,0f));
        entity1.add(new CirclePart(posX1,posY1,radius1));
        float posX2 = 50;
        float posY2 = 50;
        float radius2 = 5;
        Entity entity2 = new TestEntity1();
        entity2.add(new CollisionPart(true,0f));
        entity2.add(new CirclePart(posX2,posY2,radius2));

        world.addEntity(entity1);
        world.addEntity(entity2);

        entityCollisionProcessing.postProcess(world,gameData);

        CollisionPart collisionPart1 = entity1.getPart(CollisionPart.class);
        CollisionPart collisionPart2 = entity2.getPart(CollisionPart.class);

        Assert.assertFalse(collisionPart1.isHitEntity());
        Assert.assertFalse(collisionPart2.isHitEntity());
    }



    @org.junit.Test
    public void processCollisionNonIntersecting(){
        IPostEntityProcessingService entityCollisionProcessing = new EntityCollisionPostProcessingSystem();
        World world = new World();
        GameData gameData = null;
        float posX1 = 50;
        float posY1 = 50;
        float radius1 = 5;
        Entity entity1 = new TestEntity1();
        entity1.add(new CollisionPart(true,0f));
        entity1.add(new CirclePart(posX1,posY1,radius1));
        float posX2 = 20;
        float posY2 = 20;
        float radius2 = 5;
        Entity entity2 = new TestEntity2();
        entity2.add(new CollisionPart(true,0f));
        entity2.add(new CirclePart(posX2,posY2,radius2));

        world.addEntity(entity1);
        world.addEntity(entity2);

        entityCollisionProcessing.postProcess(world,gameData);

        CollisionPart collisionPart1 = entity1.getPart(CollisionPart.class);
        CollisionPart collisionPart2 = entity2.getPart(CollisionPart.class);

        Assert.assertFalse(collisionPart1.isHitEntity());
        Assert.assertFalse(collisionPart2.isHitEntity());
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void processCollisionNullInput(){
        IPostEntityProcessingService entityCollisionProcessing = new EntityCollisionPostProcessingSystem();
        World world = null;
        GameData gameData = null;
        entityCollisionProcessing.postProcess(world,gameData);
    }
}
