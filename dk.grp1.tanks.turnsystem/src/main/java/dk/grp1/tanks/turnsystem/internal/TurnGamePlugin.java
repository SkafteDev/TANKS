package dk.grp1.tanks.turnsystem.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.TurnPart;
import dk.grp1.tanks.common.eventManager.IEventCallback;
import dk.grp1.tanks.common.eventManager.events.EndTurnEvent;
import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.ITurnManager;

public class TurnGamePlugin implements IGamePluginService {

    private IEventCallback callback;
    public TurnGamePlugin(IEventCallback callback) {
        this.callback = callback;
    }

    @Override
    public void start(World world, GameData gameData) {
        gameData.setTurnManager((ITurnManager) callback);
        gameData.getEventManager().register(EndTurnEvent.class, callback);
    }

    @Override
    public void stop(World world, GameData gameData) {

        gameData.getEventManager().unRegister(EndTurnEvent.class, callback);

    }
}
