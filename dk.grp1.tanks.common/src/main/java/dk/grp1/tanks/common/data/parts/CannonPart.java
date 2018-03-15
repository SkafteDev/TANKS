package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CannonPart implements IEntityPart{

    private float jointX;
    private float jointY;
    private Vector2D[] vertices;
    private float direction;
    private float length;
    private float width;

    public CannonPart(float jointX, float jointY, float direction, float width, float length) {
        this.jointX = jointX;
        this.jointY = jointY;
        this.vertices = new Vector2D[4];
        this.direction = direction;
        this.length = length;
        this.width = width;
    }

    /**
     * Calculates the coordinates of the cannon's vertices.
     */
    private void updateShape(){
        float pi = 3.1415f;

        Vector2D a = new Vector2D((((float) Math.cos(direction) + (float) Math.cos(pi/2))) * width/2 + jointX
                ,(((float) Math.sin(direction) + (float )Math.cos(pi/2))) * width/2 + jointY);

        Vector2D b = new Vector2D((((float) Math.cos(direction) - (float) Math.cos(pi/2))) * width/2 + jointX
                ,(((float) Math.sin(direction) - (float )Math.cos(pi/2))) * width/2 + jointY);

        Vector2D c = new Vector2D(a.getX()+length, a.getY()+length);

        Vector2D d = new Vector2D(b.getX()+length, b.getY()+length);

        vertices[0] = a;
        vertices[1] = b;
        vertices[2] = c;
        vertices[3] = d;
    }

    @Override
    public void processPart(Entity entity, GameData gameData) {
        this.updateShape();
    }

    public Vector2D getCannonCentre(){
        return new Vector2D(jointX+length/2,jointY+length/2);
    }

    /**
     * Returns a bullet's exit point from the cannon.
     * @return
     */
    public Vector2D getMuzzleFaceCentre(){
        return new Vector2D(jointX + length, jointY + length);
    }

    public float getJointX() {
        return jointX;
    }

    public void setJointX(float jointX) {
        this.jointX = jointX;
    }

    public float getJointY() {
        return jointY;
    }

    public void setJointY(float jointY) {
        this.jointY = jointY;
    }

    public Vector2D[] getVertices() {
        return vertices;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public float getLength() {
        return length;
    }

    public float getWidth() {
        return width;
    }
}
