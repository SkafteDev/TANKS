package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;

public class ExpirationPart implements IEntityPart{

    private float remainingLifeTime;

    @Override
    public void processPart(Entity entity, GameData gameData) {

    }


    /**
     * returns the remaining time until expiration
     * @return
     */
    public float getRemainingLifeTime() {
        return remainingLifeTime;
    }

    /**
     * set the remaining time until expiration
     * Prevents it from falling below 0
     * @param remainingLifeTime
     */
    public void setRemainingLifeTime(float remainingLifeTime) {
        this.remainingLifeTime = remainingLifeTime;
        if (this.remainingLifeTime < 0){
            this.remainingLifeTime = 0;
        }
    }
}
