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
        this.pathCost = parent != null ? parent.getPathCost() + calculatePathCost() : 0;
    }

    private float calculatePathCost() {
        return Vector2D.subtractVectors(state.getEntityPosition(), parent.getState().getEntityPosition()).length();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return getState() != null ? getState().equals(node.getState()) : node.getState() == null;
    }

    @Override
    public int hashCode() {
        int result = getParent() != null ? getParent().hashCode() : 0;
        result = 31 * result + (getState() != null ? getState().hashCode() : 0);

        return result;
    }
}
