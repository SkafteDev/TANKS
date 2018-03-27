package dk.grp1.tanks.weapon.BouncyBall.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.DamagePart;
import dk.grp1.tanks.common.data.parts.MovementPart;
import dk.grp1.tanks.common.data.parts.PhysicsPart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.events.Event;
import dk.grp1.tanks.common.events.ExplosionEvent;
import dk.grp1.tanks.common.events.MapDestructionEvent;
import dk.grp1.tanks.common.utils.Vector2D;

public class BouncyBallMovementPart extends MovementPart {



    public BouncyBallMovementPart(Vector2D velocity, float maxSpeed) {
        super(velocity, maxSpeed);
    }


    @Override
    public void processPart(Entity entity, GameData gameData, World world) {
        // Get time since last process
        float dt = gameData.getDelta();

        // get pos
        PositionPart positionPart = entity.getPart(PositionPart.class);
        if (positionPart == null) {
            return; // IF no pos, we cant move
        }

        PhysicsPart physicsPart = entity.getPart(PhysicsPart.class);
        // update velocity with grav
        if (physicsPart != null) {
            addVelocity(physicsPart.getGravityVector());
        }

        BouncyBallCollisionPart bouncyBallCollisionPart = entity.getPart(BouncyBallCollisionPart.class);

        if (bouncyBallCollisionPart != null && bouncyBallCollisionPart.isHitGameMap()) {
            bouncyBallCollisionPart.updateBouncingVector(this.getVelocity());
            this.setVelocity(bouncyBallCollisionPart.getBouncingVector());
            bouncyBallCollisionPart.setHitGameMap(false);

            DamagePart damagePart = entity.getPart(DamagePart.class);

            if (damagePart != null) {
                Event explosionEvent = new ExplosionEvent(entity, new Vector2D(positionPart.getX(), positionPart.getY()), damagePart.getExplosionRadius());
                Event mapDestructionEvent = new MapDestructionEvent(entity,new Vector2D(positionPart.getX(),positionPart.getY()),damagePart.getExplosionRadius());
                gameData.addEvent(explosionEvent);
                gameData.addEvent(mapDestructionEvent);
            }

        }

        // update pos with velo
        positionPart.setX(positionPart.getX() + getVelocity().getX() * dt);
        positionPart.setY(positionPart.getY() + getVelocity().getY() * dt);
    }

    /**
     * adds a vector to the velocity vector
     * @param velocity
     */
    private void addVelocity(Vector2D velocity) {
        Vector2D prevVelocity = getVelocity();
        prevVelocity.add(velocity);
        this.setVelocity(prevVelocity);
    }


}
