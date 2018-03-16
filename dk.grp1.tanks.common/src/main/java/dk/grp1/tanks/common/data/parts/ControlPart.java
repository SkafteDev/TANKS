package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameMap;
import dk.grp1.tanks.common.utils.Vector2D;

import javax.naming.ldap.Control;

public class ControlPart implements IEntityPart {

    private boolean left, right;
    private Vector2D controlVector;
    private float acceleration;
    private Vector2D rotation;



    public ControlPart(float acceleration){
        this.acceleration = acceleration;
    }

    @Override
    public void processPart(Entity entity, GameData gameData) {
        controlVector = new Vector2D(0,0);
        float dt = gameData.getDelta();
        if (right()) {
            // accelerate right
            float change = acceleration * dt;
            controlVector.add(new Vector2D(change, 0));
        }
        if (left()) {
            // accelerate left

            float change = -1 * acceleration* dt;
            controlVector.add(new Vector2D(change, 0));
        }
    }


    public void setRotation(Vector2D rotation) {
        this.rotation = rotation;
    }

    public boolean left() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean right() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
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


    public Vector2D getControlVector() {
        return controlVector;
    }
}
