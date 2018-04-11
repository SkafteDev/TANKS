package dk.grp1.tanks.pushsystem.internal;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.eventManager.events.PushEvent;
import dk.grp1.tanks.common.services.IGamePluginService;

public class PushSystemGamePlugin implements IGamePluginService {
    private PushSystem pushHandler;

    @Override
    public void start(World world, GameData gameData) {
        pushHandler = new PushSystem(world, gameData);
        gameData.getEventManager().register(PushEvent.class, pushHandler);
    }

    @Override
    public void stop(World world, GameData gameData) {
        gameData.getEventManager().unRegister(PushEvent.class, pushHandler);
    }
}
