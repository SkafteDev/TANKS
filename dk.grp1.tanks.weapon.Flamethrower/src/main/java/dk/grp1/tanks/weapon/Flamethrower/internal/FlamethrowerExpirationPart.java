package dk.grp1.tanks.weapon.Flamethrower.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.eventManager.events.SoundEvent;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.Random;

public class FlamethrowerExpirationPart extends ExpirationPart {

    private float timeSinceLastShot;
    private float firepower;
    private Vector2D direction;
    private Random random;

    public FlamethrowerExpirationPart(float timeToLive, float firepowe, Vector2D direction){
        setRemainingLifeTime(timeToLive);
        this.timeSinceLastShot = 0;
        this.firepower = firepower;
        this.direction = direction;
        this.random = new Random();
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {
        super.setRemainingLifeTime(super.getRemainingLifeTime()-gameData.getDelta());

        if (getRemainingLifeTime() <= 0){
            world.removeEntity(entity);
        }

        timeSinceLastShot += gameData.getDelta();

        if (timeSinceLastShot >= 0.05){
            Entity e = createFlame(entity);
            world.addEntity(e);
            SoundPart sounds = e.getPart(SoundPart.class);
            gameData.getEventManager().addEvent(new SoundEvent(e,sounds.getShootSoundPath()));
            timeSinceLastShot = 0;
        }


    }

    private Flamethrower createFlame(Entity parrent){
        PositionPart parrentPos = parrent.getPart(PositionPart.class);

        Flamethrower flamethrower = new Flamethrower();
        flamethrower.add(new PositionPart(parrentPos.getX(), parrentPos.getY(), parrentPos.getDirectionInRadians()));
        float i = (random.nextFloat()*20 + 90) /100;
        Vector2D accelVector = new Vector2D(direction.getX()*i,direction.getY()*i);
        flamethrower.add(new MovementPart(accelVector, 1000));
        flamethrower.add(new ShapePart());
        flamethrower.add(new CirclePart(parrentPos.getX(), parrentPos.getY(),2));
        flamethrower.add(new PhysicsPart(30, -90.82f));
        flamethrower.add(new CollisionPart(true,0));
        flamethrower.add(new DamagePart(3,2));
        flamethrower.add(new TexturePart("flame.png"));
        flamethrower.add(new ExplosionTexturePart(8,8,"flame_animation.png"));

        SoundPart sounds = new SoundPart("shot.mp3","burning.mp3");
        flamethrower.add(sounds);

        return flamethrower;
    }
}
