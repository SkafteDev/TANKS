package dk.grp1.tanks.weapon.HomingMissile.internal.AI;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameMap;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.utils.Vector2D;
import dk.grp1.tanks.weapon.HomingMissile.internal.HomingMissile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class State {

    private GameMap gameMap;
    private Entity entity;
    private final float precision = 5f;
    private final Vector2D entityPosition;

    public State(GameMap gameMap, Entity entity) {
        this.gameMap = gameMap;
        this.entity = entity;
        CirclePart circlePart = this.entity.getPart(CirclePart.class);
        if (circlePart == null) {
            throw new NullPointerException("PositionPart is null");
        }
        entityPosition = new Vector2D(circlePart.getCentreX(),circlePart.getCentreY());
    }


    /**
     * Get all valid successor states
     * @return
     */
    public List<State> getSuccessors(){
        ArrayList<State> stateList = new ArrayList<>();
        Entity rightEntity = copyEntity(this.entity);
        changePosition(rightEntity,precision,0);

        Entity rightUpEntity = copyEntity(this.entity);
        changePosition(rightUpEntity,precision,precision);

        Entity upEntity = copyEntity(this.entity);
        changePosition(upEntity,0,precision);

        Entity leftUpEntity = copyEntity(this.entity);
        changePosition(leftUpEntity,-precision,precision);

        Entity leftEntity = copyEntity(this.entity);
        changePosition(leftEntity,-precision,0);

        Entity leftDownEntity = copyEntity(this.entity);
        changePosition(leftDownEntity,-precision,-precision);

        Entity downEntity = copyEntity(this.entity);
        changePosition(downEntity,0,-precision);

        Entity rightDownEntity = copyEntity(this.entity);
        changePosition(rightDownEntity,precision,-precision);




        stateList.add(new State(gameMap,rightEntity));
        stateList.add(new State(gameMap,rightUpEntity));
        stateList.add(new State(gameMap,upEntity));
       stateList.add(new State(gameMap,leftUpEntity));
        stateList.add(new State(gameMap,leftEntity));
       stateList.add(new State(gameMap,leftDownEntity));
        stateList.add(new State(gameMap,downEntity));
       stateList.add(new State(gameMap,rightDownEntity));



        ArrayList<State> statesToReturn = new ArrayList<>();
        for (State state : stateList) {
            if (state.isValid()){
                statesToReturn.add(state);
            }
        }

        return statesToReturn;

    }



    /**
     * Get the position of the Entity
     * @return
     */
    public Vector2D getEntityPosition(){
        return entityPosition;
    }


    /**
     * Create a copy of the given Entity
     * @param entityToCopy
     * @return
     */
    private Entity copyEntity(Entity entityToCopy){
        CirclePart circlePart = entityToCopy.getPart(CirclePart.class);

        if (circlePart == null) {
            throw new NullPointerException("CirclePart is null");
        }

        Entity newEntity = new HomingMissile() {};
        newEntity.add(new CirclePart(circlePart.getCentreX(),circlePart.getCentreY(),circlePart.getRadius()));
        return newEntity;
    }

    /**
     * Change the position of a given Entity
     * @param entity
     * @param addToX
     * @param addToY
     * @return
     */
    private Entity changePosition(Entity entity, float addToX, float addToY){
        if (entity == null) {
            throw new IllegalArgumentException("Entity is null");
        }

        CirclePart circlePart = entity.getPart(CirclePart.class);
        if (circlePart != null) {
            circlePart.setCentreX(circlePart.getCentreX() + addToX);
            circlePart.setCentreY(circlePart.getCentreY() + addToY);

        }
        return entity;

    }

    /**
     * Get the entity
     * @return
     */
    public Entity getEntity(){
        return this.entity;
    }

    /**
     * Check if the entity has collided with the GameMap
     * @return
     */
    private boolean isValid(){
        CirclePart circlePart = this.getEntity().getPart(CirclePart.class);
        if (circlePart != null) {
            if(this.getEntityPosition().getX() > 0 && this.getEntityPosition().getX() < gameMap.getGAMEWIDTH()){
                if(this.getEntityPosition().getY() > 0 && this.getEntityPosition().getY() < gameMap.getGAMEHEIGHT()) {
                    return gameMap.getHeight(this.getEntityPosition()) < this.getEntityPosition().getY() - (circlePart.getRadius() + 1f);
                }
            }

        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        return getEntityPosition() != null ? Math.abs(getEntityPosition().getX()-state.getEntityPosition().getX()) < 0.001f && Math.abs(getEntityPosition().getY()-state.getEntityPosition().getY()) < 0.001f  : state.getEntityPosition() == null;

    }

    @Override
    public int hashCode() {
        return getEntityPosition() != null ? getEntityPosition().hashCode() : 0;
    }
}
