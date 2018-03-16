package dk.grp1.tanks.mapcollision.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameMap;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.CollisionPart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.services.IPostEntityProcessingService;

public class MapCollisionProcessing implements IPostEntityProcessingService {
    @Override
    public void postProcess(World world, GameData gameData) {
        GameMap gameMap = world.getGameMap();

        for (Entity entity : world.getEntities()) {
            CollisionPart collisionPart = entity.getPart(CollisionPart.class);
            if (collisionPart != null && collisionPart.canCollide()) {
                squareMapCollision(gameMap, entity,gameData);
            }
        }

    }

    private void squareMapCollision(GameMap gameMap, Entity entity,GameData gameData) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        CollisionPart collisionPart = entity.getPart(CollisionPart.class);
        CirclePart circlePart = entity.getPart(CirclePart.class);

        if(positionPart != null && collisionPart != null && circlePart != null){
            float y = positionPart.getY() - circlePart.getRadius();
            //Get height of map
            float height = (gameData.getGameHeight()*0.5f)*(float)Math.sin(((double)positionPart.getX())/128);
            if(y <= height){
                collisionPart.setHitGameMap(true);
            } else {
                collisionPart.setHitGameMap(false);
            }
        }




    }
}
