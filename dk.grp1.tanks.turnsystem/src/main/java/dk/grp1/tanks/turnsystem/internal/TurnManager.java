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
import dk.grp1.tanks.common.services.IRoundService;

import java.util.*;

public class TurnManager implements IRoundService, IPostEntityProcessingService, IEventCallback {

    private float roundDuration = 30;
    private float timeRemaining = 30;
    private ArrayList<Entity> entities;
    private Entity currentEntity;
    private Boolean wantToEndTurn = false;

    public TurnManager() {
        this.entities = new ArrayList<>();
    }

    public void start(){
        entities = new ArrayList<>();
        wantToEndTurn = false;
        currentEntity = null;
    }
    public void stop(){
        for (Entity entity : entities) {
            TurnPart turnPart = entity.getPart(TurnPart.class);
            if (turnPart != null) {
                turnPart.setMyTurn(true);
            }
        }
    }

    @Override
    public void processEvent(Event event) {
        if (event instanceof EndTurnEvent) {
            EndTurnEvent endTurnEvent = (EndTurnEvent) event;
            Entity source = endTurnEvent.getSource();

            if (source != null) {

                wantToEndTurn = true;
            }
        }
    }

    private void selectNextEntity(Entity source) {

        if (!entities.contains(source)) {
            return;
        }
        timeRemaining = roundDuration;
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
        

        if (wantToEndTurn){
            if (anythingMoves(world)){
                TurnPart turnPart = currentEntity.getPart(TurnPart.class);
                if (turnPart != null){
                    turnPart.setMyTurn(false);
                }
                return;
            }
            selectNextEntity(currentEntity);
            wantToEndTurn = false;
        }
		unRegisterMissingEntities(world);
        unRegisterDeadEntities();


        timeRemaining -= gameData.getDelta();

        if (timeRemaining <= 0) {
            gameData.getEventManager().addEvent(new EndTurnEvent(currentEntity));
        }

    }

    private void unRegisterMissingEntities(World world) {
        List<Entity> entitiesToRemove = new ArrayList<>();
        for (Entity entity : entities) {
            if(!world.getEntities().contains(entity)){
                entitiesToRemove.add(entity);
            }
        }

        for (Entity entity : entitiesToRemove) {
            unRegister(entity);
        }
    }

    private void unRegisterDeadEntities() {
        List<Entity> entitiesToRemove = new ArrayList<>();
        for (Entity entity : entities) {
            LifePart lifePart = entity.getPart(LifePart.class);
            TurnPart turnPart = entity.getPart(TurnPart.class);
            if (lifePart != null && turnPart != null) {
                if (lifePart.getCurrentHP() <= 0) {
                    entitiesToRemove.add(entity);
                }
            }
        }

        for (Entity entity : entitiesToRemove) {
            unRegister(entity);
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


    private void unRegister(Entity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity is null");
        }
        if (entities.contains(entity)) {
            TurnPart turnPart = entity.getPart(TurnPart.class);
            if (turnPart != null) {
                if (turnPart.isMyTurn()) {
                    selectNextEntity(entity);
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
    public boolean isRoundOver(World world)
    {

        return (entities.size() <= 1);
    }

    @Override
    public Entity getRoundWinner(World world) {
        if (!isRoundOver(world)) {
            return null;
        }
        if (!entities.isEmpty()){
            Entity winner = entities.get(0);
            return winner;
        }
        return null;
    }

    @Override
    public float getTimeRemaining() {
        return this.timeRemaining;
    }
}
