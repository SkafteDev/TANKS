package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.utils.Vector2D;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.*;

public class ShapePartTest {

    private ShapePart shapePart;
    private List<Vector2D> vectors;

    public ShapePartTest() {
        shapePart = new ShapePart();
        vectors = getVertices(200);
        shapePart.setShape(vectors);
    }

    private List<Vector2D> getVertices(int n) {
        List<Vector2D> vertices = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            vertices.add(new Vector2D(i, i));
        }

        return vertices;
    }

    @Test
    public void getShape() {
        assertEquals(shapePart.getShape(), vectors);
    }

    @Test
    public void setShape() {
        List<Vector2D> newVertices = getVertices(500);
        shapePart.setShape(newVertices);

        assertEquals(shapePart.getShape(), newVertices);

    }
}