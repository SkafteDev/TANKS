package dk.grp1.tanks.gamemap.internal;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameMap;
import dk.grp1.tanks.common.data.IGameMapFunction;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.eventManager.IEventCallback;
import dk.grp1.tanks.common.eventManager.events.Event;
import dk.grp1.tanks.common.eventManager.events.GameMapChangedEvent;
import dk.grp1.tanks.common.eventManager.events.MapDestructionEvent;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

/**
 * The GameMapProcessing class is responsible for destroying the gameMap when explosions occur.
 */
public class GameMapProcessing implements IEventCallback {
    private final GameData gameData;
    private final World world;
    private List<IGameMapFunction> functionsToRemove = new ArrayList<>();

    public GameMapProcessing(GameData gameData, World world) {
        this.gameData = gameData;
        this.world = world;
    }

    @Override
    public void processEvent(Event event) {

        if (event instanceof MapDestructionEvent) {
            List<Vector2D> intersectionPoints = calculateIntersectionPointsWithMap(event, world);
            replacePartsOfMapWithLinearFunctions(intersectionPoints, world, event);
            gameData.getEventManager().addEvent(new GameMapChangedEvent(event.getSource()));
        }
    }

    /**
     * Replaces part of the map with 3 linear functions, to simulate an explosion crater. It happens based on intersection points and the explosion event's position
     *
     * @param intersectionPoints
     * @param world
     * @param event
     */
    private void replacePartsOfMapWithLinearFunctions(List<Vector2D> intersectionPoints, World world, Event event) {
        if (intersectionPoints.size() != 2) {
            return;
        }
        // Check the order of the intersection points
        Vector2D firstIntersectionPoint = intersectionPoints.get(0);
        Vector2D secondIntersectionPoint = intersectionPoints.get(1);
        if (firstIntersectionPoint.getX() > secondIntersectionPoint.getX()) {
            Vector2D tmp = firstIntersectionPoint;
            firstIntersectionPoint = secondIntersectionPoint;
            secondIntersectionPoint = tmp;
        }

        // Get the DestructionEvent and Radius of the explosion
        MapDestructionEvent mapDestructionEvent = (MapDestructionEvent) event;
        Vector2D explosionCenter = mapDestructionEvent.getPointOfCollision();
        float explosionRadius = mapDestructionEvent.getExplosionRadius();

        GameMap gameMap = world.getGameMap();


        //Remove Functions that are only within circle radius + buffer
        float buffer = (explosionRadius / 2);
        RemoveFunctions(gameMap, mapDestructionEvent, buffer);

        float rangeOneEndX = explosionCenter.getX() - explosionRadius - buffer;
        float rangeTwoStartX = explosionCenter.getX() + explosionRadius + buffer;
        // Find nearby functions
        IGameMapFunction leftFunc = getNearbyFunction(gameMap.getGameMapFunctions(), rangeOneEndX - 0.001f);
        IGameMapFunction rightFunc = getNearbyFunction(gameMap.getGameMapFunctions(), rangeTwoStartX + 0.001f);
        ;


        // If null, restore to original state of the map and return.
        if (rightFunc == null || leftFunc == null) {
            restore(gameMap);
            return;
        }

        float rangeOneStartX = leftFunc.getStartX();
        float rangeTwoEndX = rightFunc.getEndX();
        //Change found functions by shortening them
        if (leftFunc.equals(rightFunc)) {
            List<IGameMapFunction> splitFunctions = leftFunc.splitInTwoWithNewRanges(rangeOneStartX, rangeOneEndX, rangeTwoStartX, rangeTwoEndX);
            //Remove original function
            gameMap.getGameMapFunctions().remove(leftFunc);
            for (IGameMapFunction splitFunction : splitFunctions) {
                gameMap.addGameMapFunction(splitFunction);
            }
            leftFunc = splitFunctions.get(0);
            rightFunc = splitFunctions.get(1);
        } else {
            leftFunc.setEndX(rangeOneEndX);
            rightFunc.setStartX(rangeTwoStartX);
        }

        // Create the flat linear function which represent the bottom of the hole created by the explosion.
        IGameMapFunction flatLinear = createFlatLinear(mapDestructionEvent, firstIntersectionPoint, secondIntersectionPoint, rangeOneEndX, rangeTwoStartX, leftFunc, rightFunc);
        gameMap.addGameMapFunction(flatLinear);

        //Generate linear functions between function and circle.
        //Calc y value for end point on startFunc. Calc y value for start point on endFunc.x

        Vector2D leftFuncPoint = new Vector2D(rangeOneEndX, leftFunc.getYValue(rangeOneEndX));
        Vector2D leftFlatLinearPoint = new Vector2D(flatLinear.getStartX() + 0.001f, flatLinear.getYValue(flatLinear.getStartX() + 0.001f));
        Vector2D rightFlatLinearPoint = new Vector2D(flatLinear.getEndX() - 0.001f, flatLinear.getYValue(flatLinear.getEndX() - 0.001f));
        Vector2D rightFuncPoint = new Vector2D(rangeTwoStartX, rightFunc.getYValue(rangeTwoStartX));
        //Create the 2 linear functions that connect to the flat one.
        createLinearEdges(gameMap, rangeOneEndX, rangeTwoStartX, flatLinear, leftFuncPoint, leftFlatLinearPoint, rightFlatLinearPoint, rightFuncPoint);
    }

