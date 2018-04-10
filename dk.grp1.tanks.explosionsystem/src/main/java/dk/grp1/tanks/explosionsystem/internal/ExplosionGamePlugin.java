package dk.grp1.tanks.explosionsystem.internal;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.eventManager.IEventCallback;
import dk.grp1.tanks.common.eventManager.events.ExplosionEvent;
import dk.grp1.tanks.common.services.IGamePluginService;

public class ExplosionGamePlugin implements IGamePluginService {

    private IEventCallback eventCallback;
    @Override
    public void start(World world, GameData gameData) {
        eventCallback = new ExplosionPostProcessingSystem(gameData,world);
        gameData.getEventManager().register(ExplosionEvent.class, eventCallback);
    }

    @Override
    public void stop(World world, GameData gameData) {
        gameData.getEventManager().unRegister(ExplosionEvent.class, eventCallback);
    }
}
