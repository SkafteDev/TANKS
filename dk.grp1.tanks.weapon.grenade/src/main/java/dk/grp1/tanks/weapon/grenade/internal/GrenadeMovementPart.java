package dk.grp1.tanks.weapon.grenade.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.utils.Vector2D;

public class GrenadeMovementPart extends MovementPart {
    public GrenadeMovementPart(Vector2D velocity, float maxSpeed) {
        super(velocity, maxSpeed);
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {
        // Get time since last process
        float dt = gameData.getDelta();

        // get pos
        PositionPart position = entity.getPart(PositionPart.class);
        if (position == null) {
            return; // IF no pos, we cant move
        }

        PhysicsPart physicsPart = entity.getPart(PhysicsPart.class);
        // update velocity with grav
        if (physicsPart != null) {
            addVelocity(physicsPart.getGravityVector());
        }

        GrenadeCollisionPart collisionPart = entity.getPart(GrenadeCollisionPart.class);

        if (collisionPart != null && collisionPart.isHitGameMap()) {
            collisionPart.updateBouncingVector(this.getVelocity());
            this.setVelocity(collisionPart.getBouncingVector());
            collisionPart.setHitGameMap(false);
        }

        // update pos with velo
        position.setX(position.getX() + getVelocity().getX() * dt);
        position.setY(position.getY() + getVelocity().getY() * dt);
        // update circle part
        CirclePart circlePart = entity.getPart(CirclePart.class);
        if (circlePart != null) {
            circlePart.setCentreX(position.getX());
            circlePart.setCentreY(position.getY());
        }
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
