package dk.grp1.tanks.explosionsystem.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.eventManager.IEventCallback;
import dk.grp1.tanks.common.eventManager.events.Event;
import dk.grp1.tanks.common.eventManager.events.ExplosionEvent;
import dk.grp1.tanks.common.services.IPostEntityProcessingService;

public class ExplosionEventCallbackImpl implements IEventCallback {

    private GameData gameData;
    private World world;

    public ExplosionEventCallbackImpl(GameData gameData, World world) {
        if(gameData == null || world == null){
            throw new IllegalArgumentException("GameData or world cannot be null");
        }
        this.gameData = gameData;
        this.world = world;
    }

    @Override
    public void processEvent(Event event) {
        if(event instanceof ExplosionEvent && event.getSource().getPart(DamagePart.class) != null) {
            for (Entity ent : world.getEntities()) {

                if (isInExplosion(event, ent)) {
                    LifePart lp = ent.getPart(LifePart.class);
                    if (lp != null) {
                        lp.removeHP(((DamagePart) event.getSource().getPart(DamagePart.class)).getDamage());
                    }
                }
            }
        } else if (event == null){
            throw new IllegalArgumentException("Event cannot be null");
        }
    }

    private boolean isInExplosion(Event evnt, Entity ent) {
        ExplosionEvent exEvnt = (ExplosionEvent) evnt;
        CirclePart circlePart = ent.getPart(CirclePart.class);

        if(circlePart == null) {
            return false;
        }

        float distX = exEvnt.getPointOfCollision().getX() - circlePart.getCentreX();
        float distY = exEvnt.getPointOfCollision().getY() - circlePart.getCentreY();
        float distance = (float)(Math.sqrt(distX*distX + distY*distY));
        return distance < (exEvnt.getExplosionRadius()+circlePart.getRadius());
        }


}
