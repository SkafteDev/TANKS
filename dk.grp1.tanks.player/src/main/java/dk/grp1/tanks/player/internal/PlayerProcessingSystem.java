package dk.grp1.tanks.player.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameKeys;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.events.ShootingEvent;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.services.IEntityProcessingService;

/**
 * Created by danie on 12-03-2018.
 */
public class PlayerProcessingSystem implements IEntityProcessingService {

    private float timeSinceLastShot;

    @Override
    public void process(World world, GameData gameData) {

        for (Entity player : world.getEntities(Player.class)
                ) {

            CannonPart cannonPart = player.getPart(CannonPart.class);
            MovementPart movePart =  player.getPart(MovementPart.class);
            ControlPart ctrlPart = player.getPart(ControlPart.class);
            PositionPart positionPart = player.getPart(PositionPart.class);
            CollisionPart collisionPart = player.getPart(CollisionPart.class);
            PhysicsPart physicsPart = player.getPart(PhysicsPart.class);


            ctrlPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            ctrlPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
            ctrlPart.setRotation(world.getGameMap().getDirectionVector(positionPart.getX()));

            if (gameData.getKeys().isPressed(GameKeys.SPACE) && timeSinceLastShot
                    > 1) {
                gameData.addEvent(new ShootingEvent(player));
                timeSinceLastShot = 0;
            }
            timeSinceLastShot += gameData.getDelta();

            physicsPart.processPart(player,gameData);
            ctrlPart.processPart(player,gameData);
            collisionPart.processPart(player,gameData);
            movePart.processPart(player, gameData);
            cannonPart.setJointY(positionPart.getY());
            cannonPart.setJointX(positionPart.getX());
            cannonPart.processPart(player, gameData);


        }
    }
}
