package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;

public class ControlPart implements IEntityPart {

    private boolean left, right;


    @Override
    public void processPart(Entity entity, GameData gameData) {

    }


    public boolean left() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean right() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
}
