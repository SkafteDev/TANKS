package dk.grp1.tanks.enemy.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameKeys;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.services.IEntityProcessingService;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.Random;

public class EnemyProcessingSystem implements IEntityProcessingService {

    private final boolean AICONTROLLED = true;
    float firepower = 0;
    private float timeSinceLastShot;
    private boolean isReadyToShoot = false;

    private boolean randomMovement() {
        if (Math.random() > 0.5) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void process(World world, GameData gameData) {


        for (Entity enemy : world.getEntities(Enemy.class)
                ) {
            TurnPart turnPart = enemy.getPart(TurnPart.class);
            CannonPart cannonPart = enemy.getPart(CannonPart.class);
            MovementPart movePart = enemy.getPart(MovementPart.class);
            ControlPart ctrlPart = enemy.getPart(ControlPart.class);
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            CollisionPart collisionPart = enemy.getPart(CollisionPart.class);
            PhysicsPart physicsPart = enemy.getPart(PhysicsPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);
            CirclePart circlePart = enemy.getPart(CirclePart.class);
            InventoryPart inventoryPart = enemy.getPart(InventoryPart.class);
            inventoryPart.processPart(enemy, gameData, world);

            if (lifePart.getCurrentHP() <= 0) {
                world.removeEntity(enemy);
            }

            if (turnPart.isMyTurn()) {
                if (!AICONTROLLED) {
                    manualControl(ctrlPart, gameData, world, positionPart);
                } else {
                    simpleAIControl(ctrlPart, movePart, world, positionPart);
                }


            } else {
                ctrlPart.setLeft(false);
                ctrlPart.setRight(false);
                ctrlPart.setRotation(world.getGameMap().getDirectionVector(new Vector2D(positionPart.getX(), positionPart.getY())));
            }


            physicsPart.processPart(enemy, gameData, world);
            ctrlPart.processPart(enemy, gameData, world);
            collisionPart.processPart(enemy, gameData, world);
            movePart.processPart(enemy, gameData, world);
            cannonPart.setJointY(positionPart.getY() + circlePart.getRadius() / 8 * 3);
            cannonPart.setJointX(positionPart.getX());
            cannonPart.processPart(enemy, gameData, world);


            if (!AICONTROLLED) {
                manualShoot(gameData, turnPart, world, cannonPart, enemy);
            } else {
                simpleAIShoot(gameData, turnPart, world, cannonPart, enemy);
            }

            turnPart.processPart(enemy, gameData, world);


        }
    }

    private void simpleAIShoot(GameData gameData, TurnPart turnPart, World world, CannonPart cannonPart, Entity enemy) {
        if (turnPart.isMyTurn()) {
            MovementPart movementPart = enemy.getPart(MovementPart.class);
            if (!(movementPart.getCurrentSpeed() > 0)) {
                shootLessThanPerfectShot(gameData, world, cannonPart, enemy);
                turnPart.endMyTurn();
            }
        }

    }

    private void manualShoot(GameData gameData, TurnPart turnPart, World world, CannonPart cannonPart, Entity enemy) {
        if (gameData.getKeys().isPressed(GameKeys.SHIFT) && turnPart.isMyTurn()) {
            shootPerfectShot(gameData, world, cannonPart, enemy);
            turnPart.endMyTurn();
        }
    }

    private void manualControl(ControlPart ctrlPart, GameData gameData, World world, PositionPart positionPart) {
        ctrlPart.setLeft(gameData.getKeys().isDown(GameKeys.A));
        ctrlPart.setRight(gameData.getKeys().isDown(GameKeys.D));
        ctrlPart.setRotation(world.getGameMap().getDirectionVector(new Vector2D(positionPart.getX(), positionPart.getY())));
    }

    private void simpleAIControl(ControlPart ctrlPart, MovementPart movementPart, World world, PositionPart positionPart) {
        Random random = new Random();
        boolean moving = movementPart.getCurrentSpeed() > 0;

        boolean goRight = random.nextBoolean();
        boolean move = (random.nextDouble() < 0.99);

        if (move) {
            if(!moving){
                ctrlPart.setLeft(!goRight);
                ctrlPart.setRight(goRight);
            }

            ctrlPart.setRotation(world.getGameMap().getDirectionVector(new Vector2D(positionPart.getX(), positionPart.getY())));

        } else {
            ctrlPart.setLeft(false);
            ctrlPart.setRight(false);
            ctrlPart.setRotation(world.getGameMap().getDirectionVector(new Vector2D(positionPart.getX(), positionPart.getY())));

        }
    }


    private void shootPerfectShot(GameData gameData, World world, CannonPart cannonPart, Entity enemy) {

        InventoryPart inventoryPart = enemy.getPart(InventoryPart.class);
        inventoryPart.processPart(enemy, gameData, world);


        for (Entity entity : world.getEntities()) {

            if (entity != enemy && entity.getPart(ControlPart.class) != null && inventoryPart.getCurrentWeapon() != null) {
                PositionPart otherEntityPositionPart = entity.getPart(PositionPart.class);


                setCannonAngle(cannonPart, enemy, otherEntityPositionPart);
                cannonPart.processPart(enemy, gameData, world);

                firepower = initialVelocity(cannonPart, otherEntityPositionPart, 90.82f, cannonPart.getDirection());

                inventoryPart.getCurrentWeapon().shoot(enemy, firepower, world);
                cannonPart.setPreviousFirepower(firepower);
                cannonPart.setPreviousAngle(cannonPart.getDirection());
                //gameData.addEvent(new ShootingEvent(enemy, firepower));
                return;
            }
        }
    }

    private void shootLessThanPerfectShot(GameData gameData, World world, CannonPart cannonPart, Entity enemy) {

        InventoryPart inventoryPart = enemy.getPart(InventoryPart.class);
        inventoryPart.processPart(enemy, gameData, world);
        Random wepRandom = new Random();
        int i = wepRandom.nextInt(inventoryPart.getWeapons().size());
        for (; i > 0; i--) {
            inventoryPart.nextWeapon();
        }

        for (Entity entity : world.getEntities()) {

            if (entity != enemy && entity.getPart(ControlPart.class) != null && inventoryPart.getCurrentWeapon() != null) {
                PositionPart otherEntityPositionPart = entity.getPart(PositionPart.class);


                setCannonAngle(cannonPart, enemy, otherEntityPositionPart);
                cannonPart.processPart(enemy, gameData, world);

                firepower = initialVelocity(cannonPart, otherEntityPositionPart, 90.82f, cannonPart.getDirection());
                Random random = new Random();
                float modification1 = random.nextFloat() / 10f;
                float modification2 = random.nextFloat() / 10f;
                firepower = firepower * (1 + modification1) * (1 - modification2);


                inventoryPart.getCurrentWeapon().shoot(enemy, firepower, world);
                cannonPart.setPreviousFirepower(firepower);
                cannonPart.setPreviousAngle(cannonPart.getDirection());
                //gameData.addEvent(new ShootingEvent(enemy, firepower));
                return;
            }
        }
    }

    /**
     * Sets the cannon o match the direction of the target, while maintaining a 45 degree angle
     *
     * @param cannonPart
     * @param enemy
     * @param otherEntityPositionPart
     */
    private void setCannonAngle(CannonPart cannonPart, Entity enemy, PositionPart otherEntityPositionPart) {
        //check if other entity is to my left or right
        PositionPart enemyPositionPart = enemy.getPart(PositionPart.class);
        if (enemyPositionPart.getX() < otherEntityPositionPart.getX()) {
            cannonPart.setDirection(3.1415f / 4);
        } else {
            cannonPart.setDirection(3 * 3.1415f / 4);
        }
    }

    private float initialVelocity(CannonPart myPosition, PositionPart otherPosition,
                                  float gravity, float angle) {
        float velocity;
        float distanceX = Math.abs(myPosition.getMuzzleFaceCentre().getX() - otherPosition.getX());
        float distanceY = otherPosition.getY() - myPosition.getMuzzleFaceCentre().getY();
        velocity = (float) (10 * gravity * distanceX / Math.sqrt((100 * gravity * distanceX - 100 * gravity * distanceY)));
        return velocity;
    }


}
