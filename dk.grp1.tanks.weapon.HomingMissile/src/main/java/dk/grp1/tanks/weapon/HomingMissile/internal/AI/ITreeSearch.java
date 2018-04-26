package dk.grp1.tanks.weapon.HomingMissile.internal.AI;

import dk.grp1.tanks.common.utils.Vector2D;

import java.util.List;

public interface ITreeSearch {
    /**
     * Searches for the optimal path
     * @return The path as a list of nodes, or null if no path was found
     */
    public List<Node> search();

    public void searchPoints( List<Vector2D> points,Consumer consumer);
}
