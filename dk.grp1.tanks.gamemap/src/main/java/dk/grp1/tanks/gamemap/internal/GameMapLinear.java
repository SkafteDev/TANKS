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
        this.startX = startX;
        this.endX = endX;
    }

    public GameMapLinear(float a, float startX, float endX, IGameMapFunction sucessorFn) {
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
        splitGameMapFunctions.add(new GameMapLinear(this.a,this.b,rangeOneStartX,rangeOneEndX));
        splitGameMapFunctions.add(new GameMapLinear(this.a,this.b,rangeTwoStartX,rangeTwoEndX));
        return splitGameMapFunctions;
    }

    @Override
    public int compareTo(Object o) {
        IGameMapFunction other = (IGameMapFunction) o;
        if(other == null){
            return -1;
        }
        if(this.getStartX() < other.getStartX()){
            return 1;
        }
        if(this.getStartX() == other.getStartX()){
            if(this.getEndX() < other.getEndX()){
                return 1;
            }else if(this.getEndX() == other.getEndX()){
                return 0;
            }
        }
        return -1;
    }
}
