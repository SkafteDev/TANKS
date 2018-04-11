package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.utils.Vector2D;

public class ControlPart implements IEntityPart {

    private boolean left, right;
    private Vector2D controlVector;
    private float speed;
    private Vector2D rotation;



    public ControlPart(float speed){
        this.speed = speed;
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {
        controlVector = new Vector2D(rotation.getX(),rotation.getY());
        controlVector.unitVector();
        if (right()) {
            // go right
            controlVector.multiplyWithConstant(speed);
        }
        if (left()) {
            // go left
            controlVector.multiplyWithConstant(-1*speed);
        }
        if (left() == right()){
            controlVector = new Vector2D(0,0);
        }
    }


    public void setRotation(Vector2D rotation) {
        this.rotation = rotation.unitVector();
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
     * Returns the speed
     *
     * @return  the max change in horizontal and vertical velocity in (m/s)/s
     */
    public float getSpeed() {
        return speed;
    }



    /**
     * Sets the speed
     *
     * @param speed max change in velocity in (m/s)/s
     *
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }


    public Vector2D getControlVector() {
        return controlVector;
    }
}
