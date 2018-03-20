package dk.grp1.tanks.enemy.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.services.IGamePluginService;

/**
 * Created by danie on 12-03-2018.
 */
public class EnemyGamePlugin implements IGamePluginService {
    private float enemyRadius = 5f;

    @Override
    public void start(World world, GameData gameData) {
        Entity enemy = createEnemy(gameData);
        world.addEntity(enemy);
    }

    private Entity createEnemy(GameData gameData) {
        Enemy enemy = new Enemy();
        float centreX = gameData.getGameWidth() * 0.8f;
        float centreY = gameData.getGameHeight();
        PositionPart positionPart = new PositionPart(centreX,centreY, 0);
        float cannonDirection = 3.1415f/2;
        float cannonWidth = enemyRadius /2;
        float cannonLength = enemyRadius *2;
        enemy.add(new CirclePart(centreX, centreY, enemyRadius));
        enemy.add(new PhysicsPart(5000f,-62f));
        enemy.add(new ControlPart(200));
        enemy.add(new LifePart());
        enemy.add(positionPart);
        enemy.add(new CannonPart(positionPart.getX(), positionPart.getY(), cannonDirection, cannonWidth, cannonLength));
        enemy.add(new ShapePart());
        enemy.add(new CollisionPart(true,0));
        enemy.add(new MovementPart(50));
        enemy.add(new TexturePart("enemy.png"));
        return enemy;
    }

    @Override
    public void stop(World world, GameData gameData) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            world.removeEntity(enemy);
        }
    }
}
