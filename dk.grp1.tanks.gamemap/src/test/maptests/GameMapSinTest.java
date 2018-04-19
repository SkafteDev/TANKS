package maptests;

import dk.grp1.tanks.common.data.IGameMapFunction;
import dk.grp1.tanks.gamemap.internal.GameMapSin;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class GameMapSinTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNull(){
        GameMapSin map = new GameMapSin(1,1,1,null,10,20);

    }
    @Test
    public void testGetStartX() {
        float startX = 0;
        GameMapSin map = new GameMapSin(1,1,1,1,startX,20);
        Assert.assertEquals(map.getStartX(), startX, 0.005);
    }



    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testGetYValueOutsideRange() {
        float startX = 0;
        float mapHeight = 100;
        GameMapSin map = new GameMapSin(100,1,1,1,startX,20);
        Assert.assertEquals(map.getYValue(-2), -83.14709848, 0.005);

    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testGetYValueOutsideRange2() {
        float startX = 0;
        float mapHeight = 100;
        GameMapSin map = new GameMapSin(1,1,1,1,startX,20);
        Assert.assertEquals(map.getYValue(30), mapHeight, 0.005);



    }

    @org.junit.Test
    public void testGetYValue() {
        float startX = 0;
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,startX,500);
        Assert.assertEquals(map.getYValue(0), 85.14709848, 0.005);
        Assert.assertEquals(map.getYValue(10),-98.99902066, 0.005);
        Assert.assertEquals(map.getYValue(50), 68.02291758, 0.005);
        Assert.assertEquals(map.getYValue(300), -54.87640496, 0.005);
        Assert.assertEquals(map.getYValue(499), -45.77718053, 0.005);


    }

    @org.junit.Test
    public void testGetEndX() {
        float startX = 0;
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        float endX = 300;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,startX,endX);
        Assert.assertEquals(map.getEndX(), endX, 0.005);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testCreateStartXgreaterThanEndX() {
        float startX = 100;
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        float endX = 50;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,startX,endX);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testCreateStartXEqualToEndX() {
        float startX = 300;
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        float endX = 300;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,startX,endX);
    }


    @org.junit.Test
    public void testGetYValuesTiltedMap() {
        float startX = 0;
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        float endX = 500;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,startX,endX);
        List<Float> xValues = new ArrayList<>();
        xValues.add(0f);
        xValues.add(10f);
        xValues.add(50f);
        xValues.add(300f);
        xValues.add(499f);
        List<Float> yvalues = map.getYValues(xValues);
        Assert.assertEquals(yvalues.get(0), 85.14709848, 0.005);
        Assert.assertEquals(yvalues.get(1),-98.99902066, 0.005);
        Assert.assertEquals(yvalues.get(2), 68.02291758, 0.005);
        Assert.assertEquals(yvalues.get(3), -54.87640496, 0.005);
        Assert.assertEquals(yvalues.get(4), -45.77718053, 0.005);

    }

    @org.junit.Test
    public void testIsWithinIsWithin() {
        float startX = 10;
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        float endX = 20;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,startX,endX);
        Assert.assertTrue(map.isWithin(15));
    }
    @org.junit.Test
    public void testIsWithinIsNotWithin() {
        float startX = 10;
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        float endX = 20;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,startX,endX);
        Assert.assertFalse(map.isWithin(25));
    }

    @org.junit.Test
    public void testIsWithinEdge1() {
        float startX = 10;
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        float endX = 20;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,startX,endX);
        Assert.assertTrue(map.isWithin(10));
    }

    @org.junit.Test
    public void testIsWithinEdge2() {
        float startX = 0;
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        float endX = 20;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,startX,endX);
        Assert.assertFalse(map.isWithin(20));
    }

    @org.junit.Test
    public void testSetEndX() {
        float startX = 0;
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        float endX = 200;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,startX,endX);
        float newEndX = 20;
        map.setEndX(newEndX);
        Assert.assertEquals(map.getEndX(), newEndX, 0.005);
    }

    @org.junit.Test(expected=IllegalArgumentException.class)
    public void testSetEndXLessThanStartX() {
        float startX = 0;
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        float endX = 200;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,startX,endX);
        float newEndX = -5;
        map.setEndX(newEndX);
        Assert.assertEquals(map.getEndX(), newEndX, 0.005);
    }

    @org.junit.Test
    public void testSetStartX() {
        float startX = 0;
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        float endX = 20;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,startX,endX);
        float newStartX = 10;
        map.setStartX(newStartX);
        Assert.assertEquals(map.getStartX(), newStartX, 0.005);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetStartXGreaterThanEndX(){
        float startX = 0;
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        float endX = 20;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,startX,endX);
        float newStartX = 25;
        map.setStartX(newStartX);
        Assert.assertEquals(map.getStartX(), newStartX, 0.005);
    }

    @Test
    public void testIsOnlyWithin(){
        float startX = 10;
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        float endX = 20;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,startX,endX);
        Assert.assertTrue(map.existsOnlyWithinRange(5,25));
    }

    @Test
    public void testIsOnlyWithinWithOut(){
        float startX = 10;
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        float endX = 20;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,startX,endX);
        Assert.assertFalse(map.existsOnlyWithinRange(5,8));
    }

    @Test
    public void testIsOnlyWithinHalfOverlap(){
        float startX = 10;
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        float endX = 20;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,startX,endX);
        Assert.assertFalse(map.existsOnlyWithinRange(5,15));
    }

    @Test
    public void testIsOnlyWithinOverlap(){
        float startX = 10;
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        float endX = 20;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,startX,endX);
        Assert.assertTrue(map.existsOnlyWithinRange(10,20));
    }

    @Test
    public void testIsOnlyWithinEdge1(){
        float startX = 10;
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        float endX = 20;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,startX,endX);
        Assert.assertTrue(map.existsOnlyWithinRange(10,30));
    }

    @Test
    public void testIsOnlyWithinEdge2(){
        float startX = 10;
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        float endX = 20;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,startX,endX);
        Assert.assertTrue(map.existsOnlyWithinRange(5,20));
    }

    @Test
    public void testSplitIntoNewRangesPerfect(){
        float startX = 10;
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        float endX = 20;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,startX,endX);
        List<IGameMapFunction> splits = map.splitInTwoWithNewRanges(10,15,15,20);
        Assert.assertEquals(2, splits.size());
        Assert.assertEquals(startX, splits.get(0).getStartX(), 0.001);
        Assert.assertEquals(15, splits.get(0).getEndX(), 0.001);
        Assert.assertEquals(15, splits.get(1).getStartX(), 0.001);
        Assert.assertEquals(endX, splits.get(1).getEndX(), 0.001);
        Assert.assertEquals(map.getYValue(startX), splits.get(0).getYValue(startX), 0.001);
        Assert.assertEquals(map.getYValue(endX-0.001f), splits.get(1).getYValue(endX-0.001f), 0.001);
        Assert.assertEquals(map.getYValue(15-0.001f), splits.get(0).getYValue(15-0.001f), 0.001);
        Assert.assertEquals(map.getYValue(15), splits.get(1).getYValue(15), 0.001);
    }

    @Test
    public void testSplitIntoNewRangesRangesSubsetTheFunction(){
        float startX = 12;
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        float endX =18;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,10,20);
        List<IGameMapFunction> splits = map.splitInTwoWithNewRanges(12,15,15,18);
        Assert.assertEquals(2, splits.size());
        Assert.assertEquals(startX, splits.get(0).getStartX(), 0.001);
        Assert.assertEquals(15, splits.get(0).getEndX(), 0.001);
        Assert.assertEquals(15, splits.get(1).getStartX(), 0.001);
        Assert.assertEquals(endX, splits.get(1).getEndX(), 0.001);
        Assert.assertEquals(map.getYValue(startX), splits.get(0).getYValue(startX), 0.001);
        Assert.assertEquals(map.getYValue(endX-0.001f), splits.get(1).getYValue(endX-0.001f), 0.001);
        Assert.assertEquals(map.getYValue(14.99f), splits.get(0).getYValue(14.99f), 0.001);
        Assert.assertEquals(map.getYValue(15), splits.get(1).getYValue(15), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSplitIntoNewRangesRangesSuperSetTheFunction(){
        float startX = 8;
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        float endX = 22;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,10,20);
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
        float amp = 100;
        float angFreq = 1;
        float phaseShift = 1;
        float phaseConst=1;
        float endX = 20;
        GameMapSin map = new GameMapSin(amp,angFreq,phaseShift,phaseConst,startX,endX);
        List<IGameMapFunction> splits = map.splitInTwoWithNewRanges(10,12,17,20);
        Assert.assertEquals(2, splits.size());
        Assert.assertEquals(startX, splits.get(0).getStartX(), 0.001);
        Assert.assertEquals(12, splits.get(0).getEndX(), 0.001);
        Assert.assertEquals(17, splits.get(1).getStartX(), 0.001);
        Assert.assertEquals(endX, splits.get(1).getEndX(), 0.001);
        Assert.assertEquals(map.getYValue(startX), splits.get(0).getYValue(startX), 0.001);
        Assert.assertEquals(map.getYValue(endX-0.001f), splits.get(1).getYValue(endX-0.001f), 0.001);
        Assert.assertEquals(map.getYValue(11.99f), splits.get(0).getYValue(11.99f), 0.001);
        Assert.assertEquals(map.getYValue(17), splits.get(1).getYValue(17), 0.001);
    }


}
