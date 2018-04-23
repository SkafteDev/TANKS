package dk.grp1.tanks.turnsystem.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.LifePart;
import dk.grp1.tanks.common.data.parts.MovementPart;
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
    private Boolean wantToEndTurn = false;

    public TurnManager() {
        this.entities = new ArrayList<>();
    }

    @Override
    public void processEvent(Event event) {
        if (event instanceof EndTurnEvent) {
            EndTurnEvent endTurnEvent = (EndTurnEvent) event;
            Entity source = endTurnEvent.getSource();

            if (source != null) {
                System.out.println("Want to End turn");
                wantToEndTurn = true;
            }
        }
    }

    private void selectNextEntity(Entity source) {

        if (!entities.contains(source)) {
            System.out.println("Does not contain source");
            return;
        }
        timeRemaining = 30;
        int nextIndex = 0;
        int index = entities.indexOf(source);
        if (index != entities.size() - 1) {
            nextIndex = index + 1;
        }

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
            TurnPart turnPart = entity.getPart(TurnPart.class);
            if (turnPart != null){
                System.out.println(entity.toString() + " : " + turnPart.isMyTurn());
            }
        }
        System.out.println("\n");

        if (wantToEndTurn){
            if (anythingMoves(world)){
                TurnPart turnPart = currentEntity.getPart(TurnPart.class);
                if (turnPart != null){
                    turnPart.setMyTurn(false);
                }
                //unRegisterEntities(gameData);
                return;
            }
            selectNextEntity(currentEntity);
            wantToEndTurn = false;
        }

        unRegisterEntities(gameData);

        timeRemaining -= gameData.getDelta();

        if (timeRemaining < 0) {
            gameData.getEventManager().addEvent(new EndTurnEvent(currentEntity));
        }

    }

    private void unRegisterEntities(GameData gameData) {
        List<Entity> entitiesToRemove = new ArrayList<>();
        for (Entity entity : entities) {
            LifePart lifePart = entity.getPart(LifePart.class);
            TurnPart turnPart = entity.getPart(TurnPart.class);
            if (lifePart != null && turnPart != null) {
                if (lifePart.getCurrentHP() <= 0 && !turnPart.isMyTurn()) {
                    entitiesToRemove.add(entity);
                }
            }
        }

        for (Entity entity : entitiesToRemove) {
            unRegister(entity, gameData);
        }
    }


    private void register(Entity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity is null");
        }
        TurnPart turnPart = entity.getPart(TurnPart.class);

        if (turnPart != null) {
            if (entities.isEmpty()) {
                turnPart.setMyTurn(true);
                currentEntity = entity;
            }
            if (!entities.contains(entity)) {
                entities.add(entity);
            }
        }
    }


    private void unRegister(Entity entity, GameData gameData) {
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

    private boolean anythingMoves(World world) {
        for (Entity e : world.getEntities()
                ) {
            MovementPart movPart = e.getPart(MovementPart.class);
            if (movPart.getCurrentSpeed() > 0f) {
                return true;
            }
        }
        return false;
    }

    @Override
    public float getTimeRemaining() {
        return this.timeRemaining;
    }
}
