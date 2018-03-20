package dk.grp1.tanks.gamemap.internal;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.IGameMapFunction;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.events.ExplosionEvent;
import dk.grp1.tanks.common.services.IEntityProcessingService;
import dk.grp1.tanks.common.services.INonEntityProcessingService;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class GameMapProcessing implements INonEntityProcessingService {
    @Override
    public void process(World world, GameData gameData) {
<<<<<<< Updated upstream


    }


    private List<Vector2D> calculateIntersectionPointsWithMap(ExplosionEvent event, World world) {
        List<Vector2D> intersectionPoints = new ArrayList<>();

        float centerX = event.getPointOfCollision().getX();
        float centerY = event.getPointOfCollision().getY();
        float radius = event.getExplosionRadius();

        IGameMapFunction negativeHalf = new GameMapNegativeHalfCircle(centerX - radius, centerX + radius, centerX, centerY, radius);
        IGameMapFunction positiveHalf = new GameMapPositiveHalfCircle(centerX - radius, centerX + radius, centerX, centerY, radius);

        List<Float> intersectionXValuesPositive = new ArrayList<>();
        List<Float> intersectionXValuesNegative = new ArrayList<>();


        float resultLeftHalf = -1;
        for (IGameMapFunction mapFunction : world.getGameMap().getGameMapFunctions()) {

            resultLeftHalf = intersect(mapFunction, negativeHalf, 1f, centerX - radius, centerX);
            if (resultLeftHalf != -1) {
                intersectionXValuesNegative.add(resultLeftHalf);
                break;
            }
        }

        if (resultLeftHalf == -1) {
            for (IGameMapFunction mapFunction : world.getGameMap().getGameMapFunctions()) {

                resultLeftHalf = intersect(mapFunction, positiveHalf, 1f, centerX - radius, centerX);
                if (resultLeftHalf != -1) {
                    intersectionXValuesPositive.add(resultLeftHalf);
                    break;
                }
            }
        }


        float resultRightHalf = -1;
        for (IGameMapFunction mapFunction : world.getGameMap().getGameMapFunctions()) {

            resultRightHalf = intersect(mapFunction, negativeHalf, 1f, centerX, centerX + radius);
            if (resultRightHalf != -1) {
                intersectionXValuesNegative.add(resultRightHalf);
                break;
            }
        }

        if (resultRightHalf == -1) {
            for (IGameMapFunction mapFunction : world.getGameMap().getGameMapFunctions()) {

                resultRightHalf = intersect(mapFunction, positiveHalf, 1f, centerX, centerX + radius);
                if (resultRightHalf != -1) {
                    intersectionXValuesPositive.add(resultRightHalf);
                    break;
                }
            }
        }

        if (intersectionXValuesNegative.size() + intersectionXValuesNegative.size() == 2){
            for (Float xValue : intersectionXValuesNegative) {
                intersectionPoints.add(new Vector2D(xValue,negativeHalf.getYValue(xValue)));
            }

            for (Float xValue : intersectionXValuesPositive) {
                intersectionPoints.add(new Vector2D(xValue,positiveHalf.getYValue(xValue)));
            }
        }

        return intersectionPoints;
=======
        
>>>>>>> Stashed changes
    }


    private float intersect(IGameMapFunction mapFunction, IGameMapFunction halfCircle, float acceptInterval, float rangeStart, float rangeEnd) {

        for (float x = rangeStart; x <= rangeEnd; x += acceptInterval) {

            if (mapFunction.isWithin(x)) {
                float yMapFunction = mapFunction.getYValue(x);
                float yHalfCircle = halfCircle.getYValue(x);

                if (Math.abs(yMapFunction - yHalfCircle) < acceptInterval) {
                    return x;
                }
            }

        }

        // TODO: Vi skal overveje om det er korrekt at returnere dette.
        return -1;
    }

}
