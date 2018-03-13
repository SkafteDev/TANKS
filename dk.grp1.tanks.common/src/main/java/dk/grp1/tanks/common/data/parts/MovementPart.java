package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.utils.Vector2D;

import javax.naming.ldap.Control;

/**
 * Created by danie on 12-03-2018.
 */
public class MovementPart implements IEntityPart {

    private Vector2D velocity;
    private float acceleration;
    private float deceleration;
    private float maxSpeed;

    public MovementPart(Vector2D velocity, float acceleration, float maxSpeed, float deceleration) {

        this.velocity = velocity;
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.deceleration = deceleration;
    }

    public MovementPart(float acceleration, float maxSpeed, float deceleration) {

        this(new Vector2D(0, 0), acceleration, maxSpeed, deceleration);
    }

    public void processPart(Entity entity, GameData gameData) {
        float dt = gameData.getDelta();

        // get pos
        PositionPart position = entity.getPart(PositionPart.class);
        if (position == null) {
            return;
        }

        ControlPart controls = entity.getPart(ControlPart.class);
        if (controls != null) {
            // set acceleration
            if (controls.left()) {
                // accelerate left
                float change = acceleration* dt;
                addVelocity(new Vector2D(change, 0));
            } else if (controls.right()) {
                // accelerate right
                float change = -1 * acceleration* dt;
                addVelocity(new Vector2D(change, 0));
            }
        }

        // get grav from physics
        PhysicsPart physicsPart = entity.getPart(PhysicsPart.class);

        // update velocity with accel and grav
        if (physicsPart != null) {
            addVelocity(new Vector2D(0, physicsPart.getGravity()));
        }




        // Decelerate
        float speed = getVelocity().length();
        if (speed > 0) {
            float changeX = -1 * (getVelocity().getX() / speed) * deceleration * dt;
            float changeY = -1 * (getVelocity().getY() / speed) * deceleration * dt;
            addVelocity(
                    new Vector2D(changeX, changeY)
            );
        }

        // update pos with velo
        position.setX(position.getX() + getVelocity().getX() * dt);
        position.setY(position.getY() + getVelocity().getY() * dt);


    }

    private void addVelocity(Vector2D velocity) {
        Vector2D prevVelocity = getVelocity();
        prevVelocity.add(velocity);
        this.setVelocity(prevVelocity);
    }

    /**
     * Returns the current speed in m/s
     *
     * @return
     */
    public float getCurrentSpeed() {
        return velocity.length();
    }

    /**
     * Returns the velocity (directional speed) as a Vector
     *
     * @return
     */
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

    /**
     * Sets the velocity
     *
     * @param x change in horizontal position in m/s
     * @param y change in vertical position in m/s
     */
    public void setVelocity(float x, float y) {
        setVelocity(new Vector2D(x, y));
    }

    /**
     * Returns the acceleration
     *
     * @return  the max change in horizontal and vertical velocity in (m/s)/s
     */
    public float getAcceleration() {
        return acceleration;
    }



    /**
     * Sets the acceleration
     *
     * @param acceleration max change in velocity in (m/s)/s
     *
     */
    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * Get the maximum allowed speed
     *
     * @return
     */
    public float getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * Sets the maximum allowed speed
     *
     * @param maxSpeed
     */
    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
