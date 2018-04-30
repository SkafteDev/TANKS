package AITest;

import dk.grp1.tanks.common.data.IGameMapFunction;

import java.util.List;

public class TestGameMapFunction implements IGameMapFunction {
    @Override
    public float getStartX() {
        return 0;
    }

    @Override
    public float getYValue(float xValue) {
        return 50;
    }

    @Override
    public float getEndX() {
        return 800;
    }

    @Override
    public List<Float> getYValues(List<Float> xValues) {
        return null;
    }

    @Override
    public boolean isWithin(float x) {
        return getStartX() <= x && getEndX() > x;
    }

    @Override
    public void setEndX(float value) {

    }

    @Override
    public void setStartX(float value) {

    }

    @Override
    public boolean existsOnlyWithinRange(float startX, float endX) {
        return false;
    }

    @Override
    public List<IGameMapFunction> splitInTwoWithNewRanges(float rangeOneStartX, float rangeOneEndX, float rangeTwoStartX, float rangeTwoEndX) {
        return null;
    }
}
