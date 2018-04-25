package dk.grp1.tanks.weapon.HomingMissile.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.IEntityPart;
import dk.grp1.tanks.common.data.parts.MovementPart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.eventManager.events.ExplosionEvent;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.List;

public class HomingControlPart implements IEntityPart {

    private List<Vector2D> path;
    private int goingToIndex;

    public HomingControlPart(){

    }

    public HomingControlPart(List<Vector2D> path){

        this.path = path;
    }
    @Override
    public void processPart(Entity entity, GameData gameData, World world) {
        if(isPastPoint(entity)){
            goingToIndex++;
            if(goingToIndex >= path.size()){
                gameData.getEventManager().addEvent(new ExplosionEvent(entity,path.get(path.size()-1),20));
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
        movementPart.setVelocity(directionVector);
    }

    private boolean isPastPoint(Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        MovementPart movementPart = entity.getPart(MovementPart.class);
        Vector2D goingTo = path.get(goingToIndex);

        if(movementPart.getVelocity().getX() == 0){
            return norm(positionPart.getX()) >= norm(goingTo.getX());
        }else if(movementPart.getVelocity().getY() == 0){
            return norm(positionPart.getY()) >= norm(goingTo.getY());
        }else{
           return positionPart.getX() * norm(positionPart.getX()) >= path.get(goingToIndex).getX() * norm(positionPart.getX()) &&  positionPart.getY() * norm(positionPart.getY()) >= path.get(goingToIndex).getY() * norm(positionPart.getY());
        }

    }

    private float norm(float value){
        if(value > 0){
            return 1;
        }
        if(value < 0){
            return -1;
        }
        throw new Error("You cant normalize a value of 0 (Zero)");
    }
}
