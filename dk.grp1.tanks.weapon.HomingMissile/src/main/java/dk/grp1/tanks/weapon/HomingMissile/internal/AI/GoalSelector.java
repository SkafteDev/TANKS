package dk.grp1.tanks.weapon.HomingMissile.internal.AI;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.DamagePart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.data.parts.TurnPart;
import dk.grp1.tanks.common.utils.Vector2D;
import javafx.geometry.Pos;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GoalSelector implements IGoalSelector{

    private final World world;
    private GameData gameData;
    private final Entity origin;
    private Entity homingMissile;
    private final int numberOfPoints = 512;

    public GoalSelector(World world, GameData gameData, Entity origin, Entity homingMissile){

        this.world = world;
        this.gameData = gameData;
        this.origin = origin;
        this.homingMissile = homingMissile;
    }


    @Override
    public State calculateGoalState() {
        List<Vector2D> vertices = world.getGameMap().getVertices(0, gameData.getGameWidth(), numberOfPoints);
        Vector2D bestExplosion =  null;
        int bestCount = 0;

        // Check the value of a bunch of points
        for (int i = 0; i < vertices.size(); i++) {
            int count = 0;
            float x = vertices.get(i).getX();
            float y = vertices.get(i).getY() + 5;

            // Check how many enemies are hit, if exploded at this point
            for (Entity entity : world.getEntities()) {
                TurnPart turnPart = entity.getPart(TurnPart.class);

                if (turnPart != null) {
                    if (isClose(entity, x, y) && entity != origin) {
                        count++;
                    }
                }
            }

            // Keep the one that hits the most enemies
            if (count > bestCount) {
                bestCount = count;
                bestExplosion = new Vector2D(x, y);
            }
        }

        Entity newMissile = (Entity) cloneObject(homingMissile);
        CirclePart newPos = newMissile.getPart(CirclePart.class);
        newPos.setCentreX(bestExplosion.getX());
        newPos.setCentreY(bestExplosion.getY());
        State goalState = new State(world.getGameMap(),newMissile);
        return goalState;

    }

    private boolean isClose(Entity entity, float x, float y) {
        CirclePart circlePart = entity.getPart(CirclePart.class);
        DamagePart dmg = homingMissile.getPart(DamagePart.class);
        float radius = dmg.getExplosionRadius();
        if (circlePart != null ) {
            float distX = x - circlePart.getCentreX();
            float distY = y - circlePart.getCentreY();
            float distance = (float) (Math.sqrt(distX * distX + distY * distY));
            return distance < radius + circlePart.getRadius();
        }else{
            throw new Error("Cannot calculate isClose if there is no positionpart or no circlepart");
        }
    }


    /**
     * Clones an object using the javal reflection API
     * @param obj
     * @return
     */
    private static Object cloneObject(Object obj) {
        try {
            Object clone = obj.getClass().newInstance();
            if (obj.getClass().getDeclaredFields().length == 0) {
                for (Field field : obj.getClass().getSuperclass().getSuperclass().getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.get(obj) == null || Modifier.isFinal(field.getModifiers())) {
                        continue;
                    }
                    if (field.getType().isPrimitive() || field.getType().equals(String.class)
                            || (field.getType().getSuperclass() != null && field.getType().getSuperclass().equals(Number.class))
                            || field.getType().equals(Boolean.class)) {
                        field.set(clone, field.get(obj));
                    } else {
                        Object childObj = field.get(obj);
                        if (childObj == obj) {
                            field.set(clone, clone);
                        } else if (childObj.getClass().equals((GameData.class))) {
                            continue;
                        } else if (childObj.getClass().equals((Object[].class))) {
                            field.set(clone, Arrays.copyOf((Object[]) field.get(obj), ((Object[]) field.get(obj)).length));
                        } else if (childObj.getClass().equals(HashMap.class)) {
                            Map<Object, Object> newMap = new HashMap<>();
                            for (Object entry : ((HashMap) field.get(obj)).entrySet()
                                    ) {
                                Map.Entry mapEntry = (Map.Entry) entry;

                                newMap.put(mapEntry.getKey(), cloneObject(mapEntry.getValue()));


                            }
                            field.set(clone, newMap);
                        } else if (childObj.getClass().equals(ConcurrentHashMap.class)) {
                            Map<Object, Object> newMap = new ConcurrentHashMap<>();
                            for (Object entry : ((ConcurrentHashMap) field.get(obj)).entrySet()
                                    ) {
                                Map.Entry mapEntry = (Map.Entry) entry;

                                newMap.put(mapEntry.getKey(), cloneObject(mapEntry.getValue()));


                            }
                            field.set(clone, newMap);
                        } else {
                            field.set(clone, cloneObject(field.get(obj)));

                        }
                    }
                }
            }
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.get(obj) == null || Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                if (field.getType().isPrimitive() || field.getType().equals(String.class)
                        || (field.getType().getSuperclass() != null && field.getType().getSuperclass().equals(Number.class))
                        || field.getType().equals(Boolean.class)) {
                    field.set(clone, field.get(obj));
                } else {
                    Object childObj = field.get(obj);
                    if (childObj == obj) {
                        field.set(clone, clone);
                    } else if (childObj.getClass().equals((GameData.class))) {
                        continue;
                    } else if (childObj.getClass().equals((Object[].class))) {
                        field.set(clone, Arrays.copyOf((Object[]) field.get(obj), ((Object[]) field.get(obj)).length));
                    } else if (childObj.getClass().equals(HashMap.class)) {
                        Map<Object, Object> newMap = new HashMap<>();
                        for (Object entry : ((HashMap) field.get(obj)).entrySet()
                                ) {
                            Map.Entry mapEntry = (Map.Entry) entry;

                            newMap.put(mapEntry.getKey(), cloneObject(mapEntry.getValue()));


                        }
                        field.set(clone, newMap);
                    } else if (childObj.getClass().equals(ConcurrentHashMap.class)) {
                        Map<Object, Object> newMap = new ConcurrentHashMap<>();
                        for (Object entry : ((ConcurrentHashMap) field.get(obj)).entrySet()
                                ) {
                            Map.Entry mapEntry = (Map.Entry) entry;

                            newMap.put(mapEntry.getKey(), cloneObject(mapEntry.getValue()));


                        }
                        field.set(clone, newMap);
                    } else {
                        field.set(clone, cloneObject(field.get(obj)));

                    }
                }
            }
            return clone;
        } catch (Exception e) {
            return null;
        }
    }

}
