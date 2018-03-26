package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;

/**
 * Created by danie on 12-03-2018.
 */
public class LifePart implements IEntityPart {

    private float maxHP;
    private float currentHP;

    public void processPart(Entity entity, GameData gameData) {

    }

    /**
     * Returns ratio between entity's current and max hp
     * @return
     */
    public float getHealthRatio(){
        return this.currentHP / this.maxHP;
    }

    /**
     * Returns the entity's maximum HP
     * @return maxHP
     */
    public float getMaxHP() {
        return maxHP;
    }

    /**
     * sets the entity's maximum HP
     * @param maxHP
     */
    public void setMaxHP(float maxHP) {
        this.maxHP = maxHP;
    }

    /**
     * returns the entity's current HP
     * @return currentHP
     */
    public float getCurrentHP() {
        return currentHP;
    }

    /**
     * sets the entity's current HP to the given value
     * @param currentHP
     */
    public void setCurrentHP(float currentHP) {
        this.currentHP = currentHP;
    }

    /**
     * Adds the given amount to the entity's current HP
     * Also ensures that the currents HP doesn't exceed the maximum
     * @param change
     */
    public void addHP(float change){

        if (currentHP + change <= maxHP){
            this.currentHP += change;
        } else {
            this.currentHP = maxHP;
        }
    }

    /**
     * Removes the given amount from the entity's current HP
     * Ensures that the current HP doesn't fall below 0.
     * @param change
     */
    public void removeHP(float change){
        if (this.currentHP - change >= 0){
            this.currentHP -= change;
        } else {
            this.currentHP = 0;
        }

    }

}
