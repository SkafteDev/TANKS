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
        this (startX, endX, new Vector2D(centerX, centerY), radius);
    }

    public GameMapNegativeHalfCircle(float startX, float endX, Vector2D center, float radius) {
        if (center == null){
            throw new IllegalArgumentException("Vector to centre may not be null");
        }


        float distanceToEnd = Math.abs(endX - center.getX());
        if (radius - (distanceToEnd) < -0.001f){
            throw new IllegalArgumentException("Radius cannot be shorter than distance from centre to end");
        }

        float distanceFromStart = Math.abs(center.getX() - center.getX());
        if (radius < (distanceFromStart)){
            throw new IllegalArgumentException("Radius cannot be shorter than distance from start to centre");
        }

        if (startX > endX){
            throw new IllegalArgumentException("StartX cannot be greater than endX");
        }

        this.startX = startX;
        this.endX = endX;
        this.centerX = center.getX();
        this.centerY = center.getY();
        this.radius = radius;
    }


    @Override
    public float getStartX() {
        return startX;
    }

    @Override
    public float getYValue(float xValue) {
        if (xValue < startX || xValue > endX){
            throw new IllegalArgumentException("X value must be within the function's bounds");
        }
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
        if(this.startX <= x && this.endX > x){
            return true;
        }
        return false;
    }

    @Override
    public void setEndX(float value) {
        if (value < startX){
            throw new IllegalArgumentException("End point must be greater than start point");
        }
        this.endX = value;
    }

    @Override
    public void setStartX(float value) {
        if (value > endX){
            throw new IllegalArgumentException("Start point must be less than end point");
        }
        this.startX = value;
    }

    @Override
    public boolean existsOnlyWithinRange(float startX, float endX) {
        return (this.startX >= startX && this.endX <= endX);
    }

    @Override
    public List<IGameMapFunction> splitInTwoWithNewRanges(float rangeOneStartX, float rangeOneEndX, float rangeTwoStartX, float rangeTwoEndX) {
        if( rangeOneStartX < this.startX || rangeOneStartX > rangeOneEndX ||
                rangeOneEndX > rangeTwoStartX || rangeTwoStartX > rangeTwoEndX || rangeTwoEndX > this.endX){
            throw new IllegalArgumentException("The ranges must be within the range of the original function");
        }
        List<IGameMapFunction> splitGameMapFunctions = new ArrayList<>();
        splitGameMapFunctions.add(new GameMapNegativeHalfCircle(rangeOneStartX,rangeOneEndX,this.centerX,this.centerY,this.radius));
        splitGameMapFunctions.add(new GameMapNegativeHalfCircle(rangeTwoStartX,rangeTwoEndX,this.centerX,this.centerY,this.radius));
        return splitGameMapFunctions;
    }

}
