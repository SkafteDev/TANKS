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

        //Position of the cannon's connection to the player's body
        Vector2D jointPoint = new Vector2D(jointX,jointY);

        //Directions to the corners of the bottom of the cannon, before attaching to the player's body

        Vector2D a = new Vector2D((float) Math.cos(direction-(pi/2)), (float) (Math.sin(direction-(pi/2))));
        Vector2D b = new Vector2D((float) Math.cos(direction+(pi/2)), (float) (Math.sin(direction+(pi/2))));

        //Actual length of the corners of the bottom of the cannon from the jointPoint, before attaching to the player's body
        a.multiplyWithConstant(width/2);
        b.multiplyWithConstant(width/2);


        //Formula to calculate the direction and distance to the top of the cannon
        Vector2D aNormal = a.rotate90degrees();
        aNormal = aNormal.unitVector();
        aNormal.multiplyWithConstant(length);

        //Actual vectors to the corners of the bottom of the cannon
        a.add(jointPoint);
        b.add(jointPoint);

        //Actual vectors to the corners of the top of the cannon
        Vector2D c = Vector2D.sumVectors(aNormal,b);
        Vector2D d = Vector2D.sumVectors(aNormal,a);

        //Save the 4 corners of the cannon from bottom-right to top-right
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

        if (vertices[2] == null || vertices[3] == null) {
            throw new NullPointerException("Cannon's shape has not been processed yet and is therefore NULL");
        }

        //Calculates the face of the muzzle
        Vector2D centre = Vector2D.subtractVectors(vertices[2], vertices[3]);

        //Defines the centre of the muzzle
        centre.multiplyWithConstant(0.5f);
        centre.add(vertices[3]);
        return centre;

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