    /**
     * Creates the 2 linear functions connected to the flat one, and adds them to the GameMap
     *
     * @param gameMap
     * @param rangeOneEndX
     * @param rangeTwoStartX
     * @param flatLinear
     * @param leftFuncPoint
     * @param leftFlatLinearPoint
     * @param rightFlatLinearPoint
     * @param rightFuncPoint
     */
    private void createLinearEdges(GameMap gameMap, float rangeOneEndX, float rangeTwoStartX, IGameMapFunction flatLinear, Vector2D leftFuncPoint, Vector2D leftFlatLinearPoint, Vector2D rightFlatLinearPoint, Vector2D rightFuncPoint) {
        float a1 = (leftFlatLinearPoint.getY() - leftFuncPoint.getY()) / (leftFlatLinearPoint.getX() - leftFuncPoint.getX());
        float b1 = leftFlatLinearPoint.getY() - a1 * leftFlatLinearPoint.getX();
        IGameMapFunction leftLinear = new GameMapLinear(a1, b1, rangeOneEndX, flatLinear.getStartX());
        float a2 = (rightFuncPoint.getY() - rightFlatLinearPoint.getY()) / (rightFuncPoint.getX() - rightFlatLinearPoint.getX());
        float b2 = rightFlatLinearPoint.getY() - a2 * rightFlatLinearPoint.getX();
        IGameMapFunction rightLinear = new GameMapLinear(a2, b2, flatLinear.getEndX(), rangeTwoStartX);
        gameMap.addGameMapFunction(leftLinear);
        gameMap.addGameMapFunction(rightLinear);
    }

    /**
     * Creates the flat linear function for the explosion crater.
     *
     * @param mapDestructionEvent
     * @param firstIntersectionPoint
     * @param secondIntersectionPoint
     * @param rangeOneEndX
     * @param rangeTwoStartX
     * @param leftFunc
     * @param rightFunc
     * @return
     */
    private IGameMapFunction createFlatLinear(MapDestructionEvent mapDestructionEvent, Vector2D firstIntersectionPoint, Vector2D secondIntersectionPoint, float rangeOneEndX, float rangeTwoStartX, IGameMapFunction leftFunc, IGameMapFunction rightFunc) {
        IGameMapFunction flatLinear;

        Vector2D explosionCenter = mapDestructionEvent.getPointOfCollision();
        float explosionRadius = mapDestructionEvent.getExplosionRadius();

        float b = explosionCenter.getY() - explosionRadius;
        if (b < 10f) {
            b = 10f;
        }
        //Calculate ascending or descending, as it changes where the function should start or stop.
        boolean ascending = (leftFunc.getYValue(rangeOneEndX) < rightFunc.getYValue(rangeTwoStartX));

        if (ascending) {
            flatLinear = new GameMapLinear(0f, b, firstIntersectionPoint.getX(), explosionCenter.getX() + explosionRadius);
        } else {
            flatLinear = new GameMapLinear(0f, b, explosionCenter.getX() - explosionRadius, secondIntersectionPoint.getX());
        }
        return flatLinear;
    }

    /**
     * Remove any functions that are only within the explosion
     *
     * @param gameMap
     * @param mapDestructionEvent
     * @param buffer
     */
    private void RemoveFunctions(GameMap gameMap, MapDestructionEvent mapDestructionEvent, float buffer) {

        functionsToRemove.clear();
        Vector2D explosionCenter = mapDestructionEvent.getPointOfCollision();
        float explosionRadius = mapDestructionEvent.getExplosionRadius();

        for (IGameMapFunction gameMapFunction : gameMap.getGameMapFunctions()) {
            if (gameMapFunction.existsOnlyWithinRange(explosionCenter.getX() - explosionRadius - buffer, explosionCenter.getX() + explosionRadius + buffer)) {
                functionsToRemove.add(gameMapFunction);
            }
        }
        for (IGameMapFunction gameMapFunction : functionsToRemove) {
            gameMap.getGameMapFunctions().remove(gameMapFunction);
        }
    }

