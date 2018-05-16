package dk.grp1.tanks.mapcollision.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameMap;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.CollisionPart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.services.IPostEntityProcessingService;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;

public class MapCollisionProcessing implements IPostEntityProcessingService {
    @Override
    public void postProcess(World world, GameData gameData) {
        if(world == null || gameData == null){
            throw new IllegalArgumentException("World or gamedata is null");
        }
        GameMap gameMap = world.getGameMap();
        if(gameMap == null){
            throw new IllegalStateException("GameMap does not exist");
        }
        for (Entity entity : world.getEntities()) {
            CollisionPart collisionPart = entity.getPart(CollisionPart.class);
            if (collisionPart != null && collisionPart.canCollide()) {
                circleThreePointMapCollision(gameMap, entity);
            }
        }

    }

    /**
     * Checks for map collision on several points
     * @param gameMap
     * @param entity
     */
    private void circleThreePointMapCollision(GameMap gameMap, Entity entity) {
        CollisionPart collisionPart = entity.getPart(CollisionPart.class);
        CirclePart circlePart = entity.getPart(CirclePart.class);

        if (collisionPart != null && circlePart != null) {

            float x = circlePart.getCentreX();
            float y = circlePart.getCentreY();
            float radius = circlePart.getRadius();
            float gameMapHeight = gameMap.getHeight(new Vector2D(circlePart.getCentreX(),circlePart.getCentreY()));

            ArrayList<Float> xCordinates = new ArrayList<>();
            xCordinates.add(x);

            for (int i = 1; i < 5; i++) {
                xCordinates.add(-1 * (i * radius / 5)+x);
                xCordinates.add((i * radius / 5)+x);
            }

            for (float c : xCordinates) {
                float checkY = calculateYCordinate(c, radius,x,y);
                if (checkY <= gameMapHeight) {
                    collisionPart.setHitGameMap(true);
                    return;
                }
            }

            collisionPart.setHitGameMap(false);
        }
    }

    /**
     * Calculates an y coordinate on a negative half circle
     * @param xValue
     * @param radius
     * @param centerX
     * @param centerY
     * @return
     */
    private float calculateYCordinate(float xValue, float radius,float centerX, float centerY) {
        float toSqrt = (-(centerX*centerX)+ 2 * xValue * centerX + (radius*radius) - (xValue*xValue));
        return (float) (centerY - Math.sqrt(toSqrt));
    }
}
