package dk.grp1.tanks.gamemap.internal;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.IGameMapFunction;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.events.Event;
import dk.grp1.tanks.common.events.ExplosionEvent;
import dk.grp1.tanks.common.services.INonEntityProcessingService;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class GameMapProcessing implements INonEntityProcessingService {
    @Override
    public void process(World world, GameData gameData) {
        for (Event event : gameData.getEvents(ExplosionEvent.class)) {
            List<Vector2D> intersectionPoints = calculateIntersectionPointsWithMap(event, world);
            replacePartsOfMapWithCircles(intersectionPoints, world, event);
            gameData.removeEvent(event);
        }


    }

    private void replacePartsOfMapWithCircles(List<Vector2D> intersectionPoints, World world, Event event) {
        //Setup Part of the function
        if (intersectionPoints.size() != 2) {
            return;
        }
        //From left to right
        Vector2D firstPoint = intersectionPoints.get(0);
        Vector2D secondPoint = intersectionPoints.get(1);
        if(firstPoint.getX() > secondPoint.getX()){
            Vector2D tmp = firstPoint;
            firstPoint = secondPoint;
            secondPoint = tmp;
        }
        System.out.println(firstPoint.toString());
        System.out.println(secondPoint.toString());

        ExplosionEvent explosionEvent = (ExplosionEvent) event;

        IGameMapFunction startFunc = null;
        IGameMapFunction endFunc = null;

        float centerX = explosionEvent.getPointOfCollision().getX();
        float centerY = explosionEvent.getPointOfCollision().getY();
        float radius = explosionEvent.getExplosionRadius();

        IGameMapFunction negativeHalf = new GameMapNegativeHalfCircle(centerX - radius, centerX + radius, centerX, centerY, radius);
        IGameMapFunction positiveHalf = new GameMapPositiveHalfCircle(centerX - radius, centerX + radius, centerX, centerY, radius);
        //Setup done

        //Find the function before the start of the circle and the function after the end of the circle.
        for (IGameMapFunction gameMapFunction : world.getGameMap().getGameMapFunctions()) {
            if (gameMapFunction.isWithin(firstPoint.getX())) {
                startFunc = gameMapFunction;
            }
            if (gameMapFunction.isWithin(secondPoint.getX())) {
                endFunc = gameMapFunction;
            }
        }
        //TODO remove any function that are only within the circle

        //If the start and end functions are 2 separate functions. Then just change where they end and start.
        if (!startFunc.equals(endFunc)) {
            if (startFunc != null) {
                startFunc.setEndX(firstPoint.getX());
            }
            if (endFunc != null) {
                endFunc.setStartX(secondPoint.getX());
            }
        } else {
            //If the start and end is the same function, then split the function into two new identical functions with different ranges.
            // Call splitInTwoWithNewRanges(range1startx,range1endx,range2startx,range2endx) on the function.
            List<IGameMapFunction> splitMapFunctions = startFunc.splitInTwoWithNewRanges(startFunc.getStartX(), firstPoint.getX(), secondPoint.getX(), endFunc.getEndX());
            world.getGameMap().getGameMapFunctions().remove(startFunc);
            for (IGameMapFunction splitMapFunction : splitMapFunctions) {
                world.getGameMap().addGameMapFunction(splitMapFunction);
            }
        }
        //If it is above center Y it need to take the positive half circle from centerX-Radius to intersectionpoint.X
        //If it is below center Y it needs to take the  negative half circle from intersectionpoint.X to either intersectionpoint2.X or centerX+Radius depending on whether intersectionpoint2.Y is below or below centerY


        if (firstPoint.getY() > centerY) {
            //The circle needs to be at the correct point in the list of gamemap functions. Make method in gameMap to insert based on range.
            if (secondPoint.getY() <= centerY) {
                positiveHalf.setStartX(centerX - radius);
                positiveHalf.setEndX(firstPoint.getX());
                negativeHalf.setStartX(centerX - radius);
                negativeHalf.setEndX(secondPoint.getX());
            } else {
                positiveHalf.setStartX(firstPoint.getX());
                positiveHalf.setEndX(secondPoint.getX());
                negativeHalf.setStartX(0f);
                negativeHalf.setEndX(0f);
            }

        }
        if (firstPoint.getY() <= centerY) {
            if (secondPoint.getY() > centerY) {
                negativeHalf.setStartX(firstPoint.getX());
                negativeHalf.setEndX(centerX + radius);
                positiveHalf.setStartX(secondPoint.getX());
                positiveHalf.setEndX(centerX + radius);
            } else {
                negativeHalf.setStartX(firstPoint.getX());
                negativeHalf.setEndX(secondPoint.getX());
                positiveHalf.setStartX(0f);
                positiveHalf.setEndX(0f);
            }
        }
        world.getGameMap().addGameMapFunction(negativeHalf);
        world.getGameMap().addGameMapFunction(positiveHalf);

    }


    private List<Vector2D> calculateIntersectionPointsWithMap(Event event, World world) {
        List<Vector2D> intersectionPoints = new ArrayList<>();
        ExplosionEvent explosionEvent = (ExplosionEvent) event;
        float centerX = explosionEvent.getPointOfCollision().getX();
        float centerY = explosionEvent.getPointOfCollision().getY();
        float radius = explosionEvent.getExplosionRadius();
        System.out.println("Center of circle: " + centerX + ", " + centerY + " Radius: " + radius);

        IGameMapFunction negativeHalf = new GameMapNegativeHalfCircle(centerX - radius, centerX + radius, centerX, centerY, radius);
        IGameMapFunction positiveHalf = new GameMapPositiveHalfCircle(centerX - radius, centerX + radius, centerX, centerY, radius);

        List<Float> intersectionXValuesPositive = new ArrayList<>();
        List<Float> intersectionXValuesNegative = new ArrayList<>();
        float acceptInterval = 0.2f;

        float resultLeftHalf = -1;
        for (IGameMapFunction mapFunction : world.getGameMap().getGameMapFunctions()) {

            resultLeftHalf = intersect(mapFunction, negativeHalf, acceptInterval, centerX - radius, centerX);
            if (resultLeftHalf != -1) {
                intersectionXValuesNegative.add(resultLeftHalf);
                break;
            }
        }

        if (resultLeftHalf == -1) {
            for (IGameMapFunction mapFunction : world.getGameMap().getGameMapFunctions()) {

                resultLeftHalf = intersect(mapFunction, positiveHalf, acceptInterval, centerX - radius, centerX);
                if (resultLeftHalf != -1) {
                    intersectionXValuesPositive.add(resultLeftHalf);
                    break;
                }
            }
        }


        float resultRightHalf = -1;
        for (IGameMapFunction mapFunction : world.getGameMap().getGameMapFunctions()) {

            resultRightHalf = intersect(mapFunction, negativeHalf, acceptInterval, centerX, centerX + radius);
            if (resultRightHalf != -1) {
                intersectionXValuesNegative.add(resultRightHalf);
                break;
            }
        }

        if (resultRightHalf == -1) {
            for (IGameMapFunction mapFunction : world.getGameMap().getGameMapFunctions()) {

                resultRightHalf = intersect(mapFunction, positiveHalf, acceptInterval, centerX, centerX + radius);
                if (resultRightHalf != -1) {
                    intersectionXValuesPositive.add(resultRightHalf);
                    break;
                }
            }
        }

        if (intersectionXValuesNegative.size() + intersectionXValuesNegative.size() == 2) {
            for (Float xValue : intersectionXValuesNegative) {
                intersectionPoints.add(new Vector2D(xValue, negativeHalf.getYValue(xValue)));
            }

            for (Float xValue : intersectionXValuesPositive) {
                intersectionPoints.add(new Vector2D(xValue, positiveHalf.getYValue(xValue)));
            }
        }

        return intersectionPoints;
    }


    private float intersect(IGameMapFunction mapFunction, IGameMapFunction halfCircle, float acceptInterval, float rangeStart, float rangeEnd) {

        for (float x = rangeStart; x <= rangeEnd; x += 0.1f) {

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
