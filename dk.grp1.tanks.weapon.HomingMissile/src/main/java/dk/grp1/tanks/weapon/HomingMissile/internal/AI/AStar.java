package dk.grp1.tanks.weapon.HomingMissile.internal.AI;

import dk.grp1.tanks.common.utils.Vector2D;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class AStar implements ITreeSearch{

    private final float GOALRANGE = 5.1f;
    private List<Node> fringe;
    private State initialState;
    private State goalState;
    private Set<Node> discoveredPositions;
    private Set<Node> discoveredPositionsShared;

    public AStar(State initialState, State goalState) {
        this.initialState = initialState;
        this.goalState = goalState;
        this.fringe = new ArrayList<>();
        discoveredPositions = new HashSet<>();
    }
    public AStar(State initialState, State goalState, Set<Node> discoveredPositionsShared) {
        this.initialState = initialState;
        this.goalState = goalState;
        this.fringe = new ArrayList<>();
        discoveredPositions = new HashSet<>();
        this.discoveredPositionsShared = discoveredPositionsShared;
    }



    public List<Node> search(){
        fringe.add(new Node(null,initialState,getHeuristicValue(initialState)));
        while (!fringe.isEmpty()){
            Node node = extractLowest();
            if(isGoalState(node.getState())){
                System.out.println(discoveredPositions.size());
                System.out.println(discoveredPositionsShared.size());
                return node.getPath();
            }
            List<Node> children = expand(node);
            fringe.addAll(children);
        }
        return null;
    }

    @Override
    public void searchPoints(List<Vector2D> points, Consumer consumer) {


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                consumer.startConsuming();
                List<Node> nodes = search();

                if (nodes != null) {

                    for (Node node : nodes) {
                        points.add(node.getState().getEntityPosition());
                    }

                }
                consumer.stopConsuming();


            }
        });
        t.start();
    }

    private float getHeuristicValue(State state){
        return Vector2D.subtractVectors(state.getEntityPosition(),goalState.getEntityPosition()).length();
    }

    public List<Node> expand(Node node){
        List<Node> successors = new ArrayList<>();
        List<State> children = node.getState().getSuccessors();
        for (State child : children) {
            Node succ = new Node(node,child,getHeuristicValue(child));
            if(discoveredPositions.contains(succ)) {
                continue;
            }
            successors.add(succ);
            discoveredPositions.add(succ);
            discoveredPositionsShared.add(succ);
        }

//        successors = []
//        children = successor_fn(node.STATE)
//        for child in children:
//        s = Node(node)  # create node for each in state list
//        s.STATE = child  # e.g. result = 'F' then 'G' from list ['F', 'G']
//        s.PARENT_NODE = node
//        s.DEPTH = node.DEPTH + 1
//        s.HEU = child[0][1]
//        s.PATHCOST = child[1] + node.PATHCOST
//        successors = INSERT(s, successors)
        return successors;
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
        fringe.remove(lowest);
        return lowest;
    }

    private boolean isGoalState(State state){
        boolean inRangeX = Math.abs(goalState.getEntityPosition().getX() - state.getEntityPosition().getX()) < GOALRANGE;
        boolean inRangeY = Math.abs(goalState.getEntityPosition().getY() - state.getEntityPosition().getY()) < GOALRANGE;
        return inRangeX && inRangeY;
    }
}
