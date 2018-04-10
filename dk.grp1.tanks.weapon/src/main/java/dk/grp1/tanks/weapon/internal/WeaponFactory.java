package dk.grp1.tanks.weapon.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.eventManager.events.Event;

public class WeaponFactory {
    public Entity create(){
//        Entity e = new Projectile();
//        e.add( new MovementPart(new Vector2D(25, 22), 10000));
//        e.add(new PositionPart(30,30, (float) (Math.PI/4)));
//        e.add(new ShapePart());
//        e.add(new CirclePart(30,30,10));
//        e.add(new PhysicsPart(30, -9.82f));
//        //e.add(new TexturePart("C:\\Users\\Vedsted\\Desktop\\balls\\basketball_PNG1096.png"));
//        return e;
        return null;
    }

    public Entity create(Event ev, GameData gameData) {
//        ShootingEvent shootingEvent = (ShootingEvent) ev;
//        Entity e = new Projectile();
//
//        CannonPart cannonPart = ev.getSource().getPart(CannonPart.class);
//        Vector2D cannonCentre = cannonPart.getMuzzleFaceCentre();
//        e.add(new PositionPart(cannonCentre.getX(),cannonCentre.getY(), cannonPart.getDirection()));
//        Vector2D accelerationVector = cannonPart.getDirectionVector();
//        accelerationVector.multiplyWithConstant(shootingEvent.getFirepower());
//        e.add( new MovementPart(accelerationVector, 10000));
//
//        e.add(new ShapePart());
//        e.add(new CirclePart(30,30,4));
//        e.add(new PhysicsPart(30, -90.82f));
//        e.add(new CollisionPart(true,0));
//        e.add(new DamagePart(20,1));
//        e.add(new TexturePart("mario_red_mushroom.png"));
//        return e;
        return null;
    }
}
