package dk.grp1.tanks.common.utils;

import dk.grp1.tanks.common.data.IGameMapFunction;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GameMapFunctionComparatorTest {

    private GameMapFunctionComparator comparator;

    @Before
    public void setUp() throws Exception {
        comparator = new GameMapFunctionComparator();
    }

    @Test
    public void compare() {
        IGameMapFunction fun = new IGameMapFunction() {
            @Override
            public float getStartX() {
                return 0;
            }

            @Override
            public float getYValue(float xValue) {
                return 0;
            }

            @Override
            public float getEndX() {
                return 5;
            }

            @Override
            public List<Float> getYValues(List<Float> xValues) {
                return null;
            }

            @Override
            public boolean isWithin(float x) {
                return false;
            }

            @Override
            public void setEndX(float value) {

            }

            @Override
            public void setStartX(float value) {

            }

            @Override
            public boolean existsOnlyWithinRange(float startX, float endX) {
                return false;
            }

            @Override
            public List<IGameMapFunction> splitInTwoWithNewRanges(float rangeOneStartX, float rangeOneEndX, float rangeTwoStartX, float rangeTwoEndX) {
                return null;
            }
        };
        IGameMapFunction fun1 = new IGameMapFunction() {
            @Override
            public float getStartX() {
                return 4;
            }

            @Override
            public float getYValue(float xValue) {
                return 0;
            }

            @Override
            public float getEndX() {
                return 10;
            }

            @Override
            public List<Float> getYValues(List<Float> xValues) {
                return null;
            }

            @Override
            public boolean isWithin(float x) {
                return false;
            }

            @Override
            public void setEndX(float value) {

            }

            @Override
            public void setStartX(float value) {

            }

            @Override
            public boolean existsOnlyWithinRange(float startX, float endX) {
                return false;
            }

            @Override
            public List<IGameMapFunction> splitInTwoWithNewRanges(float rangeOneStartX, float rangeOneEndX, float rangeTwoStartX, float rangeTwoEndX) {
                return null;
            }
        };
        assertEquals(-1, comparator.compare(fun,fun1));
        assertEquals(1, comparator.compare(fun1, fun));

        fun1 = fun;
        assertEquals(0, comparator.compare(fun, fun1));

        fun1 = null;
        assertEquals(-1, comparator.compare(fun, fun1));
    }
}