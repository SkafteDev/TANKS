package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.utils.Vector2D;
import org.junit.Test;

import static org.junit.Assert.*;

public class PhysicsPartTest {

    private float mass = 30;
    private float gravity = 100;
    private PhysicsPart pp;
    private GameData gameData;

    @Test
    public void processPart() {
        pp = new PhysicsPart(mass, gravity);
        gameData = new GameData();
        gameData.setDelta(1);
        pp.processPart(null, gameData, null);
        assertEquals(gravity, pp.getGravityVector().getY(), 0.001);
    }

    @Test
    public void getMass() {
        pp = new PhysicsPart(mass, gravity);
        assertEquals(mass, pp.getMass(), 0.001);
    }

    @Test
    public void setMass() {
        pp = new PhysicsPart(mass, gravity);
        float m = 500;
        pp.setMass(m);
        assertEquals(m, pp.getMass(), 0.001);
    }

    @Test
    public void getGravity() {
        pp = new PhysicsPart(mass, gravity);
        assertEquals(gravity, pp.getGravity(), 0.001);
    }

    @Test
    public void setGravity() {
        pp = new PhysicsPart(mass, gravity);
        float g = 500;
        pp.setGravity(g);
        assertEquals(g, pp.getGravity(), 0.001);
    }

    @Test
    public void getGravityVector() {
        pp = new PhysicsPart(mass, gravity);
        gameData = new GameData();
        gameData.setDelta(0.5f);
        pp.processPart(null, gameData, null);
        assertEquals(gravity/2, pp.getGravityVector().getY(), 0.001);
    }
}