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

import java.util.*;

public class TurnPostProcessing implements IPostEntityProcessingService, IEventCallback {

    private TreeSet<TurnPart> turnParts;
    private Boolean shouldEndTurn = false;
    private Entity entityWantsToEndTurn;

    public TurnPostProcessing() {

    }

    @Override
    public void processEvent(Event event) {
        if (event instanceof EndTurnEvent){
            shouldEndTurn = true;
            entityWantsToEndTurn = event.getSource();
        }
    }

    @Override
    public void postProcess(World world, GameData gameData) {

        turnParts = new TreeSet<>(new Comparator<TurnPart>() {
            @Override
            public int compare(TurnPart o1, TurnPart o2) {
                return o1.getMyTurnNumber() - o2.getMyTurnNumber();
            }
        });

//        List<Event> events = gameData.getEvents(EndTurnEvent.class);
        if (!shouldEndTurn) {
            return;
        }
//        if (events.size() > 1) {
//            throw new Error("You cant end more than one turn each frame.");
//        }
//        EndTurnEvent event = (EndTurnEvent) events.get(0);


        for (Entity entity : world.getEntities()) {
            TurnPart turnPart = entity.getPart(TurnPart.class);
            if (turnPart != null) {
                turnParts.add(turnPart);
            }
        }


        if (anythingMoves(world)) {
            TurnPart.setCurrentTurnNumber(-1);
            return;
        }


        // Next turn
        TurnPart eventTurn = entityWantsToEndTurn.getPart(TurnPart.class);
        if (turnParts.last().equals(eventTurn)) {
            TurnPart.setCurrentTurnNumber(turnParts.first().getMyTurnNumber());
            shouldEndTurn = false;
//            gameData.removeEvent(event);
            return;
        }

        int nextturn = turnParts.higher(eventTurn).getMyTurnNumber();

        TurnPart.setCurrentTurnNumber(nextturn);
        shouldEndTurn = false;
        return;

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



}
