package dk.grp1.tanks.explosionsystem.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.eventManager.IEventCallback;
import dk.grp1.tanks.common.eventManager.events.Event;
import dk.grp1.tanks.common.eventManager.events.ExplosionEvent;
import dk.grp1.tanks.common.services.IPostEntityProcessingService;

public class ExplosionPostProcessingSystem implements IPostEntityProcessingService, IEventCallback {

    private GameData gameData;
    private World world;

    public ExplosionPostProcessingSystem(GameData gameData, World world) {
        this.gameData = gameData;
        this.world = world;
    }

    @Override
    public void processEvent(Event event) {
        for (Entity ent: world.getEntities()) {

            if(isInExplosion(event, ent)){
                LifePart lp = ent.getPart(LifePart.class);
                if(lp != null) {
                    lp.removeHP(((DamagePart) event.getSource().getPart(DamagePart.class)).getDamage());

                }
            }



        }
    }

    @Override
    public void postProcess(World world, GameData gameData) {

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
