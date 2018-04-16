package dk.grp1.tanks.common.data.parts;

import org.junit.Test;

import static org.junit.Assert.*;

public class CirclePartTest {

    private float centreX = 100;
    private float centreY = 75;
    private float radius = 40;
    private CirclePart cp;


    @Test
    public void processPart() {
    }

    @Test
    public void getCentreX() {
        cp = new CirclePart(centreX,centreY,radius);
        assertEquals(centreX, cp.getCentreX(), 0.001);
    }

    @Test
    public void setCentreX() {
        cp = new CirclePart(centreX,centreY,radius);
        float x = 2;
        cp.setCentreX(x);
        assertEquals(x, cp.getCentreX(), 0.001);
    }

    @Test
    public void getCentreY() {
        cp = new CirclePart(centreX,centreY,radius);
        assertEquals(centreY, cp.getCentreY(), 0.001);
    }

    @Test
    public void setCentreY() {
        cp = new CirclePart(centreX,centreY,radius);
        float y = 2;
        cp.setCentreX(y);
        assertEquals(y, cp.getCentreX(), 0.001);
    }

    @Test
    public void getRadius() {
        cp = new CirclePart(centreX,centreY,radius);
        assertEquals(radius, cp.getRadius(), 0.001);
    }

    @Test
    public void setRadius() {
        cp = new CirclePart(centreX,centreY,radius);
        float r = 2;
        cp.setRadius(r);
        assertEquals(r, cp.getRadius(), 0.001);
    }
}