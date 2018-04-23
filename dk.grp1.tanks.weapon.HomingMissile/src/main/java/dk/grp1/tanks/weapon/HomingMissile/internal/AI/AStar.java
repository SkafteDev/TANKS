package dk.grp1.tanks.weapon.HomingMissile.internal.AI;

import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class AStar implements ITreeSearch{

    private final float GOALRANGE = 0.5f;
    private List<Node> fringe;
    private State initialState;
    private State goalState;
    public AStar(State initialState, State goalState) {
        this.initialState = initialState;
        this.goalState = goalState;
        this.fringe = new ArrayList<>();
    }

    public List<Node> search(){
        fringe.add(new Node(null,initialState,getHeuristicValue(initialState)));
        while (!fringe.isEmpty()){
            Node node = extractLowest();
            if(isGoalState(node.getState())){
                return node.getPath();
            }
            List<Node> children = node.expand();
            fringe.addAll(children);
        }
        return null;
    }

    private float getHeuristicValue(State state){
        return Vector2D.subtractVectors(state.getEntityPosition(),goalState.getEntityPosition()).length();
    }

    private Node extractLowest(){
        Node lowest = null;
        for (Node node : fringe) {
            if(lowest == null){
                lowest = node;
                continue;
            }
            if(lowest.getEstimatedTotalCost() > node.getEstimatedTotalCost()){
                lowest = node;
            }
        }
        return lowest;
    }

    private boolean isGoalState(State state){
        boolean inRangeX = Math.abs(goalState.getEntityPosition().getX() - state.getEntityPosition().getX()) < GOALRANGE;
        boolean inRangeY = Math.abs(goalState.getEntityPosition().getY() - state.getEntityPosition().getY()) < GOALRANGE;
        return inRangeX && inRangeY;
    }
}
