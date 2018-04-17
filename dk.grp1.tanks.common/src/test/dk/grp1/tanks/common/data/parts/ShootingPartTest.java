package dk.grp1.tanks.common.data.parts;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShootingPartTest {

    private ShootingPart shootingPart;
    private float firePower = 160.5f;
    private boolean readyToShoot = false;
    private float timeSinceLastShot = 4.123f;

    @Before
    public void initialize() {
        shootingPart = new ShootingPart();
        shootingPart.setFirepower(firePower);
        shootingPart.setReadyToShoot(readyToShoot);
        shootingPart.setTimeSinceLastShot(timeSinceLastShot);
    }

    @Test
    public void getTimeSinceLastShot() {
        assertEquals(shootingPart.getTimeSinceLastShot(), timeSinceLastShot, 0.01f);
    }

    @Test
    public void setTimeSinceLastShot() {
        float newTime = 22.02f;
        shootingPart.setTimeSinceLastShot(newTime);
        assertEquals(shootingPart.getTimeSinceLastShot(), newTime, 0.01f);
    }

    @Test
    public void getFirepower() {
        assertEquals(shootingPart.getFirepower(), firePower, 0.01f);
    }

    @Test
    public void setFirepower() {
        float newFirePower = 0.01f;
        shootingPart.setFirepower(newFirePower);
        assertEquals(shootingPart.getFirepower(), newFirePower, 0.01f);
    }

    @Test
    public void isReadyToShoot() {
        assertEquals(shootingPart.isReadyToShoot(), readyToShoot);
    }

    @Test
    public void setReadyToShoot() {
        shootingPart.setReadyToShoot(!readyToShoot);
        assertEquals(shootingPart.isReadyToShoot(), !readyToShoot);

    }
}