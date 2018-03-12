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

    public float getCurrentSpeed(){
        return velocity.getLength();
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(float x, float y){
        this.velocity = new Vector2D(x,y);
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public Vector2D getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float x, float y){
        this.acceleration = new Vector2D(x,y);
    }
    public void setAcceleration(Vector2D acceleration) {
        this.acceleration = acceleration;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
