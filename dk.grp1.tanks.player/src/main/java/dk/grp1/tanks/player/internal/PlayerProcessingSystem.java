package dk.grp1.tanks.player.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameKeys;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.events.ShootingEvent;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.services.IEntityProcessingService;
import dk.grp1.tanks.common.utils.Vector2D;

/**
 * Created by danie on 12-03-2018.
 */
public class PlayerProcessingSystem implements IEntityProcessingService {

    private float timeSinceLastShot;
    float firepower = 0;
    private boolean isReadyToShoot = false;

    @Override
    public void process(World world, GameData gameData) {

        for (Entity player : world.getEntities(Player.class)
                ) {

            CannonPart cannonPart = player.getPart(CannonPart.class);
            MovementPart movePart = player.getPart(MovementPart.class);
            ControlPart ctrlPart = player.getPart(ControlPart.class);
            PositionPart positionPart = player.getPart(PositionPart.class);
            CollisionPart collisionPart = player.getPart(CollisionPart.class);
            PhysicsPart physicsPart = player.getPart(PhysicsPart.class);
            LifePart lifePart = player.getPart(LifePart.class);

            if(lifePart.getCurrentHP() <= 0){
                world.removeEntity(player);
            }


            ctrlPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            ctrlPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
            ctrlPart.setRotation(world.getGameMap().getDirectionVector(new Vector2D(positionPart.getX(),positionPart.getY())));

            //Cannon movement
            if (gameData.getKeys().isDown(GameKeys.UP)){
                cannonPart.setDirection(cannonPart.getDirection()+0.02f);
            } else if (gameData.getKeys().isDown(GameKeys.DOWN)){
                cannonPart.setDirection(cannonPart.getDirection()-0.02f);
            }

            //Cannon fire cooldown
            if (gameData.getKeys().isDown(GameKeys.SPACE)
                    //&& timeSinceLastShot> 1
            ) {
                firepower = cannonPart.calculateFirepower(gameData);
                //timeSinceLastShot = 0;
                isReadyToShoot = true;
            }

            if (isReadyToShoot && !gameData.getKeys().isDown(GameKeys.SPACE)) {
                gameData.addEvent(new ShootingEvent(player, firepower));
                cannonPart.setFirepower(0);
                //timeSinceLastShot += gameData.getDelta();
                isReadyToShoot = false;
            }


            physicsPart.processPart(player, gameData);
            ctrlPart.processPart(player, gameData);
            collisionPart.processPart(player, gameData);
            movePart.processPart(player, gameData);
            cannonPart.setJointY(positionPart.getY());
            cannonPart.setJointX(positionPart.getX());
            cannonPart.processPart(player, gameData);


        }
    }
}
