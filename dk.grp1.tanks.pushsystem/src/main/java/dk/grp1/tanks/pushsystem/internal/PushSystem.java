package dk.grp1.tanks.pushsystem.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.MovementPart;
import dk.grp1.tanks.common.data.parts.PhysicsPart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.eventManager.IEventCallback;
import dk.grp1.tanks.common.eventManager.events.Event;
import dk.grp1.tanks.common.eventManager.events.ExplosionEvent;
import dk.grp1.tanks.common.eventManager.events.PushEvent;
import dk.grp1.tanks.common.utils.Vector2D;

public class PushSystem implements IEventCallback {
    private final World world;
    private final GameData gameData;

    public PushSystem(World world, GameData gameData) {

        this.world = world;
        this.gameData = gameData;
    }

    @Override
    public void processEvent(Event event) {
        if(event instanceof PushEvent){
            PushEvent pushEvent = (PushEvent) event;

            for (Entity e : world.getEntities()) {
                if(isInExplosion(pushEvent, e)){
                    if(!(e.equals(pushEvent.getSource()))) {
                        Vector2D dir = getPushDirection(e, pushEvent);
                        push(e, dir, pushEvent.getForce());
                    }
                }
            }
        }
    }

    private void push(Entity ent, Vector2D dir, float force){
        MovementPart movementPart = ent.getPart(MovementPart.class);
        PhysicsPart physicsPart = ent.getPart(PhysicsPart.class);
        float mass = physicsPart.getMass();
        float acceleration = force/mass;
        dir.multiplyWithConstant(acceleration);
        movementPart.setVelocity(dir);
    }

    private boolean isInExplosion(Event evnt, Entity ent) {
        PushEvent exEvnt = (PushEvent) evnt;
        PositionPart positionPart = ent.getPart(PositionPart.class);
        CirclePart circlePart = ent.getPart(CirclePart.class);
        if (circlePart != null && positionPart != null) {
            float distX = exEvnt.getPointOfExplosion().getX() - positionPart.getX();
            float distY = exEvnt.getPointOfExplosion().getY() - positionPart.getY();
            float distance = (float) (Math.sqrt(distX * distX + distY * distY));
            return distance < (exEvnt.getPushRadius() + circlePart.getRadius());
        }
    }

    private Vector2D getPushDirection(Entity ent, PushEvent evnt){
        PositionPart positionPart = ent.getPart(PositionPart.class);

        Vector2D entityPos = new Vector2D(positionPart.getX(), positionPart.getY());
        entityPos.subtract(evnt.getPointOfExplosion());
        return entityPos.unitVector();

    }
}
