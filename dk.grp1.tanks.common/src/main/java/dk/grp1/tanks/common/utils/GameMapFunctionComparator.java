package dk.grp1.tanks.common.utils;

import dk.grp1.tanks.common.data.IGameMapFunction;

import java.util.Comparator;

public class GameMapFunctionComparator implements Comparator<IGameMapFunction> {

    @Override
    public int compare(IGameMapFunction current, IGameMapFunction other) {

        if(other == null || current == null){
            return -1;
        }
        if(current.getStartX() < other.getStartX()){
            return -1;
        }
        if(current.getStartX() == other.getStartX()){
            if(current.getEndX() < other.getEndX()){
                return -1;
            }else if(current.getEndX() == other.getEndX()){
                return 0;
            }
        }
        return 1;
    }
}
