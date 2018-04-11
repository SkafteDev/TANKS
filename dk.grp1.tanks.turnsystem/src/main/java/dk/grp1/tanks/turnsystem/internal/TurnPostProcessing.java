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
import sun.awt.image.ImageWatched;

import java.util.*;

public class TurnPostProcessing implements IPostEntityProcessingService, IEventCallback {

    private Boolean shouldEndTurn = false;
    private Entity entityWantsToEndTurn;
    private int lastTurn = -1;

    public TurnPostProcessing() {

    }

    @Override
    public void processEvent(Event event) {
        if (event instanceof EndTurnEvent) {
            shouldEndTurn = true;
            entityWantsToEndTurn = event.getSource();
        }
    }

    @Override
    public void postProcess(World world, GameData gameData) {
        if (!shouldEndTurn) {
            return;
        }

        List<TurnPart> turnParts = new ArrayList<>();

        for (Entity entity : world.getEntities()) {
            TurnPart turnPart = entity.getPart(TurnPart.class);
            if (turnPart != null) {
                turnParts.add(turnPart);
            }
        }
        Collections.sort(turnParts, new Comparator<TurnPart>() {
            @Override
            public int compare(TurnPart turnPart, TurnPart t1) {
                return turnPart.getMyTurnNumber() - t1.getMyTurnNumber();
            }
        });

        //If only one turnpart exists, return out as it is not possible to change the turn.
        if (turnParts.size() == 1) {
            return;
        }

        //If anything in the world moves, wait for it to finish.
        if (anythingMoves(world)) {
            setLastTurn(TurnPart.getCurrentTurnNumber());
            TurnPart.setCurrentTurnNumber(-1);
            return;
        }


        int index = -1;

        for (TurnPart turnPart : turnParts) {
            if (turnPart.getMyTurnNumber() == getLastTurn()) {
                index = turnParts.indexOf(turnPart);
            }
        }

        int nextIndex = ((index + 1) % turnParts.size());

        //Change the turn number to the next indexes turn number
        TurnPart.setCurrentTurnNumber(turnParts.get(nextIndex).getMyTurnNumber());
        shouldEndTurn = false;



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

    private int getLastTurn() {
        return lastTurn;
    }

    private void setLastTurn(int lastTurn) {
        if (lastTurn > -1) {
            this.lastTurn = lastTurn;
        }
    }


}
