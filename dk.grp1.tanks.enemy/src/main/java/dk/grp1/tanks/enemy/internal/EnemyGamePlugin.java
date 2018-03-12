package dk.grp1.tanks.enemy.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IGamePluginService;

/**
 * Created by danie on 12-03-2018.
 */
public class EnemyGamePlugin implements IGamePluginService {

    @Override
    public void start(World world, GameData gameData) {
        Entity enemy = createEnemy(gameData);
        world.addEntity(enemy);
    }

    private Entity createEnemy(GameData gameData) {
        Enemy enemy = new Enemy();
        return enemy;
    }

    @Override
    public void stop(World world, GameData gameData) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            world.removeEntity(enemy);
        }
    }
}
