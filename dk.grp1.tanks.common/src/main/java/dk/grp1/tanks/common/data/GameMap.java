package dk.grp1.tanks.common.data;

import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private List<Vector2D> vertices;

    /**
     *
     */
    public GameMap() {
        vertices = new ArrayList<>();
    }

    /**
     * Get all vertices in the map.
     *
     * @return List of all vertices of type Vector2D
     */
    public List<Vector2D> getVertices() {
        return vertices;
    }

    /**
     * '
     * Returns the list of vertices as a float array
     *
     * @return float[]
     */
    public float[] getVerticesAsFloats() {
        float[] floatVertices = new float[getVertices().size() * 2];
        int i = 0;
        for (Vector2D vertex : vertices) {
            floatVertices[i] = vertex.getX();
            i++;
            floatVertices[i] = vertex.getY();
            i++;
        }
        return floatVertices;
    }

    /**
     * Sets all vertices in the map, as a list containing Vector2D
     *
     * @param vertices
     */
    public void setVertices(List<Vector2D> vertices) {
        this.vertices = vertices;
    }

    public Vector2D getDirectionVector(float xCoordinate, GameData gameData) {
        float y = (gameData.getGameHeight() * 0.5f) * (float) Math.sin(((double) xCoordinate) / 128);
        float y2 = (gameData.getGameHeight() * 0.5f) * (float) Math.sin(((double) xCoordinate + 1) / 128);
        Vector2D vector = new Vector2D((xCoordinate + 1) - xCoordinate, y2 - y);
        return vector;
    }
}
