package dk.grp1.tanks.player.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IGamePluginService;


public class PlayerGamePlugin implements IGamePluginService {

    @Override
    public void start(World world, GameData gameData) {
        Entity player = createPlayer(gameData);
        world.addEntity(player);
    }

    private Entity createPlayer(GameData gameData) {
        Player player = new Player();
        return player;
    }

    @Override
    public void stop(World world, GameData gameData) {
        for (Entity player : world.getEntities(Player.class)) {
            world.removeEntity(player);
        }
    }
}
