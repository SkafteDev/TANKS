package dk.grp1.tanks.weapon.HomingMissile.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.eventManager.events.ExplosionAnimationEvent;
import dk.grp1.tanks.common.eventManager.events.ExplosionEvent;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.List;

public class HomingControlPart implements IEntityPart {

    private List<Vector2D> path;
    private int goingToIndex;
    private boolean firstTime = true;

    public HomingControlPart(){

    }

    public HomingControlPart(List<Vector2D> path){
        this.path = path;
        goingToIndex = path.size()-1;

    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {
        if(isPastPoint(entity)){
            goingToIndex--;
            if(goingToIndex <=1){
                DamagePart dmg = entity.getPart(DamagePart.class);
                ExplosionTexturePart text = entity.getPart(ExplosionTexturePart.class);

                gameData.getEventManager().addEvent(new ExplosionEvent(entity,path.get(0),dmg.getExplosionRadius()));
                gameData.getEventManager().addEvent(new ExplosionAnimationEvent(entity,path.get(0),text,dmg.getExplosionRadius()));
                world.removeEntity(entity);
                return;
            }
            setNewDirection(entity);



            System.out.println("Is past point. Calculated new direction. index: " + goingToIndex);
        }

    }

    private void setNewDirection(Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        MovementPart movementPart = entity.getPart(MovementPart.class);
        if(positionPart == null || movementPart == null){
            return;
        }
        Vector2D nextPoint = path.get(goingToIndex);
        Vector2D directionVector = new Vector2D(nextPoint.getX()-positionPart.getX(), nextPoint.getY()-positionPart.getY());
        //
        if(directionVector.length() < 0.1){
            movementPart.setVelocity(directionVector);
        }else {
            Vector2D dir = directionVector.unitVector();
            dir.multiplyWithConstant(200);
            movementPart.setVelocity(dir);
        }

    }

    private boolean isPastPoint(Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        MovementPart movementPart = entity.getPart(MovementPart.class);
        Vector2D goingTo = path.get(goingToIndex);
        Vector2D velocity = movementPart.getVelocity();
        if(movementPart.getVelocity().getX() == 0 && movementPart.getVelocity().getY() == 0){
            return true;
        }
        if(movementPart.getVelocity().getX() == 0){
            return positionPart.getY()* norm(velocity.getY()) >= goingTo.getY()* norm(velocity.getY());
        }else if(movementPart.getVelocity().getY()==0){
            return positionPart.getX() * norm(velocity.getX()) >=goingTo.getX()* norm(velocity.getX());
        }else{
           return positionPart.getX() * norm(velocity.getX()) >= path.get(goingToIndex).getX() * norm(velocity.getX()) &&  positionPart.getY() * norm(velocity.getY()) >= path.get(goingToIndex).getY() * norm(velocity.getY());
        }

    }

    private float norm(float value){
        if(value > 0){
            return 1;
        }
         if(value < 0){
            return -1;
        }
        throw new Error("You cant normalize a value of 0 (Zero) " + value);
    }
}
