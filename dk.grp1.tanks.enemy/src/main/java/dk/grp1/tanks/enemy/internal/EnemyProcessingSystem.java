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

            //shootWithKeys(gameData, cannonPart, enemy);


            physicsPart.processPart(enemy, gameData);
            ctrlPart.processPart(enemy, gameData);
            collisionPart.processPart(enemy, gameData);
            movePart.processPart(enemy, gameData);
            cannonPart.setJointY(positionPart.getY());
            cannonPart.setJointX(positionPart.getX());

            if (gameData.getKeys().isPressed(GameKeys.SHIFT)) {
                aiShoot(gameData, world, cannonPart, enemy);
            }

            cannonPart.processPart(enemy, gameData);



        }
    }

    private void aiShoot(GameData gameData, World world, CannonPart cannonPart, Entity enemy){
        PositionPart enemyPositionPart = enemy.getPart(PositionPart.class);

        for (Entity entity: world.getEntities()) {

            if (entity != enemy && entity.getPart(ControlPart.class) != null){
                PositionPart otherEntityPositionPart = entity.getPart(PositionPart.class);

                //check if other entity is to my left or right
                if (enemyPositionPart.getX() < otherEntityPositionPart.getX()) {
                    cannonPart.setDirection(3.1415f / 4);
                } else {
                    cannonPart.setDirection(3 * 3.1415f / 4);
                }
                System.out.println(cannonPart.getDirection() + " " + enemyPositionPart.getX() + " " +otherEntityPositionPart.getX());

                //TODO fix me
                firepower = initialVelocity(cannonPart, otherEntityPositionPart, 90.82f, cannonPart.getDirection());
                gameData.addEvent(new ShootingEvent(enemy, firepower));
            }
        }
    }

    private float initialVelocity(CannonPart myPosition, PositionPart otherPosition,
                                  float gravity, float angle){
        float velocity;
        float distanceX = Math.abs(myPosition.getMuzzleFaceCentre().getX() - otherPosition.getX());
        float distanceY = otherPosition.getY() - myPosition.getMuzzleFaceCentre().getY() ;
        System.out.println("DistX: " +distanceX + " DistY: " + distanceY);
        velocity = (float) (10*gravity*distanceX/Math.sqrt((100*gravity*distanceX-100*gravity*distanceY)));
        System.out.println("VELOCITY: " + velocity);
        return velocity;
    }

    private void shootWithKeys(GameData gameData, CannonPart cannonPart, Entity enemy){
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
            //firepower = cannonPart.calculateFirepower(gameData);
            //timeSinceLastShot = 0;
            isReadyToShoot = true;
        }

        if (isReadyToShoot && !gameData.getKeys().isDown(GameKeys.SHIFT)) {
            gameData.addEvent(new ShootingEvent(enemy, firepower));
            cannonPart.setFirepower(0);
            //timeSinceLastShot += gameData.getDelta();
            isReadyToShoot = false;
        }
    }
}
