package dk.grp1.tanks.turnsystem.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.MovementPart;
import dk.grp1.tanks.common.data.parts.TurnPart;
import dk.grp1.tanks.common.eventManager.IEventCallback;
import dk.grp1.tanks.common.eventManager.events.EndTurnEvent;
import dk.grp1.tanks.common.eventManager.events.Event;
import dk.grp1.tanks.common.services.IPostEntityProcessingService;
import dk.grp1.tanks.common.services.ITurnManager;
import sun.awt.image.ImageWatched;

import java.util.*;

public class TurnManager implements ITurnManager, IPostEntityProcessingService, IEventCallback {

    private float roundDuration = 30;
    private ArrayList<Entity> entities;
    private GameData gameData;

    public TurnManager() {
        this.entities = new ArrayList<>();
    }

    @Override
    public void processEvent(Event event) {
        if (event instanceof EndTurnEvent) {

        }
    }

    @Override
    public void postProcess(World world, GameData gameData) {

    }


    @Override
    public void register(Entity entity) {
        if (entity == null){
            throw new IllegalArgumentException("Entity is null");
        }
        entities.add(entity);
    }

    @Override
    public void unRegister(Entity entity) {
        if (entity == null){
            throw new IllegalArgumentException("Entity is null");
        }
        if (entities.contains(entity)){
            TurnPart turnPart = entity.getPart(TurnPart.class);
            if (turnPart != null){
                if (turnPart.isMyTurn()){
                    gameData.getEventManager().addEvent(new EndTurnEvent(entity));
                }
                entities.remove(entity);
            }
        }
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }
}
