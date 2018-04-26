package dk.grp1.tanks.weapon.HomingMissile.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.eventManager.events.ExplosionAnimationEvent;
import dk.grp1.tanks.common.eventManager.events.ExplosionEvent;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.List;

public class HomingMovementPart extends MovementPart {

    private List<Vector2D> path;
    private int index;

    public HomingMovementPart(List<Vector2D> path) {
        super(new Vector2D(10, 10), 100);
        this.path = path;
        this.index = path.size() - 2;
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {
        PositionPart pp = entity.getPart(PositionPart.class);
        CirclePart cp = entity.getPart(CirclePart.class);

        Vector2D v = null;
        if (index >= 0) {
            v = path.get(index);

            pp.setX(v.getX());
            pp.setY(v.getY());
            cp.setCentreX(v.getX());
            cp.setCentreY(v.getY());
            index--;
        } else {
            DamagePart dmg = entity.getPart(DamagePart.class);
            ExplosionTexturePart text = entity.getPart(ExplosionTexturePart.class);

            if (text == null || dmg == null) {
                throw new NullPointerException("DamagePart or ExplosionTexturePart is null");
            }

            gameData.getEventManager().addEvent(new ExplosionEvent(entity, path.get(0), dmg.getExplosionRadius()));
            gameData.getEventManager().addEvent(new ExplosionAnimationEvent(entity, path.get(0), text, dmg.getExplosionRadius()));
            world.removeEntity(entity);
        }
    }
}
