package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.utils.Vector2D;

public class PhysicsPart implements IEntityPart {
    private float mass;
    private float gravity;
    private Vector2D gravityVector;

    /**
     * Instantiate a PhysicsPart
     *
     * @param mass    The mass in kilograms
     * @param gravity The gravity in m/s^2
     */
    public PhysicsPart(float mass, float gravity) {
        this.mass = mass;
        this.gravity = gravity;
        this.gravityVector = new Vector2D(0, 0);

    }

    @Override
    public void processPart(Entity entity, GameData gameData) {
        this.gravityVector = new Vector2D(0, getGravity() * gameData.getDelta());

    }

    /**
     * Get the mass in kilograms
     *
     * @return
     */
    public float getMass() {
        return mass;
    }

    /**
     * Set the mass in kilograms
     *
     * @param mass
     */
    public void setMass(float mass) {
        this.mass = mass;
    }

    /**
     * Get the gravity
     *
     * @return
     */
    public float getGravity() {
        return gravity;
    }

    /**
     * Set the gravity
     *
     * @param gravity
     */
    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public Vector2D getGravityVector() {
        return this.gravityVector;
    }
}
