package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;

/**
 * Created by danie on 12-03-2018.
 */
public class LifePart implements IEntityPart {

    private float maxHP;
    private float currentHP;
    private float remainingLifeTime;

    public void processPart(Entity entity, GameData gameData) {

    }

    public float getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(float maxHP) {
        this.maxHP = maxHP;
    }

    public float getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(float currentHP) {
        this.currentHP = currentHP;
    }

    public float getRemainingLifeTime() {
        return remainingLifeTime;
    }

    public void setRemainingLifeTime(float remainingLifeTime) {
        this.remainingLifeTime = remainingLifeTime;
    }
}
