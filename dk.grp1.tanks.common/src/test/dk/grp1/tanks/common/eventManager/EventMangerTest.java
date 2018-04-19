package dk.grp1.tanks.common.eventManager;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.eventManager.events.EndTurnEvent;
import dk.grp1.tanks.common.eventManager.events.Event;
import dk.grp1.tanks.common.eventManager.events.ExplosionEvent;
import dk.grp1.tanks.common.eventManager.events.ShootingEvent;
import org.junit.Assert;
import org.junit.Test;

public class EventMangerTest {

    @Test
    public void testRegisterAndAddEvent() {
        GameData gd = new GameData();
        Entity entity = new Entity() {};
        TestEventCallBack testEventCallBack = new TestEventCallBack();
        gd.getEventManager().register(ShootingEvent.class, testEventCallBack);
        gd.getEventManager().addEvent(new ShootingEvent(entity, 100));
        Assert.assertEquals(true, testEventCallBack.getHasProcessed());
    }

    @Test
    public void testUnregister() {
        GameData gd = new GameData();
        Entity entity = new Entity() {};
        TestEventCallBack testEventCallBack = new TestEventCallBack();
        gd.getEventManager().register(ShootingEvent.class, testEventCallBack);
        gd.getEventManager().unRegister(ShootingEvent.class, testEventCallBack);
        gd.getEventManager().addEvent(new ShootingEvent(entity, 100));
        Assert.assertEquals(false, testEventCallBack.getHasProcessed());
    }


    private class TestEventCallBack implements IEventCallback {
        private boolean hasProcessed = false;

        @Override
        public void processEvent(Event event) {
            hasProcessed = true;
        }

        boolean getHasProcessed() {
            return this.hasProcessed;
        }
    }

}


