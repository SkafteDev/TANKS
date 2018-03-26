package dk.grp1.tanks.gamemap.internal;

import dk.grp1.tanks.common.data.IGameMapFunction;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class GameMapNegativeHalfCircle implements IGameMapFunction {
    private float startX;
    private float endX;
    private float centerX;
    private float centerY;
    private float radius;

    public GameMapNegativeHalfCircle(float startX, float endX, float centerX, float centerY, float radius) {
        this.startX = startX;
        this.endX = endX;
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }
    public GameMapNegativeHalfCircle(float startX, float endX, Vector2D center, float radius) {
        this(startX,endX,center.getX(),center.getY(),radius);
    }


    @Override
    public float getStartX() {
        return startX;
    }

    @Override
    public float getYValue(float xValue) {
        float toSqrt = (-(centerX*centerX)+ 2 * xValue * centerX + (radius*radius) - (xValue*xValue));
        return (float) (centerY - Math.sqrt(toSqrt));
    }

    @Override
    public float getEndX() {
        return endX;
    }

    @Override
    public List<Float> getYValues(List<Float> xValues) {
        List<Float> yValues = new ArrayList<>();
        for (Float xValue : xValues) {
            yValues.add(getYValue(xValue));
        }
        return yValues;
    }

    @Override
    public boolean isWithin(float x) {
        if(this.startX < x && this.endX > x){
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
        return (this.startX > startX && this.endX < endX);
    }

    @Override
    public List<IGameMapFunction> splitInTwoWithNewRanges(float rangeOneStartX, float rangeOneEndX, float rangeTwoStartX, float rangeTwoEndX) {
        List<IGameMapFunction> splitGameMapFunctions = new ArrayList<>();
        splitGameMapFunctions.add(new GameMapNegativeHalfCircle(rangeOneStartX,rangeOneEndX,this.centerX,this.centerY,this.radius));
        splitGameMapFunctions.add(new GameMapNegativeHalfCircle(rangeTwoStartX,rangeTwoEndX,this.centerX,this.centerY,this.radius));
        return splitGameMapFunctions;
    }

}
