package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.utils.Vector2D;
import org.junit.Test;

import static org.junit.Assert.*;

public class CannonPartTest {
    private float jointX = 100;
    private float jointY = 100;
    private float direction = 3.14f;
    private float width = 20;
    private float length = 60;
    private String texture = "texture";
    private CannonPart cp;
    private GameData gameData = new GameData();

    @Test
    public void calculateFirepower() {
        cp = new CannonPart(jointX, jointY, direction, width, length, texture);

        float firePower = 0;
        gameData.setDelta(1);
        cp.calculateFirepower(gameData);
        assertEquals(162.5, cp.getFirepower(), 0.001);


        firePower = 0;
        gameData.setDelta(0.5f);
        cp.setFirepower(0);
        cp.calculateFirepower(gameData);
        assertEquals(81.25, cp.getFirepower(), 0.001);


        firePower = 0;
        gameData.setDelta(5);
        cp.calculateFirepower(gameData);
        gameData.setDelta(0.5f);
        cp.calculateFirepower(gameData);
        assertEquals(168.75, cp.getFirepower(), 0.001);
    }

    @Test
    public void processPart() {
    }

    @Test
    public void getMuzzleFaceCentre() {
        cp = new CannonPart(jointX, jointY, direction, width, length, texture);
        assertEquals(new Vector2D(39.999607f, 100.09277f), cp.getMuzzleFaceCentre());

        cp = new CannonPart(jointX, jointY, direction / 8, width, length, texture);
        assertEquals(new Vector2D(155.4367f, 122.95272f), cp.getMuzzleFaceCentre());
    }

    @Test
    public void getMaxFirepower() {
        cp = new CannonPart(jointX, jointY, direction, width, length, texture);
        assertEquals(250, cp.getMaxFirepower(), 0.0001);
    }

    @Test
    public void getJointX() {
        cp = new CannonPart(jointX, jointY, direction, width, length, texture);
        assertEquals(jointX, cp.getJointX(), 0.0001);
    }

    @Test
    public void setJointX() {
        cp = new CannonPart(jointX, jointY, direction, width, length, texture);
        float x = 50;
        cp.setJointX(x);
        assertEquals(x, cp.getJointX(), 0.0001);
    }

    @Test
    public void getJointY() {
        cp = new CannonPart(jointX, jointY, direction, width, length, texture);
        assertEquals(jointY, cp.getJointY(), 0.0001);
    }

    @Test
    public void setJointY() {
        cp = new CannonPart(jointX, jointY, direction, width, length, texture);
        float y = 50;
        cp.setJointY(y);
        assertEquals(y, cp.getJointY(), 0.0001);
    }

    @Test
    public void getVertices() {
        cp = new CannonPart(jointX, jointY, direction, width, length, texture);
        assertEquals(new Vector2D(100.015465f,109.999985f),cp.getVertices()[0]);
        assertEquals(new Vector2D(99.98361f, 90.000015f),cp.getVertices()[1]);
        assertEquals(new Vector2D(39.98368f, 90.09279f),cp.getVertices()[2]);
        assertEquals(new Vector2D(40.015533f, 110.09276f),cp.getVertices()[3]);
    }

    @Test
    public void getDirection() {
        cp = new CannonPart(jointX, jointY, direction, width, length, texture);
        assertEquals(direction, cp.getDirection(), 0.0001);
    }

    @Test
    public void getDirectionVector() {
        cp = new CannonPart(jointX, jointY, direction, width, length, texture);
        assertEquals(-1, cp.getDirectionVector().getX(), 0.01);
        assertEquals(0, cp.getDirectionVector().getY(), 0.01);

        cp = new CannonPart(jointX, jointY, direction/2, width, length, texture);
        assertEquals(0, cp.getDirectionVector().getX(), 0.01);
        assertEquals(1, cp.getDirectionVector().getY(), 0.01);
    }

    @Test
    public void setDirection() {
        cp = new CannonPart(jointX, jointY, direction, width, length, texture);
        float d = 3.14f * 2;
        cp.setDirection(d);
        assertEquals(d, cp.getDirection(), 0.0001);
    }

    @Test
    public void getLength() {
        cp = new CannonPart(jointX, jointY, direction, width, length, texture);
        assertEquals(length, cp.getLength(), 0.0001);
    }

    @Test
    public void getWidth() {
        cp = new CannonPart(jointX, jointY, direction, width, length, texture);
        assertEquals(width, cp.getWidth(), 0.0001);
    }

    @Test
    public void getFirepower() {
        cp = new CannonPart(jointX, jointY, direction, width, length, texture);
        assertEquals(0, cp.getFirepower(), 0.0001);
    }

    @Test
    public void setFirepower() {
        cp = new CannonPart(jointX, jointY, direction, width, length, texture);
        float fp = 100;
        cp.setFirepower(fp);
        assertEquals(fp, cp.getFirepower(), 0.0001);


        cp = new CannonPart(jointX, jointY, direction, width, length, texture);
        fp = 600;
        cp.setFirepower(fp);
        assertEquals(cp.getMaxFirepower(), cp.getFirepower(), 0.0001);
    }

    @Test
    public void getTexturePath() {
        cp = new CannonPart(jointX, jointY, direction, width, length, texture);
        assertEquals(texture, cp.getTexturePath());
    }

    @Test
    public void setTexturePath() {
        cp = new CannonPart(jointX, jointY, direction, width, length, texture);
        String t = "Best/hEst/tesT";
        cp.setTexturePath(t);
        assertEquals(t, cp.getTexturePath());
    }

    @Test
    public void getPreviousAngle() {
        cp = new CannonPart(jointX, jointY, direction, width, length, texture);
        float previousAngle = 500;
        cp.setPreviousAngle(previousAngle);
        assertEquals(previousAngle, cp.getPreviousAngle(), 0.001);
    }

    @Test
    public void getPreviousFirepower() {
        cp = new CannonPart(jointX, jointY, direction, width, length, texture);
        float i = 1;
        cp.setPreviousFirepower(i);
        assertEquals(i, cp.getPreviousFirepower(), 0.0001);
    }

    @Test
    public void setPreviousAngle() {
        cp = new CannonPart(jointX, jointY, direction, width, length, texture);
        float previousAngle = 500;
        cp.setPreviousAngle(previousAngle);
        assertEquals(previousAngle, cp.getPreviousAngle(), 0.001);
    }

    @Test
    public void setPreviousFirepower() {
        cp = new CannonPart(jointX, jointY, direction, width, length, texture);
        float i = 1;
        cp.setPreviousFirepower(i);
        assertEquals(i, cp.getPreviousFirepower(), 0.0001);
    }
}