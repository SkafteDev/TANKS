package dk.grp1.tanks.turnsystem.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.TurnPart;
import dk.grp1.tanks.common.eventManager.IEventCallback;
import dk.grp1.tanks.common.eventManager.events.EndTurnEvent;
import dk.grp1.tanks.common.eventManager.events.Event;
import dk.grp1.tanks.common.services.IPostEntityProcessingService;
import dk.grp1.tanks.common.services.ITurnManager;

import java.util.*;

public class TurnManager implements ITurnManager, IPostEntityProcessingService, IEventCallback {

    private float roundDuration = 30;
    private float timeRemaining = 30;
    private ArrayList<Entity> entities;
    private Entity currentEntity;
    private GameData gameData;

    public TurnManager() {
        this.entities = new ArrayList<>();
    }

    @Override
    public void processEvent(Event event) {
        if (event instanceof EndTurnEvent) {
            EndTurnEvent endTurnEvent = (EndTurnEvent) event;
            Entity source = endTurnEvent.getSource();

            if (source != null){
                if (entities.contains(source)){
                    timeRemaining = 30;
                    int nextIndex = 0;
                    int index = entities.indexOf(source);
                    if (index != entities.size() - 1){
                        nextIndex = index - 1;
                    }
                    selectNextEntity(nextIndex);
                }
            }
        }
    }

    private void selectNextEntity(int nextIndex) {
        for (Entity entity : entities) {
            TurnPart turnPart = entity.getPart(TurnPart.class);
            if (turnPart != null){
                if (entities.indexOf(entity) == nextIndex){
                    turnPart.setMyTurn(true);
                    currentEntity = entity;
                } else {
                    turnPart.setMyTurn(false);
                }
            }
        }
    }

    @Override
    public void postProcess(World world, GameData gameData) {
        if (gameData == null){
            throw new IllegalArgumentException("GameData is null");
        }
        timeRemaining -= gameData.getDelta();
        if (timeRemaining < 0){
            this.gameData.getEventManager().addEvent(new EndTurnEvent(currentEntity));
        }
        
    }


    @Override
    public void register(Entity entity) {
        if (entity == null){
            throw new IllegalArgumentException("Entity is null");
        }
        if (entities.size() == 0){
            TurnPart turnPart = entity.getPart(TurnPart.class);
            if (turnPart != null){
                turnPart.setMyTurn(true);
            }
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

    @Override
    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public float getTimeRemaining() {
        return this.timeRemaining;
    }
}
