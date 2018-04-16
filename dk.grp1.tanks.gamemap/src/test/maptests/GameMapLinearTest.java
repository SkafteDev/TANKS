package maptests;

import dk.grp1.tanks.common.data.IGameMapFunction;
import dk.grp1.tanks.gamemap.internal.GameMapLinear;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class GameMapLinearTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNull(){
        GameMapLinear map = new GameMapLinear(0, 0,1,null);

    }
    @Test
    public void testGetStartX() {
        float startX = 0;
        GameMapLinear map = new GameMapLinear(0, 100, startX, 500);
        Assert.assertEquals(map.getStartX(), startX, 0.005);
    }

    @org.junit.Test
    public void testGetYValueFlatMap() {
        float startX = 0;
        float mapHeight = 100;
        GameMapLinear map = new GameMapLinear(0, mapHeight, startX, 500);
        Assert.assertEquals(map.getYValue(0), mapHeight, 0.005);
        Assert.assertEquals(map.getYValue(10), mapHeight, 0.005);
        Assert.assertEquals(map.getYValue(50), mapHeight, 0.005);
        Assert.assertEquals(map.getYValue(300), mapHeight, 0.005);
        Assert.assertEquals(map.getYValue(499), mapHeight, 0.005);


    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testGetYValueOutsideRange() {
        float startX = 0;
        float mapHeight = 100;
        GameMapLinear map = new GameMapLinear(0, mapHeight, startX, 20);
        Assert.assertEquals(map.getYValue(-2), mapHeight, 0.005);



    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testGetYValueOutsideRange2() {
        float startX = 0;
        float mapHeight = 100;
        GameMapLinear map = new GameMapLinear(0, mapHeight, startX, 20);
        Assert.assertEquals(map.getYValue(30), mapHeight, 0.005);



    }

    @org.junit.Test
    public void testGetYValueTiltedMap() {
        float startX = 0;
        float mapHeight = 100;
        float inclination = 2.5f;
        GameMapLinear map = new GameMapLinear(inclination, mapHeight, startX, 500);
        Assert.assertEquals(map.getYValue(0), mapHeight, 0.005);
        Assert.assertEquals(map.getYValue(10), 10 * inclination + mapHeight, 0.005);
        Assert.assertEquals(map.getYValue(50), 50 * inclination + mapHeight, 0.005);
        Assert.assertEquals(map.getYValue(300), 300 * inclination + mapHeight, 0.005);
        Assert.assertEquals(map.getYValue(499), 499 * inclination + mapHeight, 0.005);


    }

    @org.junit.Test
    public void testGetEndX() {
        float startX = 0;
        float endX = 300;
        GameMapLinear map = new GameMapLinear(0, 100, startX, endX);
        Assert.assertEquals(map.getEndX(), endX, 0.005);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testCreateStartXgreaterThanEndX() {
        float startX = 100;
        float endX = 20;
        GameMapLinear map = new GameMapLinear(0, 100, startX, endX);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testCreateStartXEqualToEndX() {
        float startX = 100;
        float endX = startX;
        GameMapLinear map = new GameMapLinear(0, 100, startX, endX);
    }


    @org.junit.Test
    public void testGetYValuesTiltedMap() {
        float startX = 0;
        float mapHeight = 100;
        float inclination = 2.5f;
        GameMapLinear map = new GameMapLinear(inclination, mapHeight, startX, 500);
        List<Float> xValues = new ArrayList<>();
        xValues.add(0f);
        xValues.add(10f);
        xValues.add(50f);
        xValues.add(300f);
        xValues.add(499f);
        List<Float> yvalues = map.getYValues(xValues);
        Assert.assertEquals(yvalues.get(0), xValues.get(0) * inclination + mapHeight, 0.005);
        Assert.assertEquals(yvalues.get(1), xValues.get(1) * inclination + mapHeight, 0.005);
        Assert.assertEquals(yvalues.get(2), xValues.get(2) * inclination + mapHeight, 0.005);
        Assert.assertEquals(yvalues.get(3), xValues.get(3) * inclination + mapHeight, 0.005);
        Assert.assertEquals(yvalues.get(4), xValues.get(4) * inclination + mapHeight, 0.005);


    }

    @org.junit.Test
    public void testIsWithinIsWithin() {
        float startX = 10;
        float endX = 20;
        GameMapLinear map = new GameMapLinear(0, 100, startX, endX);
        Assert.assertTrue(map.isWithin(15));
    }
    @org.junit.Test
    public void testIsWithinIsNotWithin() {
        float startX = 10;
        float endX = 20;
        GameMapLinear map = new GameMapLinear(0, 100, startX, endX);
        Assert.assertFalse(map.isWithin(25));
    }

    @org.junit.Test
    public void testIsWithinEdge1() {
        float startX = 10;
        float endX = 20;
        GameMapLinear map = new GameMapLinear(0, 100, startX, endX);
        Assert.assertTrue(map.isWithin(10));
    }

    @org.junit.Test
    public void testIsWithinEdge2() {
        float startX = 10;
        float endX = 20;
        GameMapLinear map = new GameMapLinear(0, 100, startX, endX);
        Assert.assertFalse(map.isWithin(20));
    }

    @org.junit.Test
    public void testSetEndX() {
        float startX = 0;
        float endX = 300;
        GameMapLinear map = new GameMapLinear(0, 100, startX, endX);
        float newEndX = 20;
        map.setEndX(newEndX);
        Assert.assertEquals(map.getEndX(), newEndX, 0.005);
    }

    @org.junit.Test
    public void testSetStartX() {
        float startX = 0;
        float endX = 300;
        GameMapLinear map = new GameMapLinear(0, 100, startX, endX);
        float newStartX = 20;
        map.setStartX(newStartX);
        Assert.assertEquals(map.getStartX(), newStartX, 0.005);
    }

    @Test
    public void testIsOnlyWithin(){
        float startX = 10;
        float endX = 20;
        GameMapLinear map = new GameMapLinear(0, 100, startX, endX);
        Assert.assertTrue(map.existsOnlyWithinRange(5,25));
    }

    @Test
    public void testIsOnlyWithinWithOut(){
        float startX = 10;
        float endX = 20;
        GameMapLinear map = new GameMapLinear(0, 100, startX, endX);
        Assert.assertFalse(map.existsOnlyWithinRange(5,8));
    }

    @Test
    public void testIsOnlyWithinHalfOverlap(){
        float startX = 10;
        float endX = 20;
        GameMapLinear map = new GameMapLinear(0, 100, startX, endX);
        Assert.assertFalse(map.existsOnlyWithinRange(5,15));
    }

    @Test
    public void testIsOnlyWithinOverlap(){
        float startX = 10;
        float endX = 20;
        GameMapLinear map = new GameMapLinear(0, 100, startX, endX);
        Assert.assertTrue(map.existsOnlyWithinRange(10,20));
    }

    @Test
    public void testIsOnlyWithinEdge1(){
        float startX = 10;
        float endX = 20;
        GameMapLinear map = new GameMapLinear(0, 100, startX, endX);
        Assert.assertTrue(map.existsOnlyWithinRange(10,30));
    }

    @Test
    public void testIsOnlyWithinEdge2(){
        float startX = 10;
        float endX = 20;
        GameMapLinear map = new GameMapLinear(0, 100, startX, endX);
        Assert.assertTrue(map.existsOnlyWithinRange(5,20));
    }

    @Test
    public void testSplitIntoNewRangesPerfect(){
        float startX = 10;
        float endX = 20;
        GameMapLinear map = new GameMapLinear(0, 100, startX, endX);
        List<IGameMapFunction> splits = map.splitInTwoWithNewRanges(10,15,15,20);
        Assert.assertEquals(2, splits.size());
        Assert.assertEquals(startX, splits.get(0).getStartX(), 0.001);
        Assert.assertEquals(15, splits.get(0).getEndX(), 0.001);
        Assert.assertEquals(15, splits.get(1).getStartX(), 0.001);
        Assert.assertEquals(endX, splits.get(1).getEndX(), 0.001);
        Assert.assertEquals(map.getYValue(startX), splits.get(0).getYValue(startX), 0.001);
        Assert.assertEquals(map.getYValue(endX-0.001f), splits.get(1).getYValue(endX-00.1f), 0.001);
        Assert.assertEquals(map.getYValue(15), splits.get(0).getYValue(15-0.001f), 0.001);
        Assert.assertEquals(map.getYValue(15), splits.get(1).getYValue(15), 0.001);
    }

    @Test
    public void testSplitIntoNewRangesRangesSubsetTheFunction(){
        float startX = 12;
        float endX = 18;
        GameMapLinear map = new GameMapLinear(0, 100, 10, 20);
        List<IGameMapFunction> splits = map.splitInTwoWithNewRanges(12,15,15,18);
        Assert.assertEquals(2, splits.size());
        Assert.assertEquals(startX, splits.get(0).getStartX(), 0.001);
        Assert.assertEquals(15, splits.get(0).getEndX(), 0.001);
        Assert.assertEquals(15, splits.get(1).getStartX(), 0.001);
        Assert.assertEquals(endX, splits.get(1).getEndX(), 0.001);
        Assert.assertEquals(map.getYValue(startX), splits.get(0).getYValue(startX), 0.001);
        Assert.assertEquals(map.getYValue(endX), splits.get(1).getYValue(endX-0.001f), 0.001);
        Assert.assertEquals(map.getYValue(15), splits.get(0).getYValue(14.99f), 0.001);
        Assert.assertEquals(map.getYValue(15), splits.get(1).getYValue(15), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSplitIntoNewRangesRangesSuperSetTheFunction(){
        float startX = 8;
        float endX = 22;
        GameMapLinear map = new GameMapLinear(0, 100, 10, 20);
        List<IGameMapFunction> splits = map.splitInTwoWithNewRanges(8,15,15,22);
        Assert.assertEquals(2, splits.size());
        Assert.assertEquals(startX, splits.get(0).getStartX(), 0.001);
        Assert.assertEquals(15, splits.get(0).getEndX(), 0.001);
        Assert.assertEquals(15, splits.get(1).getStartX(), 0.001);
        Assert.assertEquals(endX, splits.get(1).getEndX(), 0.001);
        Assert.assertEquals(startX*0 +100, splits.get(0).getYValue(startX), 0.001);
        Assert.assertEquals(endX*0 + 100, splits.get(1).getYValue(endX-0.001f), 0.001);
        Assert.assertEquals(map.getYValue(15), splits.get(0).getYValue(14.99f), 0.001);
        Assert.assertEquals(map.getYValue(15), splits.get(1).getYValue(15), 0.001);
    }


    @Test
    public void testSplitIntoNewRangesNotConnect(){
        float startX = 10;
        float endX = 20;
        GameMapLinear map = new GameMapLinear(0, 100, 10, 20);
        List<IGameMapFunction> splits = map.splitInTwoWithNewRanges(10,12,17,20);
        Assert.assertEquals(2, splits.size());
        Assert.assertEquals(startX, splits.get(0).getStartX(), 0.001);
        Assert.assertEquals(12, splits.get(0).getEndX(), 0.001);
        Assert.assertEquals(17, splits.get(1).getStartX(), 0.001);
        Assert.assertEquals(endX, splits.get(1).getEndX(), 0.001);
        Assert.assertEquals(map.getYValue(startX), splits.get(0).getYValue(startX), 0.001);
        Assert.assertEquals(map.getYValue(endX-0.001f), splits.get(1).getYValue(endX-0.001f), 0.001);
        Assert.assertEquals(map.getYValue(12), splits.get(0).getYValue(11.99f), 0.001);
        Assert.assertEquals(map.getYValue(17), splits.get(1).getYValue(17), 0.001);
    }


}
