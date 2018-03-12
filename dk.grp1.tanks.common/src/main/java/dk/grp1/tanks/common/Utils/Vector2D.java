package dk.grp1.tanks.common.Utils;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ccl
 */
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



    public Vector2D rotate90degrees() {
        return new Vector2D(-y+0.0f, x);
    }

    public float length() {
        float length = (float)Math.sqrt(getX()*getX()+getY()*getY()); // len = sqrt(x²+y²)
        return length;
    }

    public Vector2D unitVector() {
        float length = length();

        if (length == 0) {
            throw new Error("Length must be more that 0");
        }

        return new Vector2D(getX()/length, getY()/length);
    }

    public Vector2D projectOnto(Vector2D other) {

        // Formula for projecting b (this) onto a (other)
        // projection = dot(a,b)/(len(a)²) * a

        float dot = Vector2D.dot(this, other);
        float length = other.length();
        float factor = dot/(length*length);

        Vector2D projection = new Vector2D(factor*other.x, factor*other.y);
        return projection;
    }

    public static float dot(Vector2D a, Vector2D b) {
        float dot = a.getX() * b.getX() + a.getY() * b.getY();

        return dot;
    }

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

    public static List<Vector2D> getOriginVectors(float[] shapex, float[] shapey) {
        List<Vector2D> vectors = new ArrayList<>();

        for (int i = 0; i < shapey.length; i++) {
            Vector2D vect = new Vector2D(shapex[i], shapey[i]);
            vectors.add(vect);
        }

        return vectors;
    }

    public static List<Vector2D> getNormals(List<Vector2D> vectors) {
        List<Vector2D> normals = new ArrayList<>();

        for (Vector2D vect : vectors) {
            normals.add(vect.rotate90degrees());
        }

        return normals;
    }

    public static Point[] projectAndGetMinMax(Vector2D normal, List<Vector2D> shapeVectors) {
        Point[] minMax = new Point[2]; // Index0 is min, Index1 is max

        // Initialize min and max to first element
        Vector2D projection = shapeVectors.get(0).projectOnto(normal);
        minMax[0] = new Point(projection.getX(), projection.getY());
        minMax[1] = new Point(projection.getX(), projection.getY());

        for (int i = 1; i < shapeVectors.size(); i++) {
            projection = shapeVectors.get(i).projectOnto(normal);
            float x = projection.getX();
            float y = projection.getY();

            Point p = new Point(x, y);

            if (p.compareTo(minMax[0]) < 0) {
                // We got a new minimum point
                minMax[0] = p;
            } else if (p.compareTo(minMax[1]) > 0) {
                // We got a new maximum point
                minMax[1] = p;
            }
        }

        return minMax;
    }
}


