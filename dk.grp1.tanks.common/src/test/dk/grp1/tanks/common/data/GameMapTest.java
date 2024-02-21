package dk.grp1.tanks.common.data;

import dk.grp1.tanks.common.utils.GameMapFunctionComparator;
import dk.grp1.tanks.common.utils.Vector2D;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GameMapTest {

    private List<IGameMapFunction> gameMapFunctions;
    private float GAMEWIDTH = 800;
    private float GAMEHEIGHT = 600;
    private GameMapFunctionComparator comparator = new GameMapFunctionComparator();
    private GameMap gm;

    @Before
    public void setUp() throws Exception {
        gm = new GameMap(GAMEWIDTH, GAMEHEIGHT);
        gameMapFunctions = createFunctions();
    }

    private List<IGameMapFunction> createFunctions(){
        ArrayList<IGameMapFunction> functions = new ArrayList<>();
        functions.add(new IGameMapFunction() {
            private float startX = 0;
            private float endX = 100;

            @Override
            public float getStartX() {
                return startX;
            }

            @Override
            public float getYValue(float xValue) {
                return 10;
            }

            @Override
            public float getEndX() {
                return endX;
            }

            @Override
            public List<Float> getYValues(List<Float> xValues) {
                return null;
            }

            @Override
            public boolean isWithin(float x) {
                if (getStartX() <= x && x < getEndX()){
                    return true;
                }
                return false;
            }

            @Override
            public void setEndX(float value) {
                this.endX = value;
            }

            @Override
            public void setStartX(float value) {
                this.startX = value;
            }

            @Override
            public boolean existsOnlyWithinRange(float startX, float endX) {
                if (this.startX <= startX && endX < this.endX){
                    return true;
                }

                return false;
            }

            @Override
            public List<IGameMapFunction> splitInTwoWithNewRanges(float rangeOneStartX, float rangeOneEndX, float rangeTwoStartX, float rangeTwoEndX) {
                return null;
            }
        });

        return functions;
    }

    @Test
    public void getGameMapFunctions() {
        gm.setGameMapFunctions(gameMapFunctions);
        assertEquals(gameMapFunctions, gm.getGameMapFunctions());
    }

    @Test
    public void setGameMapFunctions() {
        assertEquals(0, gm.getGameMapFunctions().size());
        gm.setGameMapFunctions(gameMapFunctions);
        assertEquals(gameMapFunctions, gm.getGameMapFunctions());
        assertEquals(1, gm.getGameMapFunctions().size());
    }

    @Test
    public void addGameMapFunction() {
        IGameMapFunction fun = createFunctions().get(0);
        gm.addGameMapFunction(fun);
        assertEquals(fun, gm.getGameMapFunctions().get(0));
        assertEquals(1, gm.getGameMapFunctions().size());
    }

    @Test
    public void getVertices() {
        int vertices = 3;
        gm.setGameMapFunctions(createFunctions());
        List<Vector2D> expectedResult = Arrays.asList(new Vector2D(0.0f,10.0f), new Vector2D(33.333332f, 10.0f), new Vector2D(66.666664f,10.0f), new Vector2D(800.0f,0.0f), new Vector2D(0.0f, 0.0f));
        assertEquals(expectedResult, gm.getVertices(0,100, vertices));

    }

    @Test
    public void getVerticesAsFloats() {
        gm.setGameMapFunctions(createFunctions());
        float[] expectedResult = new float[]{0.0f, 10.0f, 33.333332f, 10.0f, 66.666664f, 10.0f, 800.0f, 0.0f, 0.0f, 0.0f};
        float[] result = gm.getVerticesAsFloats(0, 100, 3);

        for (int i = 0; i < expectedResult.length; i++) {
            assertEquals(expectedResult[i], result[i], 0.001);
        }
    }

    @Test
    public void getDirectionVector() {
        Vector2D expectedResult = new Vector2D(1,0);
        assertEquals(expectedResult, gm.getDirectionVector(new Vector2D(10, 10)));
    }

    @Test
    public void getHeight() {
        gm.setGameMapFunctions(createFunctions());

        assertEquals(10, gm.getHeight(new Vector2D(10, 15)), 0.001);
    }
}