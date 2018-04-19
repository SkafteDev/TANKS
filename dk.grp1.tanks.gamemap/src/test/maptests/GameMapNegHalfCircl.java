package maptests;

import dk.grp1.tanks.common.data.IGameMapFunction;
import dk.grp1.tanks.gamemap.internal.GameMapNegativeHalfCircle;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GameMapNegHalfCircl {


    @Test(expected = IllegalArgumentException.class)
    public void testCreateNull() {
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(0, 1, null, 5);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCenterXOutOFRange() {
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(10, 20, 100, 5, 5);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCenterStartOutOFRange() {
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(10, 20, 15, 5, 2);

    }


    @Test
    public void testGetStartX() {
        float startX = 0;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(startX, 20, 15, 5, 5);
        Assert.assertEquals(map.getStartX(), startX, 0.005);
    }

    @org.junit.Test
    public void testGetYValue() {
        float startX = 0;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(10, 20, 15, 5, 5);
        Assert.assertEquals(map.getYValue(15), 0, 0.005);



    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testGetYValueOutsideRange() {
        float mapHeight = 100;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(10, 20, 15, 5, 5);
        Assert.assertEquals(map.getYValue(-2), mapHeight, 0.005);


    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testGetYValueOutsideRange2() {
        float mapHeight = 100;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(10, 20, 15, 5, 5);
        Assert.assertEquals(map.getYValue(30), mapHeight, 0.005);


    }

    @Test
    public void testGetYValueAtStartBoundary() {
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(10, 20, 15, 5, 5);
        Assert.assertEquals(map.getYValue(10), 5, 0.005);


    }

    @Test
    public void testGetYValueAtEndBoundary() {
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(10, 20, 15, 5, 5);
        Assert.assertEquals(map.getYValue(20), 5, 0.005);


    }

    @org.junit.Test
    public void testGetEndX() {
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(10, 20, 15, 5, 5);
        Assert.assertEquals(map.getEndX(), 20, 0.005);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testCreateStartXgreaterThanEndX() {
        float startX = 100;
        float endX = 20;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(startX, endX, 15, 5, 5);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testCreateStartXEqualToEndX() {
        float startX = 100;
        float endX = startX;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(startX, endX, 15, 5, 5);
    }


    @org.junit.Test
    public void testGetYValuesCircleMap() {
        float startX = 10;
        float a = 15;

        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(startX, 20, 15, 5, 5);
        List<Float> xValues = new ArrayList<>();
        xValues.add(startX);
        xValues.add(startX+2);
        xValues.add(startX+5);
        xValues.add(startX+7);
        xValues.add(startX+9);
        List<Float> yvalues = map.getYValues(xValues);
        Assert.assertEquals(getCricleYValue(xValues.get(0)), yvalues.get(0), 0.005);
        Assert.assertEquals(yvalues.get(1), getCricleYValue(xValues.get(1)), 0.005);
        Assert.assertEquals(yvalues.get(2), getCricleYValue(xValues.get(2)), 0.005);
        Assert.assertEquals(yvalues.get(3), getCricleYValue(xValues.get(3)), 0.005);
        Assert.assertEquals(yvalues.get(4), getCricleYValue(xValues.get(4)), 0.005);

    }

    private float getCricleYValue(float x) {
        float a = 15;
        float b = 5;
        float r = 5;
        float y = (float) (b - Math.sqrt(-(a*a) + 2 * a * x + r * r - (x * x)));
        return y;
    }


    @org.junit.Test
    public void testIsWithinIsWithin() {
        float startX = 10;
        float endX = 20;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(startX, 20, 15, 5, 5);
        Assert.assertTrue(map.isWithin(15));
    }

    @org.junit.Test
    public void testIsWithinIsNotWithin() {
        float startX = 10;
        float endX = 20;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(startX, 20, 15, 5, 5);
        Assert.assertFalse(map.isWithin(25));
    }

    @org.junit.Test
    public void testIsWithinEdge1() {
        float startX = 10;
        float endX = 20;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(startX, 20, 15, 5, 5);
        Assert.assertTrue(map.isWithin(10));
    }

    @org.junit.Test
    public void testIsWithinEdge2() {
        float startX = 10;
        float endX = 20;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(startX, 20, 15, 5, 5);
        Assert.assertFalse(map.isWithin(20));
    }

    @org.junit.Test
    public void testSetEndX() {
        float startX = 50;
        float endX = 65;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(startX, endX, 15, 5, 50);
        float newEndX = 60;
        map.setEndX(newEndX);
        Assert.assertEquals(map.getEndX(), newEndX, 0.005);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testSetEndXLessThanStartX() {
        float startX = 0;
        float endX = 300;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(startX, endX, 15, 5, 5);
        float newEndX = -5;
        map.setEndX(newEndX);
        Assert.assertEquals(map.getEndX(), newEndX, 0.005);
    }

    @org.junit.Test
    public void testSetStartX() {
        float startX = 10;
        float endX = 60;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(startX, endX, 15, 5, 50);
        float newStartX = 20;
        map.setStartX(newStartX);
        Assert.assertEquals(map.getStartX(), newStartX, 0.005);
    }

   @Test(expected = IllegalArgumentException.class)
   public void testSetStartXTooFarAway(){
       float startX = 10;
       float endX = 20;
       GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(startX, endX, 15, 5, 5);
       float newStartX = 30;
       map.setStartX(newStartX);
       Assert.assertEquals(map.getStartX(), newStartX, 0.005);
   }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testSetStartXGreaterThanEndX() {
        float startX = 0;
        float endX = 300;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(startX, endX, 15, 5, 5);
        float newStartX = 400;
        map.setStartX(newStartX);
        Assert.assertEquals(map.getStartX(), newStartX, 0.005);
    }

    @Test
    public void testIsOnlyWithin() {
        float startX = 10;
        float endX = 20;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(startX, endX, 15, 5, 5);
        Assert.assertTrue(map.existsOnlyWithinRange(5, 25));
    }

    @Test
    public void testIsOnlyWithinWithOut() {
        float startX = 10;
        float endX = 20;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(startX, endX, 15, 5, 5);
        Assert.assertFalse(map.existsOnlyWithinRange(5, 8));
    }

    @Test
    public void testIsOnlyWithinHalfOverlap() {
        float startX = 10;
        float endX = 20;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(startX, endX, 15, 5, 5);
        Assert.assertFalse(map.existsOnlyWithinRange(5, 15));
    }

    @Test
    public void testIsOnlyWithinOverlap() {
        float startX = 10;
        float endX = 20;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(startX, endX, 15, 5, 5);
        Assert.assertTrue(map.existsOnlyWithinRange(10, 20));
    }

    @Test
    public void testIsOnlyWithinEdge1() {
        float startX = 10;
        float endX = 20;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(startX, endX, 15, 5, 5);
        Assert.assertTrue(map.existsOnlyWithinRange(10, 30));
    }

    @Test
    public void testIsOnlyWithinEdge2() {
        float startX = 10;
        float endX = 20;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(startX, endX, 15, 5, 5);
        Assert.assertTrue(map.existsOnlyWithinRange(5, 20));
    }

    @Test
    public void testSplitIntoNewRangesPerfect() {
        float startX = 10;
        float endX = 20;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(startX, endX, 15, 5, 5);
        List<IGameMapFunction> splits = map.splitInTwoWithNewRanges(10, 15, 15, 20);
        Assert.assertEquals(2, splits.size());
        Assert.assertEquals(startX, splits.get(0).getStartX(), 0.001);
        Assert.assertEquals(15, splits.get(0).getEndX(), 0.001);
        Assert.assertEquals(15, splits.get(1).getStartX(), 0.001);
        Assert.assertEquals(endX, splits.get(1).getEndX(), 0.001);
        Assert.assertEquals(map.getYValue(startX), splits.get(0).getYValue(startX), 0.001);
        Assert.assertEquals(map.getYValue(endX - 0.001f), splits.get(1).getYValue(endX - 0.001f), 0.001);
        Assert.assertEquals(map.getYValue(15 - 0.001f), splits.get(0).getYValue(15 - 0.001f), 0.001);
        Assert.assertEquals(map.getYValue(15), splits.get(1).getYValue(15), 0.001);
    }

    @Test
    public void testSplitIntoNewRangesRangesSubsetTheFunction() {
        float startX = 12;
        float endX = 18;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(startX, endX, 15, 5, 5);
        List<IGameMapFunction> splits = map.splitInTwoWithNewRanges(12, 15, 15, 18);
        Assert.assertEquals(2, splits.size());
        Assert.assertEquals(startX, splits.get(0).getStartX(), 0.001);
        Assert.assertEquals(15, splits.get(0).getEndX(), 0.001);
        Assert.assertEquals(15, splits.get(1).getStartX(), 0.001);
        Assert.assertEquals(endX, splits.get(1).getEndX(), 0.001);
        Assert.assertEquals(map.getYValue(startX), splits.get(0).getYValue(startX), 0.001);
        Assert.assertEquals(map.getYValue(endX - 0.001f), splits.get(1).getYValue(endX - 0.001f), 0.001);
        Assert.assertEquals(map.getYValue(14.99f), splits.get(0).getYValue(14.99f), 0.001);
        Assert.assertEquals(map.getYValue(15), splits.get(1).getYValue(15), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSplitIntoNewRangesRangesSuperSetTheFunction() {
        float startX = 8;
        float endX = 22;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(10, 20, 15, 5, 5);
        List<IGameMapFunction> splits = map.splitInTwoWithNewRanges(8, 15, 15, 22);
        Assert.assertEquals(2, splits.size());
        Assert.assertEquals(startX, splits.get(0).getStartX(), 0.001);
        Assert.assertEquals(15, splits.get(0).getEndX(), 0.001);
        Assert.assertEquals(15, splits.get(1).getStartX(), 0.001);
        Assert.assertEquals(endX, splits.get(1).getEndX(), 0.001);
        Assert.assertEquals(startX * 0 + 100, splits.get(0).getYValue(startX), 0.001);
        Assert.assertEquals(endX * 0 + 100, splits.get(1).getYValue(endX - 0.001f), 0.001);
        Assert.assertEquals(map.getYValue(15), splits.get(0).getYValue(14.99f), 0.001);
        Assert.assertEquals(map.getYValue(15), splits.get(1).getYValue(15), 0.001);
    }


    @Test
    public void testSplitIntoNewRangesNotConnect() {
        float startX = 10;
        float endX = 20;
        GameMapNegativeHalfCircle map = new GameMapNegativeHalfCircle(startX, endX, 15, 5, 5);
        List<IGameMapFunction> splits = map.splitInTwoWithNewRanges(10, 12, 17, 20);
        Assert.assertEquals(2, splits.size());
        Assert.assertEquals(startX, splits.get(0).getStartX(), 0.001);
        Assert.assertEquals(12, splits.get(0).getEndX(), 0.001);
        Assert.assertEquals(17, splits.get(1).getStartX(), 0.001);
        Assert.assertEquals(endX, splits.get(1).getEndX(), 0.001);
        Assert.assertEquals(map.getYValue(startX), splits.get(0).getYValue(startX), 0.001);
        Assert.assertEquals(map.getYValue(endX - 0.001f), splits.get(1).getYValue(endX - 0.001f), 0.001);
        Assert.assertEquals(map.getYValue(11.99f), splits.get(0).getYValue(11.99f), 0.001);
        Assert.assertEquals(map.getYValue(17), splits.get(1).getYValue(17), 0.001);
    }


}


