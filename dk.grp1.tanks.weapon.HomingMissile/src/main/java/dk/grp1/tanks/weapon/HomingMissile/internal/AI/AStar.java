package dk.grp1.tanks.weapon.HomingMissile.internal.AI;

import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class AStar implements ITreeSearch{

    private List<Node> fringe;
    private State initialState;
    private State goalState;

    public AStar(State initialState, State goalState) {
        this.initialState = initialState;
        this.goalState = goalState;
        this.fringe = new ArrayList<>();
    }

    public List<Node> search(){
        throw new UnsupportedOperationException();
    }

    private float getHeuristicValue(State state){
        throw new UnsupportedOperationException();
    }

    private Node extractLowest(){
        throw new UnsupportedOperationException();
    }

    private boolean isGoalState(State state){
        throw new UnsupportedOperationException();
    }
}
