package dk.grp1.tanks.weapon.HomingMissile.internal.AI;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameMap;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.List;
import java.util.Objects;

public class State {

    private GameMap gameMap;
    private Entity entity;

    public State(GameMap gameMap, Entity entity) {
        this.gameMap = gameMap;
        this.entity = entity;
    }

    public List<State> getSuccessor(){
        throw new UnsupportedOperationException("Get successor not supported");
    }

    public Vector2D getEntityPosition(){
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        throw new UnsupportedOperationException();
    }

}
