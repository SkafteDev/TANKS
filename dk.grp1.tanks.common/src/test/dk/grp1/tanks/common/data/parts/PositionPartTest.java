package dk.grp1.tanks.common.data.parts;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PositionPartTest {

    private float x = 7;
    private float y = 8;
    private float directionInRadians = 3.14f;
    private PositionPart pp;

    @Before
    public void setUp() throws Exception {
        pp = new PositionPart(x,y,directionInRadians);
    }

    @Test
    public void getX() {
        assertEquals(x, pp.getX(), 0.001);
    }

    @Test
    public void setX() {
        pp.setX(10);
        assertEquals(10, pp.getX(), 0.001);
    }

    @Test
    public void getY() {
        assertEquals(y, pp.getY(), 0.001);
    }

    @Test
    public void setY() {
        pp.setY(54);
        assertEquals(54, pp.getY(), 0.001);
    }

    @Test
    public void getDirectionInRadians() {
        assertEquals(directionInRadians, pp.getDirectionInRadians(), 0.001);
    }

    @Test
    public void setDirectionInRadians() {
        pp.setDirectionInRadians(directionInRadians/2);
        assertEquals(directionInRadians/2, pp.getDirectionInRadians(), 0.001);
    }

    @Test
    public void processPart() {
        //not implemented
    }
}