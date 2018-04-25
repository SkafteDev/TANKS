package dk.grp1.tanks.weapon.HomingMissile.internal.AI;

import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;
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
        this.depth = parent != null ? parent.getDepth() + 1 : 0;
        this.pathCost = parent != null ? parent.getPathCost() + 1 : 0;
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


    public List<Node> getPath() {
        List<Node> path = new ArrayList<>();
        path.add(this);
        for (Node currentParent = this.parent; currentParent != null; currentParent = currentParent.getParent()) {
            path.add(currentParent);
        }
        return path;
    }


    public float getEstimatedTotalCost() {
        return heuristic + pathCost;
    }
}
