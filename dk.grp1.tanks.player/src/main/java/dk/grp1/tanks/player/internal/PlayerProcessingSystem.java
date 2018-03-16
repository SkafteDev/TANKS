package dk.grp1.tanks.player.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameKeys;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CollisionPart;
import dk.grp1.tanks.common.data.parts.ControlPart;
import dk.grp1.tanks.common.data.parts.MovementPart;
import dk.grp1.tanks.common.data.parts.PhysicsPart;
import dk.grp1.tanks.common.events.ShootingEvent;
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

            MovementPart movePart = player.getPart(MovementPart.class);
            ControlPart ctrlPart = player.getPart(ControlPart.class);
            CollisionPart collisionPart = player.getPart(CollisionPart.class);
            PhysicsPart physicsPart = player.getPart(PhysicsPart.class);


            ctrlPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            ctrlPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));

            if (gameData.getKeys().isPressed(GameKeys.SPACE) && timeSinceLastShot
                    > 1) {
                gameData.addEvent(new ShootingEvent(player));
                timeSinceLastShot = 0;
            }
            timeSinceLastShot += gameData.getDelta();

            ctrlPart.processPart(player,gameData);
            physicsPart.processPart(player,gameData);
            collisionPart.processPart(player,gameData);
            movePart.processPart(player, gameData);


        }
    }
}
