package dk.grp1.tanks.enemy.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameKeys;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.events.ShootingEvent;
import dk.grp1.tanks.common.services.IEntityProcessingService;

public class EnemyProcessingSystem implements IEntityProcessingService {

    private float timeSinceLastShot;
    float firepower = 0;
    private boolean isReadyToShoot = false;

    private boolean randomMovement(){
        if (Math.random() > 0.5){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void process(World world, GameData gameData) {

        for (Entity enemy : world.getEntities(Enemy.class)
                ) {

            CannonPart cannonPart = enemy.getPart(CannonPart.class);
            MovementPart movePart = enemy.getPart(MovementPart.class);
            ControlPart ctrlPart = enemy.getPart(ControlPart.class);
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            CollisionPart collisionPart = enemy.getPart(CollisionPart.class);
            PhysicsPart physicsPart = enemy.getPart(PhysicsPart.class);


            ctrlPart.setLeft(gameData.getKeys().isDown(GameKeys.A));
            ctrlPart.setRight(gameData.getKeys().isDown(GameKeys.D));
            ctrlPart.setRotation(world.getGameMap().getDirectionVector(positionPart.getX()));

            //Cannon movement
            if (gameData.getKeys().isDown(GameKeys.W)) {
           // boolean move = randomMovement();
           // if (move) {
                cannonPart.setDirection(cannonPart.getDirection() + 0.02f);
                 } else if (gameData.getKeys().isDown(GameKeys.S)) {
           // } else {
                cannonPart.setDirection(cannonPart.getDirection() - 0.02f);
            }

            //Cannon fire cooldown
            if (gameData.getKeys().isDown(GameKeys.SHIFT)
                //&& timeSinceLastShot> 1
                    ) {
                firepower = cannonPart.calculateFirepower(gameData);
                //timeSinceLastShot = 0;
                isReadyToShoot = true;
            }

            if (isReadyToShoot && !gameData.getKeys().isDown(GameKeys.SHIFT)) {
                gameData.addEvent(new ShootingEvent(enemy, firepower));
                cannonPart.setFirepower(0);
                //timeSinceLastShot += gameData.getDelta();
                isReadyToShoot = false;
            }

            physicsPart.processPart(enemy, gameData);
            ctrlPart.processPart(enemy, gameData);
            collisionPart.processPart(enemy, gameData);
            movePart.processPart(enemy, gameData);
            cannonPart.setJointY(positionPart.getY());
            cannonPart.setJointX(positionPart.getX());
            cannonPart.processPart(enemy, gameData);

        }
    }
}
