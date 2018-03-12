package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.utils.Vector2D;

/**
 * Created by danie on 12-03-2018.
 */
public class MovementPart implements IEntityPart {

    private Vector2D velocity;
    private Vector2D acceleration;
    private float maxSpeed;

    public void processPart(Entity entity, GameData gameData) {

    }

    /**
     * Returns the current speed in m/s
     * @return
     */
    public float getCurrentSpeed(){
        return velocity.getLength();
    }

    /**
     * Returns the velocity (directional speed) as a Vector
     * @return
     */
    public Vector2D getVelocity() {
        return velocity;
    }

    /**
     * Sets the velocity
     * @param x change in horizontal position in m/s
     * @param y change in vertical position in m/s
     */
    public void setVelocity(float x, float y){
        setVelocity( Vector2D(x,y));
    }

    /**
     * Sets the velocity
     * @param velocity vector containing the change in horizontal and vertical position in m/s
     */
    public void setVelocity(Vector2D velocity) {
        if(velocity.getLength() > getMaxSpeed()){
            double ratio = velocity.getLength() / getMaxSpeed();
            velocity.setX(velocity.getX()/ratio);
            velocity.setY(velocity.getY()/ratio);
        }
        this.velocity = velocity;


    }

    /**
     * Returns the acceleration
     * @return vector containing the change in horizontal and vertical velocity in (m/s)/s
     */
    public Vector2D getAcceleration() {
        return acceleration;
    }

    /**
     * Sets the acceleration
     * @param x change in horizontal velocity in (m/s)/s
     * @param y change in vertical velocity in (m/s)/s
     */
    public void setAcceleration(float x, float y){
        this.acceleration = new Vector2D(x,y);
    }

    /**
     * Sets the acceleration
     * @param acceleration vector containing the change in horizontal and vertical velocity in (m/s)/s
     */
    public void setAcceleration(Vector2D acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * Get the maximum allowed speed
     * @return
     */
    public float getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * Sets the maximum allowed speed
     * @param maxSpeed
     */
    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
