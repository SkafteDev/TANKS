package dk.grp1.tanks.gamemap.internal;

import dk.grp1.tanks.common.data.IGameMapFunction;
import dk.grp1.tanks.common.utils.Vector2D;


import java.util.ArrayList;
import java.util.List;

public class GameMapLinear implements IGameMapFunction {

    private float a;
    private float b;
    private float startX;
    private float endX;

    /**
     *
     * @param a
     * @param b
     * @param startX
     * @param endX
     */
    public GameMapLinear(float a, float b, float startX, float endX) {

        this.a = a;
        this.b = b;
        if (endX <= startX){
            throw new IllegalArgumentException("endX must be greater than startX ");
        }
        this.startX = startX;
        this.endX = endX;
    }

    public GameMapLinear(float a, float startX, float endX, IGameMapFunction sucessorFn) {
        if(sucessorFn == null){
            throw new IllegalArgumentException("Successor function may not be Null");
        }
        float y = sucessorFn.getYValue(sucessorFn.getEndX());

        //Calculate b value
        float b = y - a * startX;

        this.a = a;
        this.b = b;
        this.startX = startX;
        this.endX = endX;
    }

    @Override
    public float getStartX() {
        return startX;
    }

    @Override
    public float getYValue(float xValue) {
        if(!(xValue >= startX && xValue <= endX)){
            throw new IllegalArgumentException("xValue must be within the function range");
        }
        return a * xValue + b;
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
        this.endX = value;
    }

    @Override
    public void setStartX(float value) {
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
        splitGameMapFunctions.add(new GameMapLinear(this.a,this.b,rangeOneStartX,rangeOneEndX));
        splitGameMapFunctions.add(new GameMapLinear(this.a,this.b,rangeTwoStartX,rangeTwoEndX));
        return splitGameMapFunctions;
    }

    @Override
    public String toString() {
        return "GameMapLinear{" +
                "a= " + a +
                ", b= " + b +
                ", startX= " + startX +
                ", endX= " + endX +
                '}';
    }
}
