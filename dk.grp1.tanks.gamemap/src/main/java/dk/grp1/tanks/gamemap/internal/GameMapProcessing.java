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
            replacePartsOfMapWithCircles(intersectionPoints, world, event);
            gameData.removeEvent(event);
        }
    }

    /**
     * @param intersectionPoints
     * @param world
     * @param event
     */
    private void replacePartsOfMapWithCircles(List<Vector2D> intersectionPoints, World world, Event event) {
        if (intersectionPoints.size() != 2) {
//            System.out.println("Size of intersection points list: " + intersectionPoints.size());
            return;
        }

        Vector2D firstIntersectionPoint = intersectionPoints.get(0);
        Vector2D secondIntersectionPoint = intersectionPoints.get(1);
        if (firstIntersectionPoint.getX() > secondIntersectionPoint.getX()) {
            Vector2D tmp = firstIntersectionPoint;
            firstIntersectionPoint = secondIntersectionPoint;
            secondIntersectionPoint = tmp;
        }

        MapDestructionEvent mapDestructionEvent = (MapDestructionEvent) event;
        Vector2D explosionCenter = mapDestructionEvent.getPointOfCollision();
        float explosionRadius = mapDestructionEvent.getExplosionRadius();
//        System.out.println("Explosion Center: " + explosionCenter.toString());
//        System.out.println("Explosion radius: " + explosionRadius);
        GameMap gameMap = world.getGameMap();


        //Remove Functions that are only within circle radius + buffer
        float buffer = (explosionRadius / 2);
//        System.out.println("Buffer: " + buffer);

        for (IGameMapFunction gameMapFunction : gameMap.getGameMapFunctions()) {
            if (gameMapFunction.existsOnlyWithinRange(explosionCenter.getX() - explosionRadius - buffer, explosionCenter.getX() + explosionRadius + buffer)) {
                functionsToRemove.add(gameMapFunction);
            }
        }
        for (IGameMapFunction gameMapFunction : functionsToRemove) {
            gameMap.getGameMapFunctions().remove(gameMapFunction);
        }


        IGameMapFunction leftFunc = null;
        IGameMapFunction rightFunc = null;
        //Find nearby functions
        for (IGameMapFunction gameMapFunction : gameMap.getGameMapFunctions()) {
            if (gameMapFunction.isWithin(firstIntersectionPoint.getX())) {
                leftFunc = gameMapFunction;
            }
            if (gameMapFunction.isWithin(secondIntersectionPoint.getX())) {
                rightFunc = gameMapFunction;
            }
        }


        float rangeOneEndX = explosionCenter.getX() - explosionRadius - buffer;
        float rangeTwoStartX = explosionCenter.getX() + explosionRadius + buffer;

        // If there is no nearby function found then we check with the x-values based on center + radius + buffer
        if (leftFunc == null) {
            for (IGameMapFunction gameMapFunction : gameMap.getGameMapFunctions()) {
                if (gameMapFunction.isWithin(rangeOneEndX-0.001f)){
                    leftFunc = gameMapFunction;
                }
            }
        }
        if (rightFunc == null) {
            for (IGameMapFunction gameMapFunction : gameMap.getGameMapFunctions()) {
                if (gameMapFunction.isWithin(rangeTwoStartX+0.001f)){
                    rightFunc = gameMapFunction;
                }
            }
        }
        // If still null, return.
        if (rightFunc == null || leftFunc == null){
//            System.out.println("Right is null: " + (rightFunc == null));
//            System.out.println("Left is null: " + (leftFunc == null));
            restore(gameMap);
            return;
        }
        float rangeOneStartX = leftFunc.getStartX();
        float rangeTwoEndX = rightFunc.getEndX();
        //Change found functions & Calculate ascending or descending.

        boolean ascending = (leftFunc.getYValue(rangeOneEndX) < rightFunc.getYValue(rangeTwoStartX));
        //Shorten start and end functions
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

        IGameMapFunction flatLinear;
        float b = explosionCenter.getY() - explosionRadius;
        if(b < 10f){
            b = 10f;
        }
        if(ascending){
            flatLinear = new GameMapLinear(0f,b,firstIntersectionPoint.getX(),explosionCenter.getX()+explosionRadius);
        } else {
            flatLinear = new GameMapLinear(0f,b,explosionCenter.getX()-explosionRadius,secondIntersectionPoint.getX());
        }
        gameMap.addGameMapFunction(flatLinear);
        /*
        //Generate smaller negative half
        IGameMapFunction negativeHalf;
        if (ascending) {
            negativeHalf = new GameMapNegativeHalfCircle(firstIntersectionPoint.getX(), explosionCenter.getX() + explosionRadius, explosionCenter, explosionRadius);
        } else {
            negativeHalf = new GameMapNegativeHalfCircle(explosionCenter.getX() - explosionRadius, secondIntersectionPoint.getX(), explosionCenter, explosionRadius);
        }
        gameMap.addGameMapFunction(negativeHalf);
        */
        //Generate linear functions between function and circle.
        //Calc y value for end point on startFunc. Calc y value for start point on endFunc.x

        Vector2D leftFuncPoint = new Vector2D(rangeOneEndX, leftFunc.getYValue(rangeOneEndX));
        Vector2D leftCirclePoint = new Vector2D(flatLinear.getStartX() + 0.01f, flatLinear.getYValue(flatLinear.getStartX() + 0.01f));
        Vector2D rightCirclePoint = new Vector2D(flatLinear.getEndX() - 0.01f, flatLinear.getYValue(flatLinear.getEndX() - 0.01f));
        Vector2D rightFuncPoint = new Vector2D(rangeTwoStartX, rightFunc.getYValue(rangeTwoStartX));
        if(rightFuncPoint.getY() != rightFuncPoint.getY()  || leftFuncPoint.getY() != leftFuncPoint.getY()){
     //       System.out.println("BREAK");
        }
   //     System.out.println("Left function point: " + leftFuncPoint.toString());
   //     System.out.println("Left circle point: " + leftCirclePoint.toString());
   //     System.out.println("Right circle point: " + rightCirclePoint.toString());
   //     System.out.println("Right function point: " + rightFuncPoint.toString());

        float a1 = (leftCirclePoint.getY() - leftFuncPoint.getY()) / (leftCirclePoint.getX() - leftFuncPoint.getX());
        float b1 = leftCirclePoint.getY() - a1 * leftCirclePoint.getX();
        IGameMapFunction leftLinear = new GameMapLinear(a1, b1, rangeOneEndX, flatLinear.getStartX());
        float a2 = (rightFuncPoint.getY() - rightCirclePoint.getY()) / (rightFuncPoint.getX() - rightCirclePoint.getX());
        float b2 = rightCirclePoint.getY() - a2 * rightCirclePoint.getX();
        IGameMapFunction rightLinear = new GameMapLinear(a2, b2, flatLinear.getEndX(), rangeTwoStartX);
   //     System.out.println("right Linear: A = " + a2 + " B = " + b2);
   //     System.out.println("Left Linear: A = " + a1 + " B = " + b1);
        gameMap.addGameMapFunction(leftLinear);
        gameMap.addGameMapFunction(rightLinear);
        functionsToRemove.clear();
    }

    private void restore(GameMap gameMap) {
        for (IGameMapFunction gameMapFunction : functionsToRemove) {
            gameMap.addGameMapFunction(gameMapFunction);
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
