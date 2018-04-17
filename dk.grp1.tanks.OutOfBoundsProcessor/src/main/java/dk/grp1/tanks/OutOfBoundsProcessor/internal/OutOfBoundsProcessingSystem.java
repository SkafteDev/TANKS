package dk.grp1.tanks.OutOfBoundsProcessor.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.data.parts.TurnPart;
import dk.grp1.tanks.common.eventManager.events.EndTurnEvent;
import dk.grp1.tanks.common.services.IPostEntityProcessingService;

import java.util.HashSet;

public class OutOfBoundsProcessingSystem implements IPostEntityProcessingService {

    private HashSet<Entity> entitiesToRemove = new HashSet<>();

    @Override
    public void postProcess(World world, GameData gameData) {
        if(world == null || gameData == null){
            throw new IllegalArgumentException("World or gamedata is null");
        }
        //Mark the entities to be removed
        for (Entity entity : world.getEntities()) {
            PositionPart positionPart = entity.getPart(PositionPart.class);

            if (positionPart.getY() > gameData.getGameHeight() * 2
                    || positionPart.getX() < 0
                    || positionPart.getX() > gameData.getGameWidth()) {
                entitiesToRemove.add(entity);
            }
        }

        //Remove the marked entities
        for (Entity entity : entitiesToRemove) {
            TurnPart turnPart = entity.getPart(TurnPart.class);
            if (turnPart != null) {
                if (turnPart.isMyTurn()) {
                    gameData.getEventManager().addEvent(new EndTurnEvent(entity));
                }
            }
            world.removeEntity(entity.getID());
        }

        //Clear the list of removed entities
        entitiesToRemove.clear();

    }
}
