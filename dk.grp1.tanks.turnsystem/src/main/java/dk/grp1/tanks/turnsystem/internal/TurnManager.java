package dk.grp1.tanks.turnsystem.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.LifePart;
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
            System.out.println("End Turn Event");
            EndTurnEvent endTurnEvent = (EndTurnEvent) event;
            Entity source = endTurnEvent.getSource();

            if (source != null) {
                if (entities.contains(source)) {
                    System.out.println("Entity exists");
                    timeRemaining = 30;
                    int nextIndex = 0;
                    int index = entities.indexOf(source);
                    System.out.println("List size :" + entities.size());
                    if (index != entities.size() - 1) {
                        nextIndex = index + 1;
                    }
                    System.out.println("Next index: " + nextIndex);
                    selectNextEntity(nextIndex);
                }
            }
        }
    }

    private void selectNextEntity(int nextIndex) {
        for (Entity entity : entities) {
            TurnPart turnPart = entity.getPart(TurnPart.class);
            if (turnPart != null) {
                if (entities.indexOf(entity) == nextIndex) {
                    turnPart.setMyTurn(true);
                    currentEntity = entity;
                    System.out.println(turnPart.isMyTurn());
                } else {
                    turnPart.setMyTurn(false);
                }
            }
        }
    }

    @Override
    public void postProcess(World world, GameData gameData) {
        if (world == null || gameData == null) {
            throw new IllegalArgumentException("GameData or World is null");
        }
        for (Entity entity : world.getEntities()) {

            register(entity);
        }

        for (Entity entity : entities) {
            LifePart lifePart = entity.getPart(LifePart.class);
            if (lifePart != null){
                if (lifePart.getCurrentHP() <= 0){
                    // slet fra entities
                }
            }
        }

        timeRemaining -= gameData.getDelta();
        if (timeRemaining < 0) {
            this.gameData.getEventManager().addEvent(new EndTurnEvent(currentEntity));
        }

    }


    @Override
    public void register(Entity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity is null");
        }
        TurnPart turnPart = entity.getPart(TurnPart.class);

        if (turnPart != null) {
            if (entities.size() == 0) {
                turnPart.setMyTurn(true);
                currentEntity = entity;
            }
            if (!entities.contains(entity)) {
                entities.add(entity);
            }
        }
    }

    @Override
    public void unRegister(Entity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity is null");
        }
        if (entities.contains(entity)) {
            TurnPart turnPart = entity.getPart(TurnPart.class);
            if (turnPart != null) {
                if (turnPart.isMyTurn()) {
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
