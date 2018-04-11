package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.utils.Vector2D;
import javafx.geometry.Pos;

import javax.naming.ldap.Control;

/**
 * Created by danie on 12-03-2018.
 */
public class MovementPart implements IEntityPart {

    private Vector2D velocity;
    private float maxSpeed;

    public MovementPart(Vector2D velocity, float maxSpeed) {

        this.velocity = velocity;
        this.maxSpeed = maxSpeed;
    }

    public MovementPart(float maxSpeed) {

        this(new Vector2D(0, 0), maxSpeed);
    }

    /**
     * changes velocity based on gravity from physicspart and controls
     * and updates pos based on velocity
     *
     * @param entity   the entity to move
     * @param gameData game data
     */
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

        CollisionPart collisionPart = entity.getPart(CollisionPart.class);
        if (collisionPart != null && collisionPart.isHitGameMap()) { // If hitting map
            if (getVelocity().getY() < 0) {
                ControlPart controls = entity.getPart(ControlPart.class);
                if (controls != null) {
                    // set velocity the amount specified by the controlpart
                    setVelocity(controls.getControlVector());
                } else {

                    setVelocity(getVelocity().getX(), 0);
                }
            }


        }

        // update pos with velo
        position.setX(position.getX() + getVelocity().getX() * dt);
        position.setY(position.getY() + getVelocity().getY() * dt);
        position.setDirectionInRadians(getVelocity().getAngle());
        // and circle centre
        CirclePart circlePart = entity.getPart(CirclePart.class);
        if (circlePart != null) {
            circlePart.setCentreX(position.getX());
            circlePart.setCentreY(position.getY());
        }

    }

    /**
     * adds a vector to the velocity vector
     *
     * @param velocity
     */
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
