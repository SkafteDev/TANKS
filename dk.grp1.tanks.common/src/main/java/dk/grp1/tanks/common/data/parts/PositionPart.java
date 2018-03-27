package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;

/**
 * Created by danie on 12-03-2018.
 */
public class PositionPart implements IEntityPart {
    private float x;
    private float y;
    private float directionInRadians;

    /**
     * Creates a standard position part starting at 0,0 (x,y) and with a direction of 0
     */
    public PositionPart() {
        this(0,0,0);
    }

    /**
     * Creates a position part with given parameters set
     * @param x float
     * @param y float
     * @param directionInRadians float
     */
    public PositionPart(float x, float y, float directionInRadians) {
        this.x = x;
        this.y = y;
        this.directionInRadians = directionInRadians;
    }

    /**
     * Get x coordinate
     * @return float
     */
    public float getX() {
        return x;
    }

    /**
     * Set x coordinate
     * @param x float
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Get y coordinate
     * @return float
     */
    public float getY() {
        return y;
    }

    /**
     * Set y coordinate
     * @param y float
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Returns direction in radians
     * @return float
     */
    public float getDirectionInRadians() {
        return directionInRadians;
    }

    /**
     * Sets direction in radians
     * @param directionInRadians
     */
    public void setDirectionInRadians(float directionInRadians) {
            this.directionInRadians = directionInRadians;
    }

    public void processPart(Entity entity, GameData gameData, World world) {

    }
}
