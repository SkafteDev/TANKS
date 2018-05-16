package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;

public class ShootingPart implements IEntityPart {

    private float timeSinceLastShot;
    private float firepower;
    private boolean isReadyToShoot;

    public ShootingPart() {
        this.timeSinceLastShot = 0;
        this.firepower = 0;
        this.isReadyToShoot = false;
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {

    }

    /**
     * Returns time since last shot
     * @return
     */
    public float getTimeSinceLastShot() {
        return timeSinceLastShot;
    }

    /**
     * Sets the time since last shot
     * @param timeSinceLastShot
     */
    public void setTimeSinceLastShot(float timeSinceLastShot) {
        this.timeSinceLastShot = timeSinceLastShot;
    }

    /**
     * Returns the firepower
     * @return
     */
    public float getFirepower() {
        return firepower;
    }

    /**
     * Sets the firepower
     * @param firepower
     */
    public void setFirepower(float firepower) {
        this.firepower = firepower;
    }

    /**
     * Returns if the entity is ready to shoot
     * @return
     */
    public boolean isReadyToShoot() {
        return isReadyToShoot;
    }
    /**
     * Sets if the entity is ready to shoot
     * @return
     */
    public void setReadyToShoot(boolean readyToShoot) {
        isReadyToShoot = readyToShoot;
    }
}
