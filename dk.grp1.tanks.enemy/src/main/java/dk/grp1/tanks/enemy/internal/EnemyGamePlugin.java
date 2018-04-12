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
    private float enemyRadius = 10f;
    private final int AMOUNTOFENEMIES = 2;
    @Override
    public void start(World world, GameData gameData) {
        for (int i = 0; i < AMOUNTOFENEMIES; i++) {
            world.addEntity(createEnemy(gameData));
        }
    }

    private Entity createEnemy(GameData gameData) {
        Enemy enemy = new Enemy();
        float centreX = (float) (Math.random() * (gameData.getGameWidth()*0.8+gameData.getGameWidth()*0.1));
        float centreY = gameData.getGameHeight();
        PositionPart positionPart = new PositionPart(centreX,centreY, 0);
        float cannonDirection = 3.1415f/2;
        float cannonWidth = (enemyRadius/4);
        float cannonLength = (enemyRadius/2)*3;
        enemy.add(new CirclePart(centreX, centreY, enemyRadius));
        enemy.add(new PhysicsPart(5f,-62f));
        enemy.add(new ControlPart(50));
        LifePart lifePart = new LifePart();
        lifePart.setMaxHP(100);
        lifePart.setCurrentHP(100);
        enemy.add(lifePart);
        enemy.add(positionPart);
        enemy.add(new CannonPart(positionPart.getX(), positionPart.getY(), cannonDirection, cannonWidth, cannonLength, "enemyCanon.png"));
        enemy.add(new ShapePart());
        enemy.add(new CollisionPart(true,0));
        enemy.add(new MovementPart(500));
        enemy.add(new TexturePart("enemy.png"));
        enemy.add(new TurnPart());

        InventoryPart inventoryPart = new InventoryPart(gameData.getWeapons());
        gameData.addWeaponListener(inventoryPart);
        enemy.add(inventoryPart);
        return enemy;
    }

    @Override
    public void stop(World world, GameData gameData) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            world.removeEntity(enemy);
        }
    }
}
