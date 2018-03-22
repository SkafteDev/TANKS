package dk.grp1.tanks.gamemap.internal;

import dk.grp1.tanks.common.data.IGameMapFunction;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class GameMapPositiveHalfCircle implements IGameMapFunction {
    private float startX;
    private float endX;
    private float centerX;
    private float centerY;
    private float radius;

    public GameMapPositiveHalfCircle(float startX, float endX, float centerX, float centerY, float radius) {
        this.startX = startX;
        this.endX = endX;
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    @Override
    public float getStartX() {
        return startX;
    }

    @Override
    public float getYValue(float xValue) {
        return (float) (centerY + Math.sqrt(-(Math.pow(centerX,2))+ 2 * xValue * centerX + Math.pow(radius,2) - Math.pow(xValue,2)));
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
        splitGameMapFunctions.add(new GameMapPositiveHalfCircle(rangeOneStartX,rangeOneEndX,this.centerX,this.centerY,this.radius));
        splitGameMapFunctions.add(new GameMapPositiveHalfCircle(rangeTwoStartX,rangeTwoEndX,this.centerX,this.centerY,this.radius));
        return splitGameMapFunctions;

    }


    @Override
    public int compareTo(Object o) {
        IGameMapFunction other = (IGameMapFunction) o;
        if(other == null){
            return -1;
        }
        if(this.getStartX() < other.getStartX()){
            return -1;
        }
        if(this.getStartX() == other.getStartX()){
            if(this.getEndX() < other.getEndX()){
                return -1;
            }else if(this.getEndX() == other.getEndX()){
                return 0;
            }
        }
        return 1;
    }
}
