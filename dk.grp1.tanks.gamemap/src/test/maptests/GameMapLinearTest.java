package maptests;

import dk.grp1.tanks.gamemap.internal.GameMapLinear;
import org.junit.Assert;

public class GameMapLinearTest {

    @org.junit.Test
    public void testGetStartX(){
        float startX = 0;
        GameMapLinear map = new GameMapLinear(0,100,startX,500);
        Assert.assertEquals(map.getStartX(), startX, 0.005);
    }

    @org.junit.Test
    public void testGetYValueFlatMap(){
        float startX = 0;
        float mapHeight = 100;
        GameMapLinear map = new GameMapLinear(0,mapHeight,startX,500);
        Assert.assertEquals(map.getYValue(0), mapHeight, 0.005);
        Assert.assertEquals(map.getYValue(10), mapHeight, 0.005);
        Assert.assertEquals(map.getYValue(50), mapHeight, 0.005);
        Assert.assertEquals(map.getYValue(300), mapHeight, 0.005);
        Assert.assertEquals(map.getYValue(500), mapHeight, 0.005);


    }

    @org.junit.Test
    public void testGetYValueTiltedMap(){
        float startX = 0;
        float mapHeight = 100;
        float inclination = 2.5f;
        GameMapLinear map = new GameMapLinear(inclination,mapHeight,startX,500);
        Assert.assertEquals(map.getYValue(0), mapHeight, 0.005);
        Assert.assertEquals(map.getYValue(10), 10*inclination+mapHeight, 0.005);
        Assert.assertEquals(map.getYValue(50),50*inclination+ mapHeight, 0.005);
        Assert.assertEquals(map.getYValue(300),300*inclination+ mapHeight, 0.005);
        Assert.assertEquals(map.getYValue(500),500*inclination+ mapHeight, 0.005);


    }

    @org.junit.Test
    public void testGetEndX(){
        float startX = 0;
        float endX = 300;
        GameMapLinear map = new GameMapLinear(0,100,startX,endX);
        Assert.assertEquals(map.getEndX(), endX, 0.005);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testCreateStartXgreaterThanEndX(){
        float startX = 100;
        float endX = 20;
        GameMapLinear map = new GameMapLinear(0,100,startX,endX);
    }
}
