package dk.grp1.tanks.EntityCollision.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.CollisionPart;
import dk.grp1.tanks.common.services.IPostEntityProcessingService;

public class EntityCollisionPostProcessingSystem implements IPostEntityProcessingService {

    @Override
    public void postProcess(World world, GameData gameData) {
    if(world == null) {
        return;
    }
        for (Entity entity1: world.getEntities()) {
            for (Entity entity2: world.getEntities()) {
                if (!entity1.equals(entity2)){
                    if (hasCollided(entity1, entity2)){
                        CollisionPart collisionPart1 = entity1.getPart(CollisionPart.class);
                        CollisionPart collisionPart2 = entity2.getPart(CollisionPart.class);
                        collisionPart1.setHitEntity(true);
                        collisionPart2.setHitEntity(true);
                    }
                }
            }
        }
    }


    private boolean hasCollided(Entity entity1, Entity entity2){
        CirclePart circlePart1 = entity1.getPart(CirclePart.class);
        CirclePart circlePart2 = entity2.getPart(CirclePart.class);

        //no collision if no circle part
        if (circlePart1 == null || circlePart2 == null || entity1.getClass().equals(entity2.getClass())){
            return false;
        }

        float distX = circlePart1.getCentreX() - circlePart2.getCentreX();
        float distY = circlePart1.getCentreY() - circlePart2.getCentreY();
        float distance = (float)(Math.sqrt(distX*distX + distY*distY));
        return distance < (circlePart1.getRadius() + circlePart2.getRadius());
    }
}
