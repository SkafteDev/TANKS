package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.utils.Vector2D;
import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;

import java.util.ArrayList;
import java.util.List;

/**
 * ShapePart contains a list of vertices to draw a given shape
 */
public class ShapePart implements IEntityPart {


    private List<Vector2D> vertices;
    public ShapePart() {
        vertices = new ArrayList<>();
    }

    public void processPart(Entity entity, GameData gameData,World world) {

    }

    /**
     * Get the list of vertices
     * @return
     */
    public List<Vector2D> getShape() {
        return vertices;
    }

    /**
     * Set the shape with a list of vertices
     * @param shape
     */
    public void setShape(List<Vector2D> shape) {
        this.vertices = shape;
    }
}
