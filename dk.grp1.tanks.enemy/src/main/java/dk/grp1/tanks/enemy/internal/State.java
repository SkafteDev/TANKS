package dk.grp1.tanks.enemy.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CannonPart;
import dk.grp1.tanks.common.data.parts.LifePart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.data.parts.TurnPart;
import javafx.geometry.Pos;
import sun.font.CompositeStrike;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class State {
    public World world;
    public int turn;
    public GameData gameData;

    public State(World world, GameData gameData, int turn) {

        this.world = world;
        this.gameData = gameData;
        this.turn = turn;
    }


    public boolean isTerminal() {
        return world.getEntities().size() < 2;
    }

    public float getUtility(Entity enemy) {
        if(world.getEntities().size() == 0){
            return 0;
        }
        if(world.getEntities().size() == 1){
            if(world.getEntities().contains(enemy)){
                return 1;
            }else {
                return -1;
            }
        }else{
            LifePart life = enemy.getPart(LifePart.class);
            return life.getCurrentHP()/life.getMaxHP();
        }
    }

    public Map<Action,State> getSuccessors() {
        Map<Action,State> succs = new HashMap<>();

        for (FirePowerLevel firePowerLevel : FirePowerLevel.values() ) {
            for (AimSetting aim : AimSetting.values()) {
                Action action = new Action(firePowerLevel, aim);
                succs.put(action, getOutcome(action));
            }
        }
        return succs;
    }

    private State getOutcome(Action action) {
        State newState = (State)cloneObject(this);

        for (Entity entity: world.getEntities()
             ) {
            TurnPart turnpart = entity.getPart(TurnPart.class);
            if(turnpart.getMyTurnNumber() == this.turn){
                PositionPart positionPart = entity.getPart(PositionPart.class);
                float startX = positionPart.getX();
                float v0 = action.getFirePowerLevel().getFirepoweer();
                float changeX =(float)( v0*v0 *  Math.sin(2* action.getAim().getAim()));
                float endX = startX+changeX;

                damageEntities(newState, endX, 50);
                newState.turn = "MAX";
            }
        }

        return newState;

    }

    private void damageEntities(State state, float endX, int i) {
        for (Entity ent : state.world.getEntities()
             ) {
            PositionPart positionPart = ent.getPart(PositionPart.class);
            if(positionPart.getX() > endX - 25&& positionPart.getX()< endX + 25 ){
                LifePart life = ent.getPart(LifePart.class);
                life.removeHP(i);
            }
        }
    }

    private static Object cloneObject(Object obj){
        try{
            Object clone = obj.getClass().newInstance();
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if(field.get(obj) == null || Modifier.isFinal(field.getModifiers())){
                    continue;
                }
                if(field.getType().isPrimitive() || field.getType().equals(String.class)
                        || field.getType().getSuperclass().equals(Number.class)
                        || field.getType().equals(Boolean.class)){
                    field.set(clone, field.get(obj));
                }else{
                    Object childObj = field.get(obj);
                    if(childObj == obj){
                        field.set(clone, clone);
                    }else{
                        field.set(clone, cloneObject(field.get(obj)));
                    }
                }
            }
            return clone;
        }catch(Exception e){
            return null;
        }
    }
}
