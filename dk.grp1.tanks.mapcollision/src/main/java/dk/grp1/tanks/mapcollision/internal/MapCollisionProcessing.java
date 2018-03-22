package dk.grp1.tanks.mapcollision.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameMap;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.CollisionPart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.services.IPostEntityProcessingService;

import java.util.ArrayList;

public class MapCollisionProcessing implements IPostEntityProcessingService {
    @Override
    public void postProcess(World world, GameData gameData) {
        GameMap gameMap = world.getGameMap();

        for (Entity entity : world.getEntities()) {
            CollisionPart collisionPart = entity.getPart(CollisionPart.class);
            if (collisionPart != null && collisionPart.canCollide()) {
                //squareMapCollision(gameMap, entity, gameData);
                circleThreePointMapCollision(gameMap, entity);
            }
        }

    }

    private void squareMapCollision(GameMap gameMap, Entity entity, GameData gameData) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        CollisionPart collisionPart = entity.getPart(CollisionPart.class);
        CirclePart circlePart = entity.getPart(CirclePart.class);

        if (positionPart != null && collisionPart != null && circlePart != null) {
            float y = positionPart.getY() - circlePart.getRadius();
            //Get height of map
            float height = gameMap.getHeight(positionPart.getX());
            if (y <= height) {
                collisionPart.setHitGameMap(true);
            } else {
                collisionPart.setHitGameMap(false);
            }
        }


    }

    private void circleThreePointMapCollision(GameMap gameMap, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        CollisionPart collisionPart = entity.getPart(CollisionPart.class);
        CirclePart circlePart = entity.getPart(CirclePart.class);

        if (positionPart != null && collisionPart != null && circlePart != null) {

            float x = positionPart.getX();
            float y = positionPart.getY();
            float radius = circlePart.getRadius();
            float gameMapHeight = gameMap.getHeight(positionPart.getX());

            ArrayList<Float> xCordinates = new ArrayList<>();
            xCordinates.add(0f);

            for (int i = 1; i < 5; i++) {
                xCordinates.add(-1 * (i * radius / 5));
                xCordinates.add((i * radius / 5));
            }

            for (float c : xCordinates) {
                float checkY = calculateYCordinate(c, radius) + y;
                if (checkY <= gameMapHeight) {
                    collisionPart.setHitGameMap(true);
                    return;
                }
            }

            collisionPart.setHitGameMap(false);
        }
    }

    private float calculateYCordinate(float x, float radius) {
        return (float) (-1 * Math.sqrt(-1 * Math.pow(x, 2) + Math.pow(radius, 2)));
    }
}
