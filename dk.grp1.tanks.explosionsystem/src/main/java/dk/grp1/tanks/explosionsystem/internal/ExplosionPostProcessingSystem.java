package dk.grp1.tanks.explosionsystem.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.events.Event;
import dk.grp1.tanks.common.events.ExplosionAnimationEvent;
import dk.grp1.tanks.common.events.ExplosionEvent;
import dk.grp1.tanks.common.events.ShootingEvent;
import dk.grp1.tanks.common.services.IPostEntityProcessingService;
import dk.grp1.tanks.common.utils.Vector2D;

public class ExplosionPostProcessingSystem implements IPostEntityProcessingService {
    @Override
    public void postProcess(World world, GameData gameData) {
        for (Event evnt : gameData.getEvents(ExplosionEvent.class)) {

            for (Entity ent: world.getEntities()) {

                if(isInExplosion(evnt, ent)){
                    LifePart lp = ent.getPart(LifePart.class);
                    if(lp != null) {
                        System.out.println("Life part not null");
                        lp.removeHP(((DamagePart) evnt.getSource().getPart(DamagePart.class)).getDamage());

                        //System.out.println(lp.getCurrentHP());
                    }
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
