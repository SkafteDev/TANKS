package dk.grp1.tanks.weapon.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.events.Event;
import dk.grp1.tanks.common.events.MapDestructionEvent;
import dk.grp1.tanks.common.events.ShootingEvent;
import dk.grp1.tanks.common.services.IEntityProcessingService;
import dk.grp1.tanks.common.utils.Vector2D;

public class WeaponProcessingSystem implements IEntityProcessingService {

    private WeaponFactory wepFac = new WeaponFactory();

    @Override
    public void process(World world, GameData gameData) {

        for (Event ev: gameData.getEvents(ShootingEvent.class)) {
            world.addEntity(wepFac.create(ev,gameData));
            gameData.removeEvent(ev);
        }

        for (Entity bullet : world.getEntities(Bullet.class)) {
            MovementPart movePart =  bullet.getPart(MovementPart.class);
            PhysicsPart physicsPart = bullet.getPart(PhysicsPart.class);
            CollisionPart collisionPart = bullet.getPart(CollisionPart.class);
            PositionPart positionPart = bullet.getPart(PositionPart.class);
            DamagePart damagePart = bullet.getPart(DamagePart.class);
            if(physicsPart != null) {
                physicsPart.processPart(bullet, gameData);
            }

            if(collisionPart != null){
                collisionPart.processPart(bullet,gameData);
                if(collisionPart.isHitGameMap() && positionPart != null){
                    Event explosionEvent = new MapDestructionEvent(bullet,new Vector2D(positionPart.getX(),positionPart.getY()),damagePart.getExplosionRadius());
                    gameData.addEvent(explosionEvent);
                    world.removeEntity(bullet);
                }
            }

            if(movePart != null) {
                movePart.processPart(bullet, gameData);
            }

        }
    }
}
