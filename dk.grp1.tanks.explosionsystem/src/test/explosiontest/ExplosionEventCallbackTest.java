package explosiontest;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.DamagePart;
import dk.grp1.tanks.common.data.parts.LifePart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.eventManager.IEventCallback;
import dk.grp1.tanks.common.eventManager.events.Event;
import dk.grp1.tanks.common.eventManager.events.ExplosionEvent;
import dk.grp1.tanks.common.utils.Vector2D;
import dk.grp1.tanks.explosionsystem.internal.ExplosionEventCallbackImpl;
import junit.framework.Assert;

public class ExplosionEventCallbackTest {
    private class TestEntity extends Entity {
    }
    private class TestEntityEventSource extends Entity {
    }

    @org.junit.Test
    public void processEventTest() {
        GameData gameData = new GameData();
        World world = new World();
        //Add entity that are within explosion radius.
        Entity entity = new TestEntity();
        LifePart lifePart = new LifePart();
        float startHp = 100f;
        lifePart.setMaxHP(startHp);
        lifePart.setCurrentHP(startHp);
        entity.add(lifePart);
        entity.add(new PositionPart(50, 50, 0f));
        entity.add(new CirclePart(50, 50, 5));

        world.addEntity(entity);


        IEventCallback explosionEventCallback = new ExplosionEventCallbackImpl(gameData, world);

        //Create Source Entity
        float damage = 50;
        Entity entity2 = new TestEntityEventSource();
        entity2.add(new CirclePart(50, 50, 5));
        entity2.add(new DamagePart(5,damage));
        Event explosionEvent = new ExplosionEvent(entity2,new Vector2D(50,50), 5f);

        explosionEventCallback.processEvent(explosionEvent);

        Assert.assertEquals(startHp-damage,lifePart.getCurrentHP());
    }
    @org.junit.Test
    public void processEventTestNotInRange() {
        GameData gameData = new GameData();
        World world = new World();
        //Add entity that are within explosion radius.
        Entity entity = new TestEntity();
        LifePart lifePart = new LifePart();
        float startHp = 100f;
        lifePart.setMaxHP(startHp);
        lifePart.setCurrentHP(startHp);
        entity.add(lifePart);
        entity.add(new PositionPart(100, 100, 0f));
        entity.add(new CirclePart(100, 100, 5));

        world.addEntity(entity);


        IEventCallback explosionEventCallback = new ExplosionEventCallbackImpl(gameData, world);

        //Create Source Entity
        float damage = 50;
        Entity entity2 = new TestEntityEventSource();
        entity2.add(new CirclePart(50, 50, 5));
        entity2.add(new DamagePart(5,damage));
        Event explosionEvent = new ExplosionEvent(entity2,new Vector2D(50,50), 5f);

        explosionEventCallback.processEvent(explosionEvent);

        Assert.assertEquals(startHp,lifePart.getCurrentHP());
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void constructorTestNullInput() {
        GameData gameData = null;
        World world = null;
        IEventCallback explosionEventCallback = new ExplosionEventCallbackImpl(gameData, world);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void processEventTestNullInput() {
        GameData gameData = new GameData();
        World world =  new World();
        IEventCallback explosionEventCallback = new ExplosionEventCallbackImpl(gameData, world);
        Event explosionEvent = null;
        explosionEventCallback.processEvent(explosionEvent);
    }


}
