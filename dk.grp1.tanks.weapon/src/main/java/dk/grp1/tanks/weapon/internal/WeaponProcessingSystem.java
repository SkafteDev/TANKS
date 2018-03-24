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

public class WeaponProcessingSystem implements IEntityProcessingService {

    private WeaponFactory wepFac = new WeaponFactory();

    @Override
    public void process(World world, GameData gameData) {

        for (Entity bullet : world.getEntities(Projectile.class)) {



            MovementPart movePart =  bullet.getPart(MovementPart.class);
            PhysicsPart physicsPart = bullet.getPart(PhysicsPart.class);
            CollisionPart collisionPart = bullet.getPart(CollisionPart.class);
            PositionPart positionPart = bullet.getPart(PositionPart.class);
            DamagePart damagePart = bullet.getPart(DamagePart.class);


            if(physicsPart != null) {
                physicsPart.processPart(bullet, gameData);
            }

            if(collisionPart != null){
                if(collisionPart.isHitGameMap() && positionPart != null && damagePart != null){
                    Event explosionEvent = new ExplosionEvent(bullet,new Vector2D(positionPart.getX(),positionPart.getY()),damagePart.getExplosionRadius());
                    gameData.addEvent(explosionEvent);
                    world.removeEntity(bullet);
                }
            }

            if(movePart != null) {
                movePart.processPart(bullet, gameData);
            }

            List<IEntityPart> partsLeft = new ArrayList<>(bullet.getParts());
            partsLeft.remove(movePart);
            partsLeft.remove(physicsPart);
            partsLeft.remove(collisionPart);
            partsLeft.remove(positionPart);
            partsLeft.remove(damagePart);

            for (IEntityPart part : partsLeft) {
                part.processPart(bullet, gameData);
            }
        }
    }
}
