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

/**
 * Responsible for controlling which entity is allowed to make a move,
 * and change between the entities that are allowed to play.
 */
public class TurnManager implements IRoundService, IPostEntityProcessingService, IEventCallback {

    private float roundDuration = 30;
    private float timeRemaining = 30;
    private ArrayList<Entity> entities;
    private Entity currentEntity;
    private Boolean wantToEndTurn = false;

    public TurnManager() {
        this.entities = new ArrayList<>();
    }

    public void start() {
        entities = new ArrayList<>();
        wantToEndTurn = false;
        currentEntity = null;
    }

    /**
     * Results in: All registered entities are allowed to make a move.
     */
    public void stop() {
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

    /**
     * Give the turn to the next entity
     * @param source
     */
    private void selectNextEntity(Entity source) {

        if (!entities.contains(source)) {
            return;
        }

        timeRemaining = roundDuration;
        int nextIndex = 0;
        int index = entities.indexOf(source); // Get the index of the given entity.
        if (index != entities.size() - 1) {
            nextIndex = index + 1;  // Calculate the next index.
        }

        for (Entity entity : entities) {
            TurnPart turnPart = entity.getPart(TurnPart.class);
            if (turnPart != null) {
                if (entities.indexOf(entity) == nextIndex) {
                    turnPart.setMyTurn(true);           // Sets the turn to true for the next entity
                    currentEntity = entity;
                } else {                                // And all other entities' TurnPart is set to false.
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
        // Register all entities in the world
        for (Entity entity : world.getEntities()) {

            register(entity);
        }


        if (wantToEndTurn) {
            // The current Entity wants to end its turn.
            if (anythingMoves(world)) {
                // If anything is still moving - e.g. a bullet
                TurnPart turnPart = currentEntity.getPart(TurnPart.class);
                if (turnPart != null) {
                    // Set the current Entity's TurnPart to null. (No entities are allowed to make a move)
                    turnPart.setMyTurn(false);
                }
                return;
            }
            // If nothing moves, then select the next Entity.
            selectNextEntity(currentEntity);
            wantToEndTurn = false;

        }

        unRegisterDeadEntities();
        unRegisterMissingEntities(world);


        timeRemaining -= gameData.getDelta();

        if (timeRemaining <= 0) {
            gameData.getEventManager().addEvent(new EndTurnEvent(currentEntity));
        }

    }

    /**
     * Unregister entities that are missing, which means entities that are out of bounds, from the Turn Manager.
     * If the current entity is missing, then the system will select the next entity.
     * @param world
     */

    private void unRegisterMissingEntities(World world) {
        List<Entity> entitiesToRemove = new ArrayList<>();
        for (Entity entity : entities) {
            if (!world.getEntities().contains(entity)) {
                entitiesToRemove.add(entity);
            }
        }

        for (Entity entity : entitiesToRemove) {
            unRegister(entity);
        }
    }

    /**
     * Unregister entities that are dead from the Turn Manager.
     * If the current entity is dead, then the system will select the next entity.
     */
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

    /**
     * Register a given Entity to the Turn Manager.
     * Checks if the entity is already registered.
     * @param entity
     */
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

    /**
     * Unregister a given Entity from the Turn Manager
     * @param entity
     */
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

    /**
     * Checks if anything moves in world
     * @param world
     * @return True if there is movement
     */
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
    public boolean isRoundOver(World world) {

        return (entities.size() <= 1);
    }

    @Override
    public Entity getRoundWinner(World world) {
        if (!isRoundOver(world)) {
            return null;
        }
        if (!entities.isEmpty()) {
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
