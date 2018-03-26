package dk.grp1.tanks.weapon.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.events.Event;
import dk.grp1.tanks.common.events.ExplosionEvent;
import dk.grp1.tanks.common.services.IEntityProcessingService;
import dk.grp1.tanks.common.utils.Vector2D;
import dk.grp1.tanks.weapon.Projectile;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class WeaponProcessingSystem implements IEntityProcessingService {

    private WeaponFactory wepFac = new WeaponFactory();

    @Override
    public void process(World world, GameData gameData) {

        for (Entity bullet : world.getEntities(Projectile.class)) {


            MovementPart movePart = bullet.getPart(MovementPart.class);
            PhysicsPart physicsPart = bullet.getPart(PhysicsPart.class);
            CollisionPart collisionPart = bullet.getPart(CollisionPart.class);
            PositionPart positionPart = bullet.getPart(PositionPart.class);
            DamagePart damagePart = bullet.getPart(DamagePart.class);


            if (physicsPart != null) {
                physicsPart.processPart(bullet, gameData, world);
            }

            if (collisionPart != null) {
                collisionPart.processPart(bullet, gameData, world);
            }

            if (movePart != null) {
                movePart.processPart(bullet, gameData, world);
            }
        }
    }
}
