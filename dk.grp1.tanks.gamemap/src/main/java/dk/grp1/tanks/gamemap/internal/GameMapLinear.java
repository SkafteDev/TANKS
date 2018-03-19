package dk.grp1.tanks.gamemap.internal;

import dk.grp1.tanks.common.data.IGameMapFunction;
import dk.grp1.tanks.common.utils.Vector2D;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

import java.util.ArrayList;
import java.util.List;

public class GameMapLinear implements IGameMapFunction {

    private Function linearFunction;
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
        this.linearFunction = new Function("f(x) = "+a+"*x+"+b);

        this.startX = startX;
        this.endX = endX;
    }

    public GameMapLinear(float a, float startX, float endX, IGameMapFunction sucessorFn) {
        float y = sucessorFn.getYValue(sucessorFn.getEndX());
        //Calculate b value
        float b = y - a * startX;
        //this.linearFunction = new Function("f(x) = "+a+"*x+"+b);
        this.linearFunction = new Function("f(x)=2*x+5");
        this.startX = startX;
        this.endX = endX;
    }

    @Override
    public float getStartX() {
        return startX;
    }

    @Override
    public float getYValue(float xValue) {
        double x = (double) xValue;
        System.out.println("X casted to double: " +x);
        System.out.println(linearFunction.checkSyntax());
       // Expression e = new Expression()
        double y = linearFunction.calculate(xValue);
        System.out.println("Linear Value for y: "+y);
        return (float)y;
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
    public List<Vector2D> intersectionWithCircle(Vector2D centerOfCircle, float radius) {
        return null;
    }
}
