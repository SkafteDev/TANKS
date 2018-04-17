package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.utils.Vector2D;
import org.junit.Test;

import static org.junit.Assert.*;

public class ControlPartTest {


    private float speed = 100;
    private ControlPart cp;

    @Test
    public void processPart() {
    }

    @Test
    public void setRotation() {
        cp = new ControlPart(speed);
        Vector2D v = new Vector2D(15, 33);
        cp.setRotation(v);
    }

    @Test
    public void left() {
        cp = new ControlPart(speed);
        assertEquals(false, cp.left());
        cp.setLeft(true);
        assertEquals(true, cp.left());
    }

    @Test
    public void setLeft() {
        cp = new ControlPart(speed);
        assertEquals(false, cp.left());
        cp.setLeft(true);
        assertEquals(true, cp.left());
    }

    @Test
    public void right() {
        cp = new ControlPart(speed);
        assertEquals(false, cp.right());
        cp.setRight(true);
        assertEquals(true, cp.right());
    }

    @Test
    public void setRight() {
        cp = new ControlPart(speed);
        assertEquals(false, cp.right());
        cp.setRight(true);
        assertEquals(true, cp.right());
    }

    @Test
    public void getSpeed() {
        cp = new ControlPart(speed);
        assertEquals(speed, cp.getSpeed(), 0.001);
    }

    @Test
    public void setSpeed() {
        cp = new ControlPart(speed);
        assertEquals(speed, cp.getSpeed(), 0.001);
        float s = 100000;
        cp.setSpeed(s);
        assertEquals(s, cp.getSpeed(), 0.001);
    }

    @Test
    public void getControlVector() {

    }
}