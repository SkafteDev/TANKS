package dk.grp1.tanks.weapon.HomingMissile.internal.AI;

import java.util.List;

public class Node {

    private Node parent;
    private State state;
    private int depth;
    private float pathCost;
    private float heuristic;


    public Node(Node parent, State state, float heuristic) {
        this.parent = parent;
        this.state = state;
        this.heuristic = heuristic;
        this.depth = parent.getDepth() + 1;
        this.pathCost = parent.getPathCost();
    }

    public int getDepth() {
        return depth;
    }

    public float getPathCost() {
        return pathCost;
    }

    public Node getParent() {
        return parent;
    }

    public State getState() {
        return state;
    }

    public List<Node> expand(){
        throw new UnsupportedOperationException();
    }

    public List<Node> getPath(){
        throw new UnsupportedOperationException();
    }

    public float getEstimatedTotalCost() {
        return heuristic + pathCost;
    }
}
