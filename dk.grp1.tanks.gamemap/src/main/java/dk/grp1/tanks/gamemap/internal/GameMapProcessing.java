package dk.grp1.tanks.gamemap.internal;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameMap;
import dk.grp1.tanks.common.data.IGameMapFunction;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.events.Event;
import dk.grp1.tanks.common.events.MapDestructionEvent;
import dk.grp1.tanks.common.services.INonEntityProcessingService;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class GameMapProcessing implements INonEntityProcessingService {
    private  List<IGameMapFunction> functionsToRemove = new ArrayList<>();
    @Override
    public void process(World world, GameData gameData) {
        for (Event event : gameData.getEvents(MapDestructionEvent.class)) {
            List<Vector2D> intersectionPoints = calculateIntersectionPointsWithMap(event, world);
            replacePartsOfMapWithLinearFunctions(intersectionPoints, world, event);
            gameData.removeEvent(event);
        }
    }

    /**
     * @param intersectionPoints
     * @param world
     * @param event
     */
    private void replacePartsOfMapWithLinearFunctions(List<Vector2D> intersectionPoints, World world, Event event) {
        if (intersectionPoints.size() != 2) {
//            System.out.println("Size of intersection points list: " + intersectionPoints.size());
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
        IGameMapFunction leftFunc = getNearbyFunction(gameMap.getGameMapFunctions(),rangeOneEndX-0.001f);
        IGameMapFunction rightFunc = getNearbyFunction(gameMap.getGameMapFunctions(),rangeTwoStartX+0.001f);;


        // If null, restore and return.
        if (rightFunc == null || leftFunc == null){
//            System.out.println("Right is null: " + (rightFunc == null));
//            System.out.println("Left is null: " + (leftFunc == null));
            System.out.println("Restore Map");
            restore(gameMap);
            return;
        }

        float rangeOneStartX = leftFunc.getStartX();
        float rangeTwoEndX = rightFunc.getEndX();
        //Change found functions by shorting them
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
        Vector2D leftFlatLinearPoint = new Vector2D(flatLinear.getStartX() + 0.01f, flatLinear.getYValue(flatLinear.getStartX() + 0.01f));
        Vector2D rightFlatLinearPoint = new Vector2D(flatLinear.getEndX() - 0.01f, flatLinear.getYValue(flatLinear.getEndX() - 0.01f));
        Vector2D rightFuncPoint = new Vector2D(rangeTwoStartX, rightFunc.getYValue(rangeTwoStartX));

        createLinearEdges(gameMap, rangeOneEndX, rangeTwoStartX, flatLinear, leftFuncPoint, leftFlatLinearPoint, rightFlatLinearPoint, rightFuncPoint);
    }

    private void createLinearEdges(GameMap gameMap, float rangeOneEndX, float rangeTwoStartX, IGameMapFunction flatLinear, Vector2D leftFuncPoint, Vector2D leftFlatLinearPoint, Vector2D rightFlatLinearPoint, Vector2D rightFuncPoint) {
        float a1 = (leftFlatLinearPoint.getY() - leftFuncPoint.getY()) / (leftFlatLinearPoint.getX() - leftFuncPoint.getX());
        float b1 = leftFlatLinearPoint.getY() - a1 * leftFlatLinearPoint.getX();
        IGameMapFunction leftLinear = new GameMapLinear(a1, b1, rangeOneEndX, flatLinear.getStartX());
        float a2 = (rightFuncPoint.getY() - rightFlatLinearPoint.getY()) / (rightFuncPoint.getX() - rightFlatLinearPoint.getX());
        float b2 = rightFlatLinearPoint.getY() - a2 * rightFlatLinearPoint.getX();
        IGameMapFunction rightLinear = new GameMapLinear(a2, b2, flatLinear.getEndX(), rangeTwoStartX);
        //     System.out.println("right Linear: A = " + a2 + " B = " + b2);
        //     System.out.println("Left Linear: A = " + a1 + " B = " + b1);
        gameMap.addGameMapFunction(leftLinear);
        gameMap.addGameMapFunction(rightLinear);
    }

    private IGameMapFunction createFlatLinear(MapDestructionEvent mapDestructionEvent, Vector2D firstIntersectionPoint, Vector2D secondIntersectionPoint, float rangeOneEndX, float rangeTwoStartX, IGameMapFunction leftFunc, IGameMapFunction rightFunc) {
        IGameMapFunction flatLinear;

        Vector2D explosionCenter = mapDestructionEvent.getPointOfCollision();
        float explosionRadius = mapDestructionEvent.getExplosionRadius();

        float b = explosionCenter.getY() - explosionRadius;
        if(b < 10f){
            b = 10f;
        }
        //Calculate ascending or descending.
        boolean ascending = (leftFunc.getYValue(rangeOneEndX) < rightFunc.getYValue(rangeTwoStartX));

        if(ascending){
            flatLinear = new GameMapLinear(0f,b,firstIntersectionPoint.getX(),explosionCenter.getX()+explosionRadius);
        } else {
            flatLinear = new GameMapLinear(0f,b,explosionCenter.getX()-explosionRadius,secondIntersectionPoint.getX());
        }
        return flatLinear;
    }

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

    private void restore(GameMap gameMap) {
        for (IGameMapFunction gameMapFunction : functionsToRemove) {
            gameMap.addGameMapFunction(gameMapFunction);
        }
    }

    private IGameMapFunction getNearbyFunction(List<IGameMapFunction> gameMap, float alternativeRange){
        for (IGameMapFunction gameMapFunction : gameMap) {
            if (gameMapFunction.isWithin(alternativeRange)){
                return gameMapFunction;
            }
        }
        // if no function is found
        return null;
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
        resultLeftHalf = -1;
       // if (resultLeftHalf == -1) {
            for (IGameMapFunction mapFunction : world.getGameMap().getGameMapFunctions()) {
                resultLeftHalf = intersect(mapFunction, positiveHalf, acceptInterval, centerX - radius, centerX);
                if (resultLeftHalf != -1) {
                    intersectionXValuesPositive.add(resultLeftHalf);
                    break;
                }
            }
        //}


        float resultRightHalf = -1;
        for (IGameMapFunction mapFunction : world.getGameMap().getGameMapFunctions()) {

            resultRightHalf = intersect(mapFunction, negativeHalf, acceptInterval, centerX, centerX + radius);
            if (resultRightHalf != -1) {
                intersectionXValuesNegative.add(resultRightHalf);
                break;
            }
        }
        resultRightHalf = -1;
        //if (resultRightHalf == -1) {
            for (IGameMapFunction mapFunction : world.getGameMap().getGameMapFunctions()) {

                resultRightHalf = intersect(mapFunction, positiveHalf, acceptInterval, centerX, centerX + radius);
                if (resultRightHalf != -1) {
                    intersectionXValuesPositive.add(resultRightHalf);
                    break;
                }
            }
        //}
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
