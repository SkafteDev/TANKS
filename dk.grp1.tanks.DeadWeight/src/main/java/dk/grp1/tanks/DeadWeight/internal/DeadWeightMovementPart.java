package dk.grp1.tanks.DeadWeight.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;

public class DeadWeightMovementPart extends MovementPart {

    private Vector2D velocity;
    private float maxSpeed;
    private ArrayList<Entity> targets;
    private boolean overTarget;

    public DeadWeightMovementPart(Vector2D velocity, float maxSpeed, ArrayList<Entity> targets) {
        super(velocity, maxSpeed);
        this.targets = targets;
        this.velocity = velocity;
        this.maxSpeed = maxSpeed;
    }

    public void processPart(Entity entity, GameData gameData, World world) {
        // Get time since last process
        float dt = gameData.getDelta();

        // get pos
        PositionPart position = entity.getPart(PositionPart.class);
        if (position == null) {
            return; // IF no pos, we cant move
        }

        if (!overTarget){
            checkForTarget(entity);
        }


        PhysicsPart physicsPart = entity.getPart(PhysicsPart.class);
        // update velocity with grav
        if (physicsPart != null) {
            addVelocity(physicsPart.getGravityVector());
        }

        CollisionPart collisionPart = entity.getPart(CollisionPart.class);
        if (collisionPart != null && collisionPart.isHitGameMap()) { // If hitting map
            ControlPart controls = entity.getPart(ControlPart.class);
            if (controls != null) {
                // set velocity the amount specified by the controlpart
                setVelocity(controls.getControlVector());
            } else {
                setVelocity(getVelocity().getX(), 0);
            }


        }

        // update pos with velo
        position.setX(position.getX() + getVelocity().getX() * dt);
        position.setY(position.getY() + getVelocity().getY() * dt);
        position.setDirectionInRadians(getVelocity().getAngle());

    }

    private void checkForTarget(Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);


        for (Entity target : targets) {
            PositionPart targetPos = target.getPart(PositionPart.class);
            if (Math.abs(positionPart.getX() - targetPos.getX()) < 2) {
                setVelocity(0, -100);
                PhysicsPart physicsPart = target.getPart(PhysicsPart.class);
                physicsPart.setGravity(-1000f);
                overTarget = true;
            }
        }
    }


    public Vector2D getVelocity() {
        return velocity;
    }

    /**
     * Sets the velocity
     *
     * @param velocity vector containing the change in horizontal and vertical position in m/s
     */
    public void setVelocity(Vector2D velocity) {
        if (velocity.length() > getMaxSpeed()) {
            float ratio = velocity.length() / getMaxSpeed();
            velocity.setX(velocity.getX() / ratio);
            velocity.setY(velocity.getY() / ratio);
        }
        this.velocity = velocity;


    }

    private void addVelocity(Vector2D velocity) {
        Vector2D prevVelocity = getVelocity();
        prevVelocity.add(velocity);
        this.setVelocity(prevVelocity);
    }

    /**
     * Sets the velocity
     *
     * @param x change in horizontal position in m/s
     * @param y change in vertical position in m/s
     */
    public void setVelocity(float x, float y) {
        setVelocity(new Vector2D(x, y));
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

}
