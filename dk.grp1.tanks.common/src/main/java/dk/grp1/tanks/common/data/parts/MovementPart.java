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
    private float deceleration;
    private float maxSpeed;

    public MovementPart(Vector2D velocity, float maxSpeed, float deceleration) {

        this.velocity = velocity;
        this.maxSpeed = maxSpeed;
        this.deceleration = deceleration;
    }

    public MovementPart(float maxSpeed, float deceleration) {

        this(new Vector2D(0, 0), maxSpeed, deceleration);
    }

    public void processPart(Entity entity, GameData gameData) {
        float dt = gameData.getDelta();
        Vector2D change = new Vector2D(0, 0);

        // get pos
        PositionPart position = entity.getPart(PositionPart.class);
        if (position == null) {
            return;
        }

        PhysicsPart physicsPart = entity.getPart(PhysicsPart.class);

        // update velocity with accel and grav
        if (physicsPart != null) {
            change.add(physicsPart.getGravityVector());
        }


        CollisionPart collisionPart = entity.getPart(CollisionPart.class);
        if (collisionPart != null && collisionPart.isHitGameMap()){
            ControlPart controls = entity.getPart(ControlPart.class);
            if (controls != null) {
                // set acceleration
                change.add(controls.getControlVector());
            }
            change.add(collisionPart.getCollisionVector());

            setVelocity(getVelocity().getX(), 0);
            // Decelerate
            decelerate(deceleration, dt);

            //collisionPart.setHitGameMap(false);
        }



        addVelocity(change);



        // update pos with velo
        position.setX(position.getX() + getVelocity().getX() * dt);
        position.setY(position.getY() + getVelocity().getY() * dt);


    }

    private void decelerate(float deceleration, float dt) {
        float speed = getVelocity().length();

        if (speed > 1) {
            float changeX = -1 * (getVelocity().getX() / speed) * deceleration * dt;
            float changeY = -1 * (getVelocity().getY() / speed) * deceleration * dt;
            addVelocity(
                    new Vector2D(changeX, changeY)
            );
        } else {
            setVelocity(0,0);
        }

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
