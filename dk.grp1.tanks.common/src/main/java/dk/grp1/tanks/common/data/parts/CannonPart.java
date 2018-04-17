package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CannonPart implements IEntityPart{

    private float jointX;
    private float jointY;
    private Vector2D[] vertices;
    private float direction;
    private Vector2D directionVector;
    private float length;
    private float width;
    private float firepower;
    private float maxFirepower = 250;
    private String texturePath;
    private float pi = 3.1415f;
    private float previousFirepower;
    private float previousAngle;
    private boolean increase = true;
    private float increasingFactor;

    public CannonPart(float jointX, float jointY, float direction, float width, float length, String texturePath) {
        this.jointX = jointX;
        this.jointY = jointY;
        this.vertices = new Vector2D[4];
        setDirection(direction);
        this.length = length;
        this.width = width;
        this.texturePath = texturePath;
        this.updateShape(); // Updates the shape of the cannon. Otherwise the cannon's vertices is null.
        this.increasingFactor = 65; //this is the deafault increasing factor. this can be changes through the set method.
    }

    /**
     * Calculates the coordinates of the cannon's vertices.
     */
    private void updateShape(){


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

        // Update the direction vector of the cannon
        directionVector = Vector2D.subtractVectors(d, a).unitVector();

    }

    public float calculateFirepower(GameData gameData){
        float time = gameData.getDelta();

        if (increase) {
            firepower += (time * increasingFactor * maxFirepower / 100);
        } else {
            firepower -= (time * increasingFactor * maxFirepower / 100);
        }

        if (1 < firepower/maxFirepower) { //percentage of max firepower
            firepower = maxFirepower;
            increase = false;
        }

        if (0 >= firepower/maxFirepower) {
            firepower = 0;
            increase = true;
        }

        return this.firepower;
    }

    public void setIncreasingFactor(float factor){
        this.increasingFactor = factor;
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {
        this.updateShape();
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

    public float getMaxFirepower(){ return this.maxFirepower; }

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

    public Vector2D getDirectionVector() {
        return this.directionVector;
    }

    public void setDirection(float direction) {
        this.direction = direction%(pi*2);
    }

    public float getLength() {
        return length;
    }

    public float getWidth() {
        return width;
    }

    public float getFirepower() {
        return firepower;
    }

    public void setFirepower(float firepower) {
        if (firepower > maxFirepower){
            this.firepower = maxFirepower;
        } else {
            this.firepower = firepower;
        }
    }

    public String getTexturePath() {
        return texturePath;
    }

    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
    }

    public float getPreviousAngle() { return previousAngle; }

    public float getPreviousFirepower() { return previousFirepower; }

    public void setPreviousAngle(float previousAngle) { this.previousAngle = previousAngle; }

    public void setPreviousFirepower(float previousFirepower) { this.previousFirepower = previousFirepower; }
}
