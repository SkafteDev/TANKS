package dk.grp1.tanks.player.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.services.IGamePluginService;
import javafx.geometry.Pos;


public class PlayerGamePlugin implements IGamePluginService {

    private float playerRadius = 10f;

    @Override
    public void start(World world, GameData gameData) {
        Entity player = createPlayer(gameData);
        world.addEntity(player);
    }

    private Entity createPlayer(GameData gameData) {
        Player player = new Player();
        float centreX = gameData.getGameWidth()/4f * 3f;
        float centreY = gameData.getGameHeight();
        PositionPart positionPart = new PositionPart(centreX,centreY, 0);
        float cannonDirection = 3.1415f/2;
        float cannonWidth = (playerRadius/4);
        float cannonLength = (playerRadius/2)*3;
        player.add(new CirclePart(centreX, centreY, playerRadius));
        player.add(new PhysicsPart(5000f,-62f));
        player.add(new ControlPart(200));
        LifePart lifePart = new LifePart();
        lifePart.setMaxHP(5);
        lifePart.setCurrentHP(5);
        player.add(lifePart);
        player.add(positionPart);
        player.add(new CannonPart(positionPart.getX(), positionPart.getY()+(playerRadius/2), cannonDirection, cannonWidth, cannonLength, "playerCanon.png"));
        player.add(new ShapePart());
        player.add(new CollisionPart(true,0));
        player.add(new MovementPart(50));
        player.add(new TexturePart("player.png"));
        player.add(new TurnPart());

        InventoryPart inventoryPart = new InventoryPart(gameData.getWeapons());
        gameData.addWeaponListener(inventoryPart);
        player.add(inventoryPart);
        return player;
    }

    @Override
    public void stop(World world, GameData gameData) {
        for (Entity player : world.getEntities(Player.class)) {
            world.removeEntity(player);
        }
    }
}
