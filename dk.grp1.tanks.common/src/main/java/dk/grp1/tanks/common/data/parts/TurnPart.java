package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.eventManager.events.EndTurnEvent;

public class TurnPart implements IEntityPart {

    private boolean myTurn;

    public TurnPart(){
        myTurn = false;

    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {

    }


    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }
}
