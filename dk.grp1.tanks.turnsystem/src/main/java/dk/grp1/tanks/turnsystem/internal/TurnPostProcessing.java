package dk.grp1.tanks.turnsystem.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.TurnPart;
import dk.grp1.tanks.common.events.EndTurnEvent;
import dk.grp1.tanks.common.events.Event;
import dk.grp1.tanks.common.services.IPostEntityProcessingService;

import java.util.*;

public class TurnPostProcessing implements IPostEntityProcessingService {

    private TreeSet<TurnPart> turnParts;

    public TurnPostProcessing(){
       turnParts= new TreeSet<>(new Comparator<TurnPart>() {
            @Override
            public int compare(TurnPart o1, TurnPart o2) {
                return o1.getMyTurnNumber() - o2.getMyTurnNumber();
            }
        });


    }

    @Override
    public void postProcess(World world, GameData gameData) {



        List<Event> events = gameData.getEvents(EndTurnEvent.class);
        if(events.size()==0){
            return;
        }
        if(events.size() > 1){
            throw new Error("You cant end more than one turn each frame.");
        }
        EndTurnEvent event = (EndTurnEvent) events.get(0);
        gameData.removeEvent(event);


        for (Entity entity : world.getEntities()
             ) {
            TurnPart turnPart = entity.getPart(TurnPart.class);
            if(turnPart != null){
                turnParts.add(turnPart);
            }

        }

        TurnPart eventTurn = event.getSource().getPart(TurnPart.class);
        if(turnParts.last().equals(eventTurn)){
            TurnPart.setCurrentTurnNumber(turnParts.first().getMyTurnNumber());
            return;
        }

        int nextturn = turnParts.higher(eventTurn).getMyTurnNumber();

        TurnPart.setCurrentTurnNumber(nextturn);


    }
}
