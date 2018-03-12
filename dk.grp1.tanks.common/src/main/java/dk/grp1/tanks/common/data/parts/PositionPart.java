package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;

/**
 * Created by danie on 12-03-2018.
 */
public class PositionPart implements IEntityPart {
    private float x;
    private float y;
    private float directionInRadians;

    public PositionPart() {
        this(0,0,0);
    }

    public PositionPart(float x, float y, float directionInRadians) {
        this.x = x;
        this.y = y;
        this.directionInRadians = directionInRadians;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getDirectionInRadians() {
        return directionInRadians;
    }

    public void setDirectionInRadians(float directionInRadians) {
        this.directionInRadians = directionInRadians;
    }

    public void processPart(Entity entity, GameData gameData) {

    }
}
