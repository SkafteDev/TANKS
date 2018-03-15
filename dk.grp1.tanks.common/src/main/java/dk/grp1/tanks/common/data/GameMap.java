package dk.grp1.tanks.common.data;

import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private List<Vector2D> vertices;

    /**
     *
     */
    public GameMap(){
        vertices = new ArrayList<>();
    }

    /**
     * Get all vertices in the map.
     * @return List of all vertices of type Vector2D
     */
    public List<Vector2D> getVertices() {
        return vertices;
    }

    /**
     * Sets all vertices in the map, as a list containing Vector2D
     * @param vertices
     */
    public void setVertices(List<Vector2D> vertices) {
        this.vertices = vertices;
    }
}
