package dk.grp1.tanks.common.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class Vector2DTest {

    private final static float X = 30.22f;
    private final static float Y = 122.22f;

    public static Vector2D getTestVector() {
        return new Vector2D(Vector2DTest.X, Vector2DTest.Y);
    }

    @Test
    public void getX() {
        Vector2D instance = getTestVector();
        float expResult = Vector2DTest.X;
        float result = instance.getX();
        assertEquals(expResult, result, 0.0f);
    }

    @Test
    public void getY() {
        Vector2D instance = getTestVector();
        float expResult = Vector2DTest.Y;
        float result = instance.getY();
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void setX() {
        float x = 0.0F;
        Vector2D instance = getTestVector();
        instance.setX(x);

        float result = instance.getX();

        assertEquals(0.0f, result, 0.0);
    }

    @Test
    public void setY() {
        float y = 0.0F;
        Vector2D instance = getTestVector();
        instance.setY(y);

        float result = instance.getY();

        assertEquals(0.0f, result, 0.0);
    }

    @Test
    public void rotate90degrees() {
        Vector2D instance = getTestVector();
        Vector2D expResult = new Vector2D(-getTestVector().getY(), getTestVector().getX());
        Vector2D result = instance.rotate90degrees();
        assertEquals(expResult, result);
    }

    @Test
    public void rotateClockwise90degrees() {
        Vector2D instance = getTestVector();
        Vector2D expResult = new Vector2D(getTestVector().getY(), -getTestVector().getX()+0.0f);
        Vector2D result = instance.rotateClockwise90degrees();
        assertEquals(expResult, result);
    }

    @Test
    public void length() {
        Vector2D instance = getTestVector();
        float expResult = (float)Math.sqrt(getTestVector().getX()*getTestVector().getX()+getTestVector().getY()*getTestVector().getY());
        float result = instance.length();
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void getAngleRadians() {
        Vector2D vectorOne = new Vector2D(0, 1);
        Vector2D vectorTwo = new Vector2D(-1, 0);

        float v = Vector2D.dot(vectorOne, vectorTwo)/(vectorOne.length()*vectorTwo.length());
        float expectedRadiansResult = (float) Math.acos(v);

        float result = vectorOne.getAngle(vectorTwo);

        assertEquals(expectedRadiansResult, result, 0.01f);
    }

    @Test
    public void getAngleFromUnitVector() {
        Vector2D vectorOne = new Vector2D(0, 1);
        Vector2D vectorTwo = new Vector2D(1, 0);

        float v = Vector2D.dot(vectorOne, vectorTwo)/(vectorOne.length()*vectorTwo.length());
        float expectedRadiansResult = (float) Math.acos(v);

        float result = vectorOne.getAngle();

        assertEquals(expectedRadiansResult, result, 0.01f);
    }

    @Test
    public void unitVector() {
        Vector2D instance = getTestVector();

        float expResult = 1.0f;

        Vector2D vect = instance.unitVector();
        float result = vect.length();

        assertEquals(expResult, result, 0.0f);
    }

    @Test
    public void projectOnto() {
        Vector2D other = new Vector2D(5.123f, 10.123f);
        Vector2D instance = getTestVector();
        float factor = (instance.getX()*other.getX() + instance.getY()*other.getY())/(other.length()*other.length());
        Vector2D expResult = new Vector2D(5.123f*factor, 10.123f*factor);
        Vector2D result = instance.projectOnto(other);
        assertEquals(expResult, result);
    }

    @Test
    public void dot() {
        Vector2D a = new Vector2D(11.11f, 12.12f);
        Vector2D b = new Vector2D(55.55f, 65.65f);;
        float expResult = 1412.8385F;
        float result = Vector2D.dot(a, b);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void getVerticesAsFloatArray() {
        int n = 10;
        Vector2D[] vectors = new Vector2D[n];
        for (int i = 0; i < n; i++) {
            vectors[i] = new Vector2D(i, i-1);
        }

        float[] expectedResult = new float[] {0.0f, -1.0f, 1.0f, 0.0f, 2.0f, 1.0f, 3.0f, 2.0f, 4.0f, 3.0f, 5.0f, 4.0f, 6.0f, 5.0f, 7.0f, 6.0f, 8.0f, 7.0f, 9.0f, 8.0f};
        float[] result = Vector2D.getVerticesAsFloatArray(vectors);

        for (int i = 0; i < expectedResult.length; i++) {
            assertEquals(expectedResult[i], result[i], 0.01f);
        }
    }

    @Test
    public void getVectors() {
        float[] shapex = new float[] {1.0f, 3.0f, 3.0f, 0.0f};
        float[] shapey = new float[] {5.0f, 2.0f, 1.0f, 1.0f};
        List<Vector2D> expResult = new ArrayList<>();
        expResult.add(new Vector2D(1.0f, 4.0f));
        expResult.add(new Vector2D(2.0f, -3.0f));
        expResult.add(new Vector2D(0.0f, -1.0f));
        expResult.add(new Vector2D(-3.0f, 0.0f));

        List<Vector2D> result = Vector2D.getVectors(shapex, shapey);
        assertEquals(expResult, result);
    }

    @Test
    public void getOriginVectors() {
        float[] shapex = new float[] {1.0f, 3.0f, 3.0f, 0.0f};
        float[] shapey = new float[] {5.0f, 2.0f, 1.0f, 1.0f};
        List<Vector2D> expResult = new ArrayList<>();
        expResult.add(new Vector2D(1.0f, 5.0f));
        expResult.add(new Vector2D(3.0f, 2.0f));
        expResult.add(new Vector2D(3.0f, 1.0f));
        expResult.add(new Vector2D(0.0f, 1.0f));
        List<Vector2D> result = Vector2D.getOriginVectors(shapex, shapey);
        assertEquals(expResult, result);
    }

    @Test
    public void getNormals() {
        List<Vector2D> vectors = new ArrayList<>();
        vectors.add(new Vector2D(1.0f, 4.0f));
        vectors.add(new Vector2D(2.0f, -3.0f));
        vectors.add(new Vector2D(0.0f, -1.0f));
        vectors.add(new Vector2D(-3.0f, 0.0f));

        List<Vector2D> expResult = new ArrayList<>();
        expResult.add(new Vector2D(-4.0f, 1.0f));
        expResult.add(new Vector2D(3.0f, 2.0f));
        expResult.add(new Vector2D(1.0f, 0.0f));
        expResult.add(new Vector2D(0.0f, -3.0f));

        List<Vector2D> result = Vector2D.getNormals(vectors);
        assertEquals(expResult, result);
    }

    @Test
    public void subtractVectors() {
        Vector2D vectorOne = new Vector2D(5, 10);
        Vector2D vectorTwo = new Vector2D(10, 5);

        Vector2D expectedResult = new Vector2D(-5, 5);
        Vector2D result = Vector2D.subtractVectors(vectorOne, vectorTwo);

        assertEquals(expectedResult, result);
    }

    @Test
    public void subtract() {
        Vector2D vectorOne = new Vector2D(5, 10);
        Vector2D vectorTwo = new Vector2D(10, 5);

        Vector2D expectedResult = new Vector2D(-5, 5);
        vectorOne.subtract(vectorTwo);

        assertEquals(expectedResult, vectorOne);
    }

    @Test
    public void add() {
        Vector2D vectorOne = new Vector2D(5, 10);
        Vector2D vectorTwo = new Vector2D(10, 5);

        Vector2D expectedResult = new Vector2D(15, 15);
        vectorOne.add(vectorTwo);

        assertEquals(expectedResult, vectorOne);
    }

    @Test
    public void sumVectors() {
        Vector2D vectorOne = new Vector2D(5, 10);
        Vector2D vectorTwo = new Vector2D(10, 5);

        Vector2D expectedResult = new Vector2D(15, 15);
        Vector2D result = Vector2D.sumVectors(vectorOne, vectorTwo);

        assertEquals(expectedResult, result);
    }

    @Test
    public void multiplyWithConstant() {
        Vector2D vectorOne = new Vector2D(5, 10);

        Vector2D expectedResult = new Vector2D(25, 50);
        vectorOne.multiplyWithConstant(5);

        assertEquals(expectedResult, vectorOne);
    }
}