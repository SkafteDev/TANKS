package dk.grp1.tanks.explosionsystem.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.events.Event;
import dk.grp1.tanks.common.events.ExplosionEvent;
import dk.grp1.tanks.common.events.ShootingEvent;
import dk.grp1.tanks.common.services.IPostEntityProcessingService;

public class ExplosionPostProcessingSystem implements IPostEntityProcessingService {
    @Override
    public void postProcess(World world, GameData gameData) {
        for (Event evnt : gameData.getEvents(ExplosionEvent.class)) {

            for (Entity ent: world.getEntities()) {
                if(isInExplosion(evnt, ent)){
                    System.out.println("COLLSION");
                }

            }

            gameData.removeEvent(evnt);

        }
    }

    private boolean isInExplosion(Event evnt, Entity ent) {
        ExplosionEvent exEvnt = (ExplosionEvent) evnt;
        PositionPart positionPart = ent.getPart(PositionPart.class);
        CirclePart circlePart = ent.getPart(CirclePart.class);

        float distX = exEvnt.getPointOfCollision().getX() - positionPart.getX();
        float distY = exEvnt.getPointOfCollision().getY() - positionPart.getY();
        float distance = (float)(Math.sqrt(distX*distX + distY*distY));
        return distance < (exEvnt.getExplosionRadius()+circlePart.getRadius());
        }

}
