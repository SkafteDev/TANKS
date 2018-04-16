package dk.grp1.tanks.enemy.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import javafx.geometry.Pos;
import sun.font.CompositeStrike;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class State {
    public World world;
    public String turn;
    public int depth;

    public State() {

    }

    public State(World world, GameData gameData, String turn) {
        this.depth = 0;
        this.world = world;
        this.turn = turn;
    }

    private static Object cloneObject(Object obj) {
        try {
            Object clone = obj.getClass().newInstance();
            if (obj.getClass().getDeclaredFields().length == 0) {
                for (Field field : obj.getClass().getSuperclass().getDeclaredFields()) {
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

    public boolean isTerminal() {
        if (world.getEntities().size() < 2) {
            return true;
        }
        if (depth > 1) {
            return true;
        }
        for (Entity entity : world.getEntities()) {
            LifePart life = entity.getPart(LifePart.class);
            if (life.getCurrentHP() == 0) {
                return true;
            }
        }
        return false;
    }

    public float getUtility(Entity enemy) {
        boolean atLeatOneDead = false;
        float toReturn = 0;
        for (Entity entity : world.getEntities()) {
            LifePart life = enemy.getPart(LifePart.class);
            if (life.getCurrentHP() == 0) {
                atLeatOneDead = true;
                if (entity.getClass().equals(Enemy.class)) {
                    toReturn += -1;
                } else {
                    toReturn += 1;
                }
            }
        }
        if (!atLeatOneDead) {
            for (Entity entity : world.getEntities()) {
                if (entity.getClass().equals(Enemy.class)) {
                    LifePart life = entity.getPart(LifePart.class);
                    toReturn += life.getCurrentHP() / life.getMaxHP();
                }else{
                    LifePart life = entity.getPart(LifePart.class);
                    toReturn -= life.getCurrentHP() / life.getMaxHP();
                }
            }

        }
        return toReturn;
    }

    public Map<Action, State> getSuccessors() {
        Map<Action, State> succs = new HashMap<>();

        for (FirePowerLevel firePowerLevel : FirePowerLevel.values()) {
            for (AimSetting aim : AimSetting.values()) {
                Action action = new Action(firePowerLevel, aim);
                succs.put(action, getOutcome(action));
            }
        }
        return succs;
    }

    private State getOutcome(Action action) {
        State newState = (State) cloneObject(this);
        newState.depth = this.depth + 1;
        for (Entity entity : world.getEntities()
                ) {
            if (entity.getClass() == Enemy.class && this.turn.equals("MAX")) {
                PositionPart positionPart = entity.getPart(PositionPart.class);
                PhysicsPart physicsPart = entity.getPart(PhysicsPart.class);
                float startX = positionPart.getX();
                float v0 = action.getFirePowerLevel().getFirepoweer();
                float aim = action.getAim().getAim();
                float grav = physicsPart.getGravity();
                float changeX = (float) ((v0 * v0 * Math.sin(2 * aim)) / 90.82f);
                float endX = startX + changeX;

                damageEntities(newState, endX, 50);
                newState.turn = "MIN";
                return newState;

            }
            if (entity.getClass() != Enemy.class && this.turn.equals("MIN")) {
                PositionPart positionPart = entity.getPart(PositionPart.class);
                PhysicsPart physicsPart = entity.getPart(PhysicsPart.class);
                float startX = positionPart.getX();
                float v0 = action.getFirePowerLevel().getFirepoweer();
                float aim = action.getAim().getAim();
                float grav = physicsPart.getGravity();
                float changeX = (float) ((v0 * v0 * Math.sin(2 * aim)) / 90.82f);
                float endX = startX + changeX;

                damageEntities(newState, endX, 50);
                newState.turn = "MAX";
                return newState;

            }
        }
        return null;


    }

    private void damageEntities(State state, float endX, int i) {
        for (Entity ent : state.world.getEntities()
                ) {
            PositionPart positionPart = ent.getPart(PositionPart.class);
            if (positionPart.getX() > endX - 25 && positionPart.getX() < endX + 25) {
                LifePart life = ent.getPart(LifePart.class);
                life.removeHP(i);

            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (world == null) {
            return "State world null";
        }
        for (Entity entity : world.getEntities()) {
            LifePart life = entity.getPart(LifePart.class);
            builder.append(entity.toString());
            builder.append(":");
            if (life != null)
                builder.append(life.getCurrentHP());
            builder.append("\n");
        }
        return builder.toString();
    }


}
