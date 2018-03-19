package dk.grp1.tanks.gamemap.internal;

import dk.grp1.tanks.common.data.IGameMapFunction;

import java.util.List;

public class FlatGameMap implements IGameMapFunction {


    @Override
    public float getStartX() {
        return 0;
    }

    @Override
    public float getYValue(float xValue) {
        return 0;
    }

    @Override
    public float getEndX() {
        return 0;
    }

    @Override
    public List<Float> getYValues(List<Float> xValues) {
        return null;
    }

    @Override
    public boolean isWithin(float x) {
        return false;
    }
}
