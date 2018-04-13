package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;

public class ShootingPart implements IEntityPart {

    private float timeSinceLastShot;
    float firepower;
    private boolean isReadyToShoot;

    public ShootingPart() {
        this.timeSinceLastShot = 0;
        this.firepower = 0;
        this.isReadyToShoot = false;
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {

    }

    public float getTimeSinceLastShot() {
        return timeSinceLastShot;
    }

    public void setTimeSinceLastShot(float timeSinceLastShot) {
        this.timeSinceLastShot = timeSinceLastShot;
    }

    public float getFirepower() {
        return firepower;
    }

    public void setFirepower(float firepower) {
        this.firepower = firepower;
    }

    public boolean isReadyToShoot() {
        return isReadyToShoot;
    }

    public void setReadyToShoot(boolean readyToShoot) {
        isReadyToShoot = readyToShoot;
    }
}
