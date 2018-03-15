package dk.grp1.tanks.player.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.services.IGamePluginService;


public class PlayerGamePlugin implements IGamePluginService {

    private float playerRadius = 10;

    @Override
    public void start(World world, GameData gameData) {
        Entity player = createPlayer(gameData);
        world.addEntity(player);
    }

    private Entity createPlayer(GameData gameData) {
        Player player = new Player();
        float centreX = gameData.getDisplayWidth()/2;
        float centreY = gameData.getDisplayHeight()/2;
        PositionPart positionPart = new PositionPart(centreX,centreY, 0);
        float cannonDirection = 3.1415f/4;
        float cannonWidth = playerRadius/2;
        float cannonLength = playerRadius*2;
        player.add(new CirclePart(centreX, centreY, playerRadius));
        player.add(new PhysicsPart(5000f,-0f));
        player.add(new ControlPart());
        player.add(new LifePart());
        player.add(positionPart);
        player.add(new CannonPart(positionPart.getX(), positionPart.getY(), cannonDirection, cannonWidth, cannonLength));
        player.add(new ShapePart());
        player.add(new MovementPart(50,100,20));
        return player;
    }

    @Override
    public void stop(World world, GameData gameData) {
        for (Entity player : world.getEntities(Player.class)) {
            world.removeEntity(player);
        }
    }
}
