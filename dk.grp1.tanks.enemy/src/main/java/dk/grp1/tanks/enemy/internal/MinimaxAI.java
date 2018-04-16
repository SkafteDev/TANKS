package dk.grp1.tanks.enemy.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;

import java.util.Map;

public class MinimaxAI {

    private Entity enemy;

    public Action makeDecision(World world, GameData gameData, String turn, Entity actor){
        this. enemy = actor;
        Action bestAction = null;
        float bestUtiltity = -Float.MAX_VALUE;
        State state = new State(world, gameData,turn);

        Map<Action, State> succ = state.getSuccessors();
        for (Map.Entry<Action, State> entry: succ.entrySet()
             ) {
            float utility = minValue(entry.getValue());
            if( utility > bestUtiltity){
                bestAction = entry.getKey();
                bestUtiltity = utility;
            }
        }
        System.out.println(bestAction + "::" + bestUtiltity);
        return bestAction;
    }

    public float minValue(State state){
        if(state.isTerminal()){
            return state.getUtility(enemy);
        }
        float value = Float.MAX_VALUE;
        for (Map.Entry<Action,State> entry: state.getSuccessors().entrySet()
             ) {
            value = min(value, maxValue(entry.getValue()));
        }
        return value;
    }

    public float maxValue(State state){
        if(state.isTerminal()){
            return state.getUtility(enemy);
        }
        float value = Float.MIN_VALUE;
        for (Map.Entry<Action,State> entry: state.getSuccessors().entrySet()
                ) {
            value = max(value, maxValue(entry.getValue()));
        }
        return value;
    }

    private float min(float a, float b){
        if(a > b){
            return b;
        }else{
            return a;
        }
    }

    private float max(float a, float b){
        if(b > a){
            return b;
        }else{
            return a;
        }
    }
}
