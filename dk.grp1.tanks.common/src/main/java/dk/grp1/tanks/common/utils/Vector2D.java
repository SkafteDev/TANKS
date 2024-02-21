package dk.grp1.tanks.common.utils;

import java.util.ArrayList;
import java.util.List;


public class Vector2D {
    private float x;
    private float y;

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void setX(float x) {
        this.x = x;
    }


    public void setY(float y) {
        this.y = y;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Float.floatToIntBits(this.x);
        hash = 89 * hash + Float.floatToIntBits(this.y);
        return hash;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vector2D other = (Vector2D) obj;
        if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
            return false;
        }
        if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
            return false;
        }
        return true;
    }


    /**
     * Rotate this vector 90 degrees
     * @return
     */
    public Vector2D rotate90degrees() {
        return new Vector2D(-y+0.0f, x);
    }

    /**
     * Rotate this vector 90 degrees Clockwise
     * @return
     */
    public Vector2D rotateClockwise90degrees() {
        return new Vector2D(y, -x+0.0f);
    }

    /**
     * Get the length of this vector
     * @return
     */
    public float length() {
        float length = (float)Math.sqrt(getX()*getX()+getY()*getY()); // len = sqrt(x²+y²)
        return length;
    }

    /**
     * Returns the angle in radians from the unit vector(1, 0). I.e. the angle in the unit circle.
     * @return
     */
    public float getAngle(){
        return getAngle(new Vector2D(1, 0));
    }

    /**
     * Returns the angle in radians from the given vector.
     * @param other
     * @return
     */
    public float getAngle(Vector2D other) {

        float v = Vector2D.dot(this, other)/(this.length()*other.length());

        float radians = (float) Math.acos(v);

        return radians;
    }

    /**
     * Get the unit vector of this vector
     * @return
     */
    public Vector2D unitVector() {
        float length = length();

        if (length == 0) {
            throw new Error("Length must be more that 0");
        }

        return new Vector2D(getX()/length, getY()/length);
    }

    /**
     * Project a this vector onto the 'other' vector
     * @param other The other vector
     * @return
     */
    public Vector2D projectOnto(Vector2D other) {

        // Formula for projecting b (this) onto a (other)
        // projection = dot(a,b)/(len(a)²) * a

        float dot = Vector2D.dot(this, other);
        float length = other.length();
        float factor = dot/(length*length);

        Vector2D projection = new Vector2D(factor*other.x, factor*other.y);
        return projection;
    }

    /**
     * The Dot Product for two given Vectors
     * @param a
     * @param b
     * @return
     */
    public static float dot(Vector2D a, Vector2D b) {
        float dot = a.getX() * b.getX() + a.getY() * b.getY();

        return dot;
    }

    public float dot(){
        return dot(this, new Vector2D(1,0));
    }


    /**
     * '
     * Returns the list of vertices as a float array
     *
     * @return float[]
     */
    public static float[] getVerticesAsFloatArray(Vector2D[] vertices) {
        float[] floatVertices = new float[vertices.length * 2];
        for (int i = 0; i < vertices.length; i++) {
            floatVertices[i*2] = vertices[i].getX();
            floatVertices[i*2+1] = vertices[i].getY();
        }
        return floatVertices;
    }

    /**
     * Generates vectors based on x- and y-coordinates.
     * Requires at least two pairs of coordinates.
     * @param shapex
     * @param shapey
     * @return
     */
    public static List<Vector2D> getVectors(float[] shapex, float[] shapey) {
        List<Vector2D> vectors = new ArrayList<>();

        for (int i = 0, j = shapex.length - 1;
             i < shapey.length;
             j = i++) {
            Vector2D vect = new Vector2D(shapex[i]-shapex[j], shapey[i]-shapey[j]);
            vectors.add(vect);
        }

        return vectors;
    }



    @Override
    public String toString(){
        return "x: " + this.getX() + ", y: " + getY();
    }

    /**
     * Generates vectors based on x- and y-coordinates.
     * The vectors will all begin in origin.
     * @param shapex
     * @param shapey
     * @return
     */
    public static List<Vector2D> getOriginVectors(float[] shapex, float[] shapey) {
        List<Vector2D> vectors = new ArrayList<>();

        for (int i = 0; i < shapey.length; i++) {
            Vector2D vect = new Vector2D(shapex[i], shapey[i]);
            vectors.add(vect);
        }

        return vectors;
    }

    /**
     * Generate normal vector from the given list of vectors.
     * @param vectors
     * @return
     */
    public static List<Vector2D> getNormals(List<Vector2D> vectors) {
        List<Vector2D> normals = new ArrayList<>();

        for (Vector2D vect : vectors) {
            normals.add(vect.rotate90degrees());
        }

        return normals;
    }

    /**
     * Instantiate a vector by subtracting two given vectors
     * @param a
     * @param b
     * @return
     */
    public static Vector2D subtractVectors(Vector2D a, Vector2D b){
        return new Vector2D(a.getX()-b.getX(), a.getY()-b.getY());
    }

    /**
     * Subtract a vector from this vector
     * @param otherVector
     */
    public void subtract(Vector2D otherVector){
        Vector2D newVector = subtractVectors(this, otherVector);
        this.x = newVector.getX();
        this.y = newVector.getY();
    }

    /**
     * Add a vector to this vector
     * @param otherVector
     */
    public void add(Vector2D otherVector) {
        this.setX(this.getX() + otherVector.getX());
        this.setY(this.getY() + otherVector.getY());

    }

    /**
     * Instantiate a new vector by taking the sum of two given vectors
     * @param a
     * @param b
     * @return
     */
    public static Vector2D sumVectors(Vector2D a, Vector2D b){
        return new Vector2D(a.getX()+b.getX(), a.getY()+b.getY());
    }

    /**
     * Multiply this vector with a given constant
     * @param f
     */
    public void multiplyWithConstant(float f){
        this.x *= f;
        this.y *= f;
    }
}


