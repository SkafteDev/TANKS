package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;

public class PhysicsPart implements IEntityPart
{
    private float mass;
    private float gravity;

    /**
     * Instantiate a PhysicsPart
     * @param mass The mass in kilograms
     * @param gravity The gravity in m/s^2
     */
    public PhysicsPart(float mass, float gravity) {
        this.mass = mass;
        this.gravity = gravity;
    }

    @Override
    public void processPart(Entity entity, GameData gameData) {

    }

    /**
     * Get the mass in kilograms
     * @return
     */
    public float getMass() {
        return mass;
    }

    /**
     * Set the mass in kilograms
     * @param mass
     */
    public void setMass(float mass) {
        this.mass = mass;
    }

    /**
     * Get the gravity
     * @return
     */
    public float getGravity() {
        return gravity;
    }

    /**
     * Set the gravity
     * @param gravity
     */
    public void setGravity(float gravity) {
        this.gravity = gravity;
    }
}
