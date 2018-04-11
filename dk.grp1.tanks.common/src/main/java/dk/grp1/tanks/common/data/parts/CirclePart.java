package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;

/**
 * CirclePart defines the shape and size of a given entity
 */
public class CirclePart implements IEntityPart {

    private float centreX;
    private float centreY;
    private float radius;

    public CirclePart(float centreX, float centreY, float radius) {
        this.centreX = centreX;
        this.centreY = centreY;
        this.radius = radius;
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {

    }

    /**
     * Returns the x-coordinate of the circle's centre
     * @return
     */
    public float getCentreX() {
        return centreX;
    }

    /**
     * Sets the x-coordinate of the circle's centre
     * @param centreX
     */
    public void setCentreX(float centreX) {
        this.centreX = centreX;
    }

    /**
     * Returns the y-coordinate of the circle's centre
     * @return
     */
    public float getCentreY() {
        return centreY;
    }

    /**
     * Sets the y-coordinate of the circle's centre
     * @param centreY
     */
    public void setCentreY(float centreY) {
        this.centreY = centreY;
    }

    /**
     * Returns the circle's radius
     * @return
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Sets the circle's radius
     * @param radius
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }
}
