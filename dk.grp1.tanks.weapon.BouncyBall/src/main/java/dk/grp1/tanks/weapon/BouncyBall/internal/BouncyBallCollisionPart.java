package dk.grp1.tanks.weapon.BouncyBall.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CollisionPart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.utils.Vector2D;

public class BouncyBallCollisionPart extends CollisionPart {
    private Vector2D bouncingVector;
    private Vector2D mapNormalVector;
    private float pi = 3.1415f;


    /**
     * Creates a collision part for an entity
     *
     * @param canCollide
     * @param minTimeBetweenCollision
     */
    public BouncyBallCollisionPart(boolean canCollide, float minTimeBetweenCollision) {
        super(canCollide, minTimeBetweenCollision);
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {
        PositionPart pos = entity.getPart(PositionPart.class);
        Vector2D mapDirectionVector = world.getGameMap().getDirectionVector(new Vector2D(pos.getX(), pos.getY()));
        this.mapNormalVector = mapDirectionVector.rotate90degrees();
    }

    public Vector2D getBouncingVector() {
        return this.bouncingVector;
    }

    public void updateBouncingVector(Vector2D vector) {
        Vector2D n = mapNormalVector; // quick maths
        float res = Vector2D.dot(vector, n) * 2; // quick maths
        n.multiplyWithConstant(res); // quick maths
        vector.subtract(n); // quick maths
        vector.multiplyWithConstant(0.85f); //Quicker maths
        this.bouncingVector = vector; //slow maths
    }
}
