package dk.grp1.tanks.gamemap.internal;

import dk.grp1.tanks.common.data.IGameMapFunction;

import java.util.ArrayList;
import java.util.List;

public class GameMapHalfCircle implements IGameMapFunction {
    private float startX;
    private float endX;
    private float centerX;
    private float centerY;
    private float radius;





    @Override
    public float getStartX() {
        return startX;
    }

    @Override
    public float getYValue(float xValue) {
        return 0;
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
}