    /**
     * Restore the functions that was only within the explosion.
     *
     * @param gameMap
     */
    private void restore(GameMap gameMap) {
        for (IGameMapFunction gameMapFunction : functionsToRemove) {
            gameMap.addGameMapFunction(gameMapFunction);
        }
    }

    /**
     * Gets a function that is within the alternativeRange x value. Used to find function to the left and right of the explosion
     *
     * @param gameMap
     * @param alternativeRange
     * @return
     */
    private IGameMapFunction getNearbyFunction(List<IGameMapFunction> gameMap, float alternativeRange) {
        for (IGameMapFunction gameMapFunction : gameMap) {
            if (gameMapFunction.isWithin(alternativeRange)) {
                return gameMapFunction;
            }
        }
        // if no function is found
        return null;
    }

    /**
     * Calculates where the explosion intersects with a map. The function searches for nearby intersections 4 times on 1/4 of a circle each time.
     *
     * @param event
     * @param world
     * @return
     */
    private List<Vector2D> calculateIntersectionPointsWithMap(Event event, World world) {
        List<Vector2D> intersectionPoints = new ArrayList<>();
        MapDestructionEvent mapDestructionEvent = (MapDestructionEvent) event;
        float centerX = mapDestructionEvent.getPointOfCollision().getX();
        float centerY = mapDestructionEvent.getPointOfCollision().getY();
        float radius = mapDestructionEvent.getExplosionRadius();
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
        resultLeftHalf = -1;

        for (IGameMapFunction mapFunction : world.getGameMap().getGameMapFunctions()) {
            resultLeftHalf = intersect(mapFunction, positiveHalf, acceptInterval, centerX - radius, centerX);
            if (resultLeftHalf != -1) {
                intersectionXValuesPositive.add(resultLeftHalf);
                break;
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
        resultRightHalf = -1;
        for (IGameMapFunction mapFunction : world.getGameMap().getGameMapFunctions()) {

            resultRightHalf = intersect(mapFunction, positiveHalf, acceptInterval, centerX, centerX + radius);
            if (resultRightHalf != -1) {
                intersectionXValuesPositive.add(resultRightHalf);
                break;
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
        } else if (intersectionXValuesNegative.size() + intersectionXValuesPositive.size() > 2) {
            addAppropriateIntersectionPoints(intersectionPoints, negativeHalf, positiveHalf, intersectionXValuesPositive, intersectionXValuesNegative);

        }

        return intersectionPoints;
    }

    /**
     * Finds the 2 intersection points that are furthest away from each other, as we only want exactly 2.
     *
     * @param intersectionPoints
     * @param negativeHalf
     * @param positiveHalf
     * @param intersectionXValuesPositive
     * @param intersectionXValuesNegative
     */
    private void addAppropriateIntersectionPoints(List<Vector2D> intersectionPoints, IGameMapFunction negativeHalf, IGameMapFunction positiveHalf, List<Float> intersectionXValuesPositive, List<Float> intersectionXValuesNegative) {
        //Find the biggest spread in x-values
        float x1 = 0;
        float x2 = 0;
        float y1 = 0;
        float y2 = 0;
        float dist = 0;
        float xFormer = intersectionXValuesNegative.get(0);
        for (Float x : intersectionXValuesNegative) {
            float dist2 = Math.abs(xFormer - x);
            if (dist2 > dist) {
                dist = dist2;
                x1 = xFormer;
                x2 = x;
                y1 = negativeHalf.getYValue(x1);
                y2 = negativeHalf.getYValue(x2);
            }
        }
        xFormer = intersectionXValuesPositive.get(0);
        for (Float x : intersectionXValuesPositive) {
            float dist2 = Math.abs(xFormer - x);
            if (dist2 > dist) {
                dist = dist2;
                x1 = xFormer;
                x2 = x;
                y1 = positiveHalf.getYValue(x1);
                y2 = positiveHalf.getYValue(x2);
            }
        }
        intersectionPoints.add(new Vector2D(x1, y1));
        intersectionPoints.add(new Vector2D(x2, y2));
    }

    /**
     * Searches for an intersection in the range from rangeStart to rangeEnd between the map function and the half circle, with a margin of error equal to the acceptInterval.
     *
     * @param mapFunction
     * @param halfCircle
     * @param acceptInterval
     * @param rangeStart
     * @param rangeEnd
     * @return
     */
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
        return -1;
    }


}
