package dk.grp1.tanks.gamemap.internal;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.IGameMapFunction;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.events.Event;
import dk.grp1.tanks.common.events.MapDestructionEvent;
import dk.grp1.tanks.common.services.INonEntityProcessingService;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class GameMapProcessing implements INonEntityProcessingService {
    @Override
    public void process(World world, GameData gameData) {
        for (Event event : gameData.getEvents(MapDestructionEvent.class)) {
            List<Vector2D> intersectionPoints = calculateIntersectionPointsWithMap(event, world);
            replacePartsOfMapWithCircles(intersectionPoints, world, event);
            gameData.removeEvent(event);
        }
    }

    private void replacePartsOfMapWithCircles(List<Vector2D> intersectionPoints, World world, Event event) {
        //Setup Part of the function
        if (intersectionPoints.size() != 2) {
            //System.out.println("List of intersection points size: " + intersectionPoints.size());
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

        MapDestructionEvent mapDestructionEvent = (MapDestructionEvent) event;

        float centerX = mapDestructionEvent.getPointOfCollision().getX();
        float centerY = mapDestructionEvent.getPointOfCollision().getY();
        float radius = mapDestructionEvent.getExplosionRadius();
        //Setup done

        fixFunctionsAroundCircle(world, firstPoint, secondPoint);
        removeFunctionsWithinCircle(world, centerX, radius);
        List<IGameMapFunction> functionsToAdd = generateCirclesToInsert(firstPoint, secondPoint, centerX, centerY, radius);
        world.getGameMap().getGameMapFunctions().addAll(functionsToAdd);

    }

    private List<IGameMapFunction> generateCirclesToInsert(Vector2D firstPoint, Vector2D secondPoint, float centerX, float centerY, float radius) {
        List<IGameMapFunction> functionsToInsert = new ArrayList<>();
        IGameMapFunction negativeHalf = new GameMapNegativeHalfCircle(centerX - radius, centerX + radius, centerX, centerY, radius);
        IGameMapFunction positiveHalf = new GameMapPositiveHalfCircle(centerX - radius, centerX + radius, centerX, centerY, radius);

        //If it is above center Y it need to take the positive half circle from centerX-Radius to intersectionpoint.X
        //If it is below center Y it needs to take the  negative half circle from intersectionpoint.X to either intersectionpoint2.X or centerX+Radius depending on whether intersectionpoint2.Y is below or below centerY
        if (firstPoint.getY() > centerY) {
            //The circle needs to be at the correct point in the list of gamemap functions. Make method in gameMap to insert based on range.
            if (secondPoint.getY() <= centerY) {
                positiveHalf.setStartX(centerX - radius);
                positiveHalf.setEndX(firstPoint.getX());
                negativeHalf.setStartX(centerX - radius);
                negativeHalf.setEndX(secondPoint.getX());
                functionsToInsert.add(negativeHalf);
                functionsToInsert.add(positiveHalf);
            } else {
                //Generate 2 new positive half circles
                List<IGameMapFunction> positiveHalves = positiveHalf.splitInTwoWithNewRanges(centerX-radius,firstPoint.getX(),secondPoint.getX(),centerX+radius);
                negativeHalf.setStartX(centerX-radius);
                negativeHalf.setEndX(centerX+radius);
                functionsToInsert.add(negativeHalf);
                for (IGameMapFunction gameMapFunction : positiveHalves) {
                    functionsToInsert.add(gameMapFunction);
                }
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
            functionsToInsert.add(negativeHalf);
            functionsToInsert.add(positiveHalf);

        }

        return functionsToInsert;
    }

    private void fixFunctionsAroundCircle(World world, Vector2D firstPoint, Vector2D secondPoint) {
        IGameMapFunction startFunc = null; IGameMapFunction endFunc = null;
        //Find the function before the start of the circle and the function after the end of the circle.
        for (IGameMapFunction gameMapFunction : world.getGameMap().getGameMapFunctions()) {
            if (gameMapFunction.isWithin(firstPoint.getX())) {
                startFunc = gameMapFunction;
            }
            if (gameMapFunction.isWithin(secondPoint.getX())) {
                endFunc = gameMapFunction;
            }
        }


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
    }

    private void removeFunctionsWithinCircle(World world, float centerX, float radius) {
        List<IGameMapFunction> gameMapFunctionsToRemove = new ArrayList<>();
        for (IGameMapFunction gameMapFunction : world.getGameMap().getGameMapFunctions()) {
            if(gameMapFunction.existsOnlyWithinRange(centerX-radius,centerX+radius)){
                gameMapFunctionsToRemove.add(gameMapFunction);
            }
        }

        for (IGameMapFunction gameMapFunction : gameMapFunctionsToRemove) {
            world.getGameMap().getGameMapFunctions().remove(gameMapFunction);
        }
    }


    private List<Vector2D> calculateIntersectionPointsWithMap(Event event, World world) {
        List<Vector2D> intersectionPoints = new ArrayList<>();
        MapDestructionEvent mapDestructionEvent = (MapDestructionEvent) event;
        float centerX = mapDestructionEvent.getPointOfCollision().getX();
        float centerY = mapDestructionEvent.getPointOfCollision().getY();
        float radius = mapDestructionEvent.getExplosionRadius();
        //System.out.println("Center of circle: " + centerX + ", " + centerY + " Radius: " + radius);

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
        //System.out.println("Amount of intersection points negative x: " +intersectionXValuesNegative.size() + " Amount of intersection points Positive x: "+ intersectionXValuesPositive.size());
        if (intersectionXValuesNegative.size() + intersectionXValuesPositive.size() == 2) {
            for (Float xValue : intersectionXValuesNegative) {
                intersectionPoints.add(new Vector2D(xValue, negativeHalf.getYValue(xValue)));
            }

            for (Float xValue : intersectionXValuesPositive) {
                intersectionPoints.add(new Vector2D(xValue, positiveHalf.getYValue(xValue)));
            }
        }
        //System.out.println("Intersection points size: " + intersectionPoints.size() );
        return intersectionPoints;
    }


    private float intersect(IGameMapFunction mapFunction, IGameMapFunction halfCircle, float acceptInterval, float rangeStart, float rangeEnd) {

        for (float x = rangeStart; x <= rangeEnd; x += 0.005f) {

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
