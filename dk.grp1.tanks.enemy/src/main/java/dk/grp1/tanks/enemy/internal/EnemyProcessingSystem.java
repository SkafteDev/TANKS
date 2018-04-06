package dk.grp1.tanks.enemy.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameKeys;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.events.ShootingEvent;
import dk.grp1.tanks.common.services.IEntityProcessingService;
import dk.grp1.tanks.common.utils.Vector2D;

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

            if(lifePart.getCurrentHP() <= 0){
                world.removeEntity(enemy);
            }

            if(turnPart.isMyTurn()) {

                ctrlPart.setLeft(gameData.getKeys().isDown(GameKeys.A));
                ctrlPart.setRight(gameData.getKeys().isDown(GameKeys.D));
                ctrlPart.setRotation(world.getGameMap().getDirectionVector(new Vector2D(positionPart.getX(), positionPart.getY())));

            } else{
                ctrlPart.setLeft(false);
                ctrlPart.setRight(false);
                ctrlPart.setRotation(world.getGameMap().getDirectionVector(new Vector2D(positionPart.getX(), positionPart.getY())));
            }

            //shootWithKeys(gameData, cannonPart, enemy);

            turnPart.processPart(enemy,gameData,world);
            physicsPart.processPart(enemy, gameData, world);
            ctrlPart.processPart(enemy, gameData, world);
            collisionPart.processPart(enemy, gameData, world);
            movePart.processPart(enemy, gameData, world);
            cannonPart.setJointY(positionPart.getY() + circlePart.getRadius()/8*3);
            cannonPart.setJointX(positionPart.getX());
            cannonPart.processPart(enemy, gameData, world);


                if (gameData.getKeys().isPressed(GameKeys.SHIFT) && turnPart.isMyTurn()) {
                    aiShoot(gameData, world, cannonPart, enemy);
                    turnPart.endMyTurn();

                }

        }
    }

    private void aiShoot(GameData gameData, World world, CannonPart cannonPart, Entity enemy){

        InventoryPart inventoryPart = enemy.getPart(InventoryPart.class);
        inventoryPart.processPart(enemy, gameData, world);


        for (Entity entity: world.getEntities()) {

            if (entity != enemy && entity.getPart(ControlPart.class) != null){
                PositionPart otherEntityPositionPart = entity.getPart(PositionPart.class);


                setCannonAngle(cannonPart, enemy, otherEntityPositionPart);
                cannonPart.processPart(enemy, gameData, world);

                firepower = initialVelocity(cannonPart, otherEntityPositionPart, 90.82f, cannonPart.getDirection());

                inventoryPart.getCurrentWeapon().shoot(enemy, firepower, world);
                cannonPart.setPreviousFirepower(firepower);
                cannonPart.setPreviousAngle(cannonPart.getDirection());
                //gameData.addEvent(new ShootingEvent(enemy, firepower));
            }
        }
    }

    /**
     * Sets the cannon o match the direction of the target, while maintaining a 45 degree angle
     * @param cannonPart
     * @param enemy
     * @param otherEntityPositionPart
     */
    private void setCannonAngle(CannonPart cannonPart, Entity enemy, PositionPart otherEntityPositionPart){
        //check if other entity is to my left or right
        PositionPart enemyPositionPart = enemy.getPart(PositionPart.class);
        if (enemyPositionPart.getX() < otherEntityPositionPart.getX()) {
            cannonPart.setDirection(3.1415f / 4);
        } else {
            cannonPart.setDirection(3 * 3.1415f / 4);
        }
    }

    private float initialVelocity(CannonPart myPosition, PositionPart otherPosition,
                                  float gravity, float angle){
        float velocity;
        float distanceX = Math.abs(myPosition.getMuzzleFaceCentre().getX() - otherPosition.getX());
        float distanceY = otherPosition.getY() - myPosition.getMuzzleFaceCentre().getY() ;
        velocity = (float) (10*gravity*distanceX/Math.sqrt((100*gravity*distanceX-100*gravity*distanceY)));
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
