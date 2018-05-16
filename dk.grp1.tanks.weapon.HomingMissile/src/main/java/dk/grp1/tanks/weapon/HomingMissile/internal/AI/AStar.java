package dk.grp1.tanks.weapon.HomingMissile.internal.AI;

import dk.grp1.tanks.common.utils.Vector2D;

import java.util.*;

public class AStar implements ITreeSearch{

    private final float GOALRANGE = 5.1f;
    private PriorityQueue<Node> fringe;
    private State initialState;
    private State goalState;
    private Set<Node> discoveredPositions;


    public AStar(State initialState, State goalState) {


        this.fringe = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                return (int) node1.getEstimatedTotalCost() - (int) node2.getEstimatedTotalCost();
            }
        });
        this.initialState = initialState;
        this.goalState = goalState;
        discoveredPositions = new HashSet<>();
    }



    @Override
    public List<Node> search(){

        Node firstNode = new Node(null, initialState, getHeuristicValue(initialState));

        fringe.add(firstNode);

        while (!fringe.isEmpty()){

            Node lowestNode = fringe.poll();
            if(isGoalState(lowestNode.getState())){
                return lowestNode.getPath();
            }
            List<Node> children = expand(lowestNode);
            fringe.addAll(children);
        }
        return null;
    }

    @Override
    public List<Vector2D> searchPoints() {
        List<Node> nodes = search();
        List<Vector2D> points = new ArrayList<>();

        if (nodes != null) {

            for (Node node : nodes) {
                points.add(node.getState().getEntityPosition());
            }

        }

        return points;

    }

    private float getHeuristicValue(State state){
        return Vector2D.subtractVectors(state.getEntityPosition(),goalState.getEntityPosition()).length();
    }

    private List<Node> expand(Node node){
        List<Node> successors = new ArrayList<>();
        List<State> children = node.getState().getSuccessors();
        for (State child : children) {
            Node succ = new Node(node,child,getHeuristicValue(child));
            if(discoveredPositions.contains(succ)  ) {
                continue;
            }
            successors.add(succ);
            discoveredPositions.add(succ);
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

    private boolean isGoalState(State state){
        boolean inRangeX = Math.abs(goalState.getEntityPosition().getX() - state.getEntityPosition().getX()) < GOALRANGE;
        boolean inRangeY = Math.abs(goalState.getEntityPosition().getY() - state.getEntityPosition().getY()) < GOALRANGE;
        return inRangeX && inRangeY;
    }
}
