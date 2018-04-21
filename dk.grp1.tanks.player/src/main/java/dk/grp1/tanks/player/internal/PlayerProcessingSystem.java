package dk.grp1.tanks.player.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameKeys;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.eventManager.events.EndTurnEvent;
import dk.grp1.tanks.common.services.IEntityProcessingService;
import dk.grp1.tanks.common.utils.Vector2D;

/**
 * Created by danie on 12-03-2018.
 */
public class PlayerProcessingSystem implements IEntityProcessingService {

    @Override
    public void process(World world, GameData gameData) {

        for (Entity player : world.getEntities(Player.class)
                ) {

            TurnPart turnPart = player.getPart(TurnPart.class);
            CannonPart cannonPart = player.getPart(CannonPart.class);
            MovementPart movePart = player.getPart(MovementPart.class);
            ControlPart ctrlPart = player.getPart(ControlPart.class);
            PositionPart positionPart = player.getPart(PositionPart.class);
            CollisionPart collisionPart = player.getPart(CollisionPart.class);
            PhysicsPart physicsPart = player.getPart(PhysicsPart.class);
            LifePart lifePart = player.getPart(LifePart.class);
            ShootingPart shootingPart = player.getPart(ShootingPart.class);
            CirclePart circlePart = player.getPart(CirclePart.class);
            InventoryPart inventoryPart = player.getPart(InventoryPart.class);
            inventoryPart.processPart(player, gameData, world);

            if (lifePart.getCurrentHP() <= 0) {
                if (turnPart.isMyTurn()) {
                    gameData.getEventManager().addEvent(new EndTurnEvent(player));
                }
                world.removeEntity(player);
            }


            if (turnPart.isMyTurn()) {
                ctrlPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
                ctrlPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
                ctrlPart.setRotation(world.getGameMap().getDirectionVector(new Vector2D(positionPart.getX(), positionPart.getY())));
            } else {
                ctrlPart.setLeft(false);
                ctrlPart.setRight(false);
                // There is a null pointer Exception here
                ctrlPart.setRotation(world.getGameMap().getDirectionVector(new Vector2D(positionPart.getX(), positionPart.getY())));
            }

            //Cannon movement
            if (turnPart.isMyTurn()) {
                if (gameData.getKeys().isDown(GameKeys.UP)) {
                    cannonPart.setDirection(cannonPart.getDirection() + 0.02f);
                } else if (gameData.getKeys().isDown(GameKeys.DOWN)) {
                    cannonPart.setDirection(cannonPart.getDirection() - 0.02f);
                }
            }

            //Cannon fire cooldown
            if (gameData.getKeys().isDown(GameKeys.SPACE) && turnPart.isMyTurn()) {
                shootingPart.setFirepower(cannonPart.calculateFirepower(gameData));
                //timeSinceLastShot = 0;
                shootingPart.setReadyToShoot(true);
            }

            if (turnPart.isMyTurn()) {
                if (gameData.getKeys().isPressed(GameKeys.N_1)) {
                    inventoryPart.nextWeapon();
                } else if (gameData.getKeys().isPressed(GameKeys.N_2)) {
                    inventoryPart.previousWeapon();
                }
            }

            if (turnPart.isMyTurn()) {
                if (shootingPart.isReadyToShoot() && !gameData.getKeys().isDown(GameKeys.SPACE) && inventoryPart.getCurrentWeapon() != null) {
                    //gameData.getEventManager().addEvent(new SoundEvent(player, inventoryPart.getCurrentWeapon().getShootSoundPath()));
                    inventoryPart.getCurrentWeapon().shoot(player, gameData, shootingPart.getFirepower(), world);
                    //inventoryPart.decreaseAmmo();
                    cannonPart.setFirepower(0);
                    cannonPart.setPreviousFirepower(shootingPart.getFirepower());
                    cannonPart.setPreviousAngle(cannonPart.getDirection());
                    //timeSinceLastShot += gameData.getDelta();
                    shootingPart.setReadyToShoot(false);
                    gameData.getEventManager().addEvent(new EndTurnEvent(player));
                }
            }

            turnPart.processPart(player, gameData, world);
            physicsPart.processPart(player, gameData, world);
            ctrlPart.processPart(player, gameData, world);
            collisionPart.processPart(player, gameData, world);
            movePart.processPart(player, gameData, world);
            cannonPart.setJointY(positionPart.getY() + circlePart.getRadius() / 8 * 3);
            cannonPart.setJointX(positionPart.getX());
            cannonPart.processPart(player, gameData, world);


        }
    }
}
