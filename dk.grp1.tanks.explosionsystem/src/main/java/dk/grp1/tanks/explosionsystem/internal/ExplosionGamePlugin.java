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
        if(world == null || gameData == null){
            throw new IllegalArgumentException("World or GameData is null");
        }
        eventCallback = new ExplosionEventCallbackImpl(gameData,world);
        gameData.getEventManager().register(ExplosionEvent.class, eventCallback);
    }

    @Override
    public void stop(World world, GameData gameData) {
        if(eventCallback == null){
            throw new IllegalStateException("Cannot Stop, before the start method has been called");
        }
        if(world == null || gameData == null){
            throw new IllegalArgumentException("World or GameData is null");
        }
        gameData.getEventManager().unRegister(ExplosionEvent.class, eventCallback);
    }
}
