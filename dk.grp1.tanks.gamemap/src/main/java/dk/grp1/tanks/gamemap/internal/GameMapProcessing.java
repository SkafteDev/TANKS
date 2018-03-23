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

    /**
     *
     * @param intersectionPoints
     * @param world
     * @param event
     */
    private void replacePartsOfMapWithCircles(List<Vector2D> intersectionPoints, World world, Event event) {
        //Setup Part of the function
        if (intersectionPoints.size() != 2) {
            return;
        }
        //From left to right. The firstPoint needs to have the lowest x value. Therefore swap if it has somehow been mixed up.
        Vector2D firstPoint = intersectionPoints.get(0);
        Vector2D secondPoint = intersectionPoints.get(1);
        if (firstPoint.getX() > secondPoint.getX()) {
            Vector2D tmp = firstPoint;
            firstPoint = secondPoint;
            secondPoint = tmp;
        }

        MapDestructionEvent mapDestructionEvent = (MapDestructionEvent) event;

        float centerX = mapDestructionEvent.getPointOfCollision().getX();
        float centerY = mapDestructionEvent.getPointOfCollision().getY();
        float radius = mapDestructionEvent.getExplosionRadius();
        //Setup done
        fixFunctionsAroundCircle(world, centerX, centerY, radius, firstPoint, secondPoint);
        removeFunctionsWithinCircle(world, centerX, radius);
        List<IGameMapFunction> functionsToAdd = generateCirclesToInsert(firstPoint, secondPoint, centerX, centerY, radius);
        for (IGameMapFunction gameMapFunction : functionsToAdd) {
            world.getGameMap().addGameMapFunction(gameMapFunction);
        }


    }

    /**
     *
     * @param firstPoint
     * @param secondPoint
     * @param centerX
     * @param centerY
     * @param radius
     * @return
     */
    private List<IGameMapFunction> generateCirclesToInsert(Vector2D firstPoint, Vector2D secondPoint, float centerX, float centerY, float radius) {
        List<IGameMapFunction> functionsToInsert = new ArrayList<>();
        //We don't care if it is ascending or descending. we Insert a negative half circle that is slightly smaller in radius than the actual "explosion radius"
        //TODO: Though we don't want the y value for our circle to exceed the function that it should connect to. That means both functions. End and start.
        //We then add two linear functions to either end to fix the ends. This is already done in fix functions around circle.
        float newRadius = radius - (radius/5);
        IGameMapFunction negativeHalf = new GameMapNegativeHalfCircle(centerX - newRadius, centerX + newRadius,centerX,centerY,newRadius);
        functionsToInsert.add(negativeHalf);
        return functionsToInsert;
    }

    /**
     * Fixes the functions that overlap where the circle is to be inserted.
     * @param world the world
     * @param centerX The center x value of the circle
     * @param centerY The center y value of the circle
     * @param radius The radius of the circle
     * @param firstPoint The first intersection point
     * @param secondPoint The second intersection point
     */
    private void fixFunctionsAroundCircle(World world, float centerX, float centerY, float radius, Vector2D firstPoint, Vector2D secondPoint) {
        IGameMapFunction startFunc = null;
        IGameMapFunction endFunc = null;
        //Find the function before the start of the circle and the function after the end of the circle.
        for (IGameMapFunction gameMapFunction : world.getGameMap().getGameMapFunctions()) {
            if (gameMapFunction.isWithin(firstPoint.getX())) {
                startFunc = gameMapFunction;
            }
            if (gameMapFunction.isWithin(secondPoint.getX())) {
                endFunc = gameMapFunction;
            }
        }
        float rangeOneStartX, rangeOneEndX, rangeTwoStartX, rangeTwoEndX;
        float buffer = radius / 5;
        rangeOneStartX = startFunc.getStartX();
        rangeOneEndX = centerX - radius - buffer;
        rangeTwoStartX = centerX + radius + buffer;
        rangeTwoEndX = endFunc.getEndX();

        //If the start and end functions are 2 separate functions. Then just change where they end and start.
        if (!startFunc.equals(endFunc)) {
            startFunc.setStartX(rangeOneStartX);
            startFunc.setEndX(rangeOneEndX);
            endFunc.setStartX(rangeTwoStartX);
            endFunc.setEndX(rangeTwoEndX);
        } else {
            //If the start and end is the same function, then split the function into two new identical functions with different ranges.
            // Call splitInTwoWithNewRanges(range1startx,range1endx,range2startx,range2endx) on the function.
            List<IGameMapFunction> splitMapFunctions = startFunc.splitInTwoWithNewRanges(rangeOneStartX, rangeOneEndX, rangeTwoStartX, rangeTwoEndX);
            world.getGameMap().getGameMapFunctions().remove(startFunc);
            for (IGameMapFunction splitMapFunction : splitMapFunctions) {
                world.getGameMap().addGameMapFunction(splitMapFunction);
            }
        }


        float newRadius = radius - (radius/5);

            //Second point calculated from endFunc
        Vector2D rightFirstPointLinear = new Vector2D(centerX + newRadius, centerY);
        Vector2D rightSecondPointLinear = new Vector2D(rangeTwoStartX, endFunc.getYValue(rangeTwoStartX));
            //First point calculated from startFunc
        Vector2D leftFirstPointLinear = new Vector2D(rangeOneEndX, startFunc.getYValue(rangeOneEndX));
        Vector2D leftSecondPointLinear = new Vector2D(centerX - newRadius, centerY);

        List<IGameMapFunction> linearFunctions = generateLinearFunctionToInsert(leftFirstPointLinear, leftSecondPointLinear,rightFirstPointLinear,rightSecondPointLinear);
        for (IGameMapFunction linearFunctionToInsert : linearFunctions) {
            world.getGameMap().addGameMapFunction(linearFunctionToInsert);
        }


    }

    private List<IGameMapFunction> generateLinearFunctionToInsert(Vector2D leftFirstPointLinear, Vector2D leftSecondPointLinear, Vector2D rightFirstPointLinear, Vector2D rightSecondPointLinear) {
        List<IGameMapFunction> linearMapFunctions = new ArrayList<>();

        float a1 = (leftSecondPointLinear.getY() - leftFirstPointLinear.getY()) / (leftSecondPointLinear.getX() - leftFirstPointLinear.getX());
        float b1 = leftFirstPointLinear.getY() - a1 * leftFirstPointLinear.getX();
        linearMapFunctions.add(new GameMapLinear(a1, b1, leftFirstPointLinear.getX(), leftSecondPointLinear.getX()));

        float a2 = (rightSecondPointLinear.getY() - rightFirstPointLinear.getY()) / (rightSecondPointLinear.getX() - rightFirstPointLinear.getX());
        float b2 = rightFirstPointLinear.getY() - a2 * rightFirstPointLinear.getX();
        linearMapFunctions.add(new GameMapLinear(a2, b2, rightFirstPointLinear.getX(), rightSecondPointLinear.getX()));

        return linearMapFunctions;
    }

    private void removeFunctionsWithinCircle(World world, float centerX, float radius) {
        List<IGameMapFunction> gameMapFunctionsToRemove = new ArrayList<>();
        for (IGameMapFunction gameMapFunction : world.getGameMap().getGameMapFunctions()) {
            if (gameMapFunction.existsOnlyWithinRange((centerX - radius)-(radius/5), (centerX + radius)+(radius/5))) {
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
        //Setup the intersection circles. It can intersect with either negative or positive.
        IGameMapFunction negativeHalf = new GameMapNegativeHalfCircle(centerX - radius, centerX + radius, centerX, centerY, radius);
        IGameMapFunction positiveHalf = new GameMapPositiveHalfCircle(centerX - radius, centerX + radius, centerX, centerY, radius);

        List<Float> intersectionXValuesPositive = new ArrayList<>();
        List<Float> intersectionXValuesNegative = new ArrayList<>();
        //The acceptinterval defines how much of a difference in y-values is accepted as an intersection.
        //The lower it is the better, but if it is too low the increments in the intersect method must be changed
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
        //If exactly 2 intersections are found it is considered a valid shot.
        if (intersectionXValuesNegative.size() + intersectionXValuesPositive.size() == 2) {
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
