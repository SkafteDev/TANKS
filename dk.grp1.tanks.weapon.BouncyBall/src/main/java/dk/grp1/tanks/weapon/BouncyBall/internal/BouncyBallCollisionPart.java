package dk.grp1.tanks.weapon.BouncyBall.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CollisionPart;
import dk.grp1.tanks.common.data.parts.DamagePart;
import dk.grp1.tanks.common.data.parts.ExplosionTexturePart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.eventManager.events.Event;
import dk.grp1.tanks.common.eventManager.events.ExplosionAnimationEvent;
import dk.grp1.tanks.common.eventManager.events.ExplosionEvent;
import dk.grp1.tanks.common.eventManager.events.MapDestructionEvent;
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

        DamagePart damagePart = entity.getPart(DamagePart.class);
        ExplosionTexturePart explosionTexturePart = entity.getPart(ExplosionTexturePart.class);
        PositionPart positionPart = entity.getPart(PositionPart.class);

        if ((this.isHitGameMap()) && positionPart != null && damagePart != null && explosionTexturePart != null) {
            Event explosionEvent = new ExplosionEvent(entity, new Vector2D(positionPart.getX(), positionPart.getY()), damagePart.getExplosionRadius());
            Event mapDestructionEvent = new MapDestructionEvent(entity,new Vector2D(positionPart.getX(),positionPart.getY()),damagePart.getExplosionRadius());
            Event animationEvent = new ExplosionAnimationEvent(entity,new Vector2D(positionPart.getX(),positionPart.getY()),explosionTexturePart,damagePart.getExplosionRadius());

            gameData.getEventManager().addEvent(explosionEvent);
            gameData.getEventManager().addEvent(animationEvent);
            gameData.getEventManager().addEvent(mapDestructionEvent);

        }


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
