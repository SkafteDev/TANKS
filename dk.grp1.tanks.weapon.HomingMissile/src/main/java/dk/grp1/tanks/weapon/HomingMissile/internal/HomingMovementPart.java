package dk.grp1.tanks.weapon.HomingMissile.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.MovementPart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.List;

public class HomingMovementPart extends MovementPart {

    private List<Vector2D> path;
    private int index;

    public HomingMovementPart(List<Vector2D> path) {
        super(new Vector2D(10,10),100);
        this.path = path;
        this.index = path.size()-2;
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {
        PositionPart pp = entity.getPart(PositionPart.class);
        CirclePart cp = entity.getPart(CirclePart.class);

        Vector2D v = path.get(index);

        pp.setX(v.getX());
        pp.setY(v.getY());
        cp.setCentreX(v.getX());
        cp.setCentreY(v.getY());
        index--;
    }
}
