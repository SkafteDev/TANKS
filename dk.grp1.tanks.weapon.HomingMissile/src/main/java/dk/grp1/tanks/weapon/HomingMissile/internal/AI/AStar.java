package dk.grp1.tanks.weapon.HomingMissile.internal.AI;

import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class AStar {

    private List<Node> fringe;
    private Node initialState;
    private State goalState;

    public AStar(Node initialState, State goalState) {
        this.initialState = initialState;
        this.goalState = goalState;
        this.fringe = new ArrayList<>();
    }

    public List<Vector2D> search(){
        throw new UnsupportedOperationException();
    }

    private float getHeuristicValue(State state){
        throw new UnsupportedOperationException();
    }

    private Node extractLowest(){
        throw new UnsupportedOperationException();
    }
}
