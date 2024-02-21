package dk.grp1.tanks.weapon.holysheep.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.CollisionPart;
import dk.grp1.tanks.common.data.parts.ControlPart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.utils.Vector2D;

public class HolySheepCollisionPart extends CollisionPart {


    /**
     * Creates a collision part for an entity
     *
     * @param canCollide
     * @param minTimeBetweenCollision
     */
    public HolySheepCollisionPart(boolean canCollide, float minTimeBetweenCollision) {
        super(canCollide, minTimeBetweenCollision);
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        ControlPart controlPart = entity.getPart(ControlPart.class);
        controlPart.setRotation(world.getGameMap().getDirectionVector(new Vector2D(positionPart.getX(), positionPart.getY())));

        CirclePart circlePart = entity.getPart(CirclePart.class);
        if(world.getGameMap().getHeight(new Vector2D(positionPart.getX(),positionPart.getY()))-2f > positionPart.getY()-circlePart.getRadius()) {
            positionPart.setY(positionPart.getY() + 3f);
        }
    }
}
