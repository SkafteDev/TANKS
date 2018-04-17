package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExpirationPartTest {

    private float remainingLifeTime;
    private ExpirationPart ep;
    private World world;
    private GameData gameData;

    @Test
    public void getRemainingLifeTime() {
        ep = new ExpirationPart();
        float t = 10;
        ep.setRemainingLifeTime(t);
        assertEquals(t, ep.getRemainingLifeTime(), 0.001);

        ep = new ExpirationPart();
        t = -10;
        ep.setRemainingLifeTime(t);
        assertEquals(0, ep.getRemainingLifeTime(), 0.001);
    }

    @Test
    public void setRemainingLifeTime() {
        ep = new ExpirationPart();
        float t = 10;
        ep.setRemainingLifeTime(t);
        assertEquals(t, ep.getRemainingLifeTime(), 0.001);

        ep = new ExpirationPart();
        t = -10;
        ep.setRemainingLifeTime(t);
        assertEquals(0, ep.getRemainingLifeTime(), 0.001);
    }

    @Test
    public void processPart() {
        ep = new ExpirationPart();
        gameData = new GameData();
        gameData.setDelta(1);
        float t = 2;
        ep.setRemainingLifeTime(t);
        ep.processPart(null,gameData,null);
        assertEquals(1, ep.getRemainingLifeTime(), 0.001);
    }
}