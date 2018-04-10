package dk.grp1.tanks.weapon.grenade.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CollisionPart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.utils.Vector2D;

public class GrenadeCollisionPart extends CollisionPart {

    private Vector2D bouncingVector;
    private Vector2D mapNormalVector;
    private float pi = 3.1415f;

    /**
     * Creates a collision part for an entity
     *
     * @param canCollide
     * @param minTimeBetweenCollision
     */
    public GrenadeCollisionPart(boolean canCollide, float minTimeBetweenCollision) {
        super(canCollide, minTimeBetweenCollision);
        this.bouncingVector = new Vector2D(0, 0);
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {
        PositionPart pos = entity.getPart(PositionPart.class);
        Vector2D mapDirectionVector =  world.getGameMap().getDirectionVector(new Vector2D(pos.getX(), pos.getY()));
        this.mapNormalVector = mapDirectionVector.rotate90degrees();
    }

    public Vector2D getBouncingVector() {
        return this.bouncingVector;
    }

    public void updateBouncingVector(Vector2D vector) {
        //this.bouncingVector = new Vector2D(vector.getX(), vector.getY() * -1 / 2);

        Vector2D d = vector; // quick maths
        Vector2D n = mapNormalVector; // quick maths
        if (n == null){
            n = new Vector2D(0,1);
        }
        float res = Vector2D.dot(d,n) * 2; // quick maths
        n.multiplyWithConstant(res); // quick maths
        d.subtract(n); // quick maths
        d.multiplyWithConstant(0.7f); // slow down boy aka. the bounciness

/*
        float angle = mapNormalVector.getAngle(vector);
        System.out.println(mapNormalVector);
        System.out.println(vector);
        System.out.println(angle);
        //angle = pi - angle;

        Vector2D angleVector = new Vector2D((float)Math.cos(angle), (float)Math.sin(angle));
        System.out.println(angleVector);

        //this.bouncingVector = new Vector2D((vector.getX() + (float)Math.cos(angle)), -(vector.getY() + (float)Math.sin(angle))+0.0f);
        this.bouncingVector = angleVector;*/

        this.bouncingVector = d;
    }

}
