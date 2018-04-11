package dk.grp1.tanks.weapon.Airstrike.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;

public class AirstrikeExpirationPart extends ExpirationPart {

    private boolean isDropping;
    private int numDropped = 0;


    public AirstrikeExpirationPart(float timeToLive) {
        super.setRemainingLifeTime(timeToLive);
        this.isDropping = false;
    }


    @Override
    public void processPart(Entity entity, GameData gameData, World world) {
        super.setRemainingLifeTime(super.getRemainingLifeTime() - gameData.getDelta());

        if (getRemainingLifeTime() <= 0) {

            if (isDropping) {

                world.removeEntity(entity);
            } else {
                world.addEntity(createAirstrike(entity, gameData.getGameHeight()));
                numDropped++;
                setRemainingLifeTime(0.5f);

                if (numDropped == 5) {
                    this.isDropping = true;
                    setRemainingLifeTime(2);
                }
            }
        }
    }


    private ArrayList<Airstrike> createAirstrikes(Entity entity, float gameHeight) {
        ArrayList<Airstrike> airstrikes = new ArrayList<>();

        PositionPart flarePos = entity.getPart(PositionPart.class);

        float i = flarePos.getX() - 40;

        for (int j = 0; j < 5; j++) {

            Airstrike airstrike = new Airstrike();

            airstrike.add(new PositionPart(i, gameHeight, 0));
            airstrike.add(new MovementPart(new Vector2D(0, -40), 10000));
            airstrike.add(new ShapePart());
            airstrike.add(new CirclePart(i, gameHeight, 5));
            airstrike.add(new PhysicsPart(30, -90.82f));
            airstrike.add(new CollisionPart(true, 0));
            airstrike.add(new DamagePart(7, 10));
            airstrike.add(new TexturePart("airstrike_bomb.png"));
            airstrike.add(entity.getPart(ExplosionTexturePart.class));

            airstrikes.add(airstrike);

            i += 20;
        }

        return airstrikes;
    }

    private Airstrike createAirstrike(Entity entity, float gameHeight) {
        PositionPart flarePos = entity.getPart(PositionPart.class);

        float i = flarePos.getX() - 40 + (20 * numDropped);

        Airstrike airstrike = new Airstrike();

        airstrike.add(new PositionPart(i, gameHeight, 0));
        airstrike.add(new MovementPart(new Vector2D(0, -40), 10000));
        airstrike.add(new ShapePart());
        airstrike.add(new CirclePart(i, gameHeight, 5));
        airstrike.add(new PhysicsPart(30, -90.82f));
        airstrike.add(new CollisionPart(true, 0));
        airstrike.add(new DamagePart(7, 10));
        airstrike.add(new TexturePart("airstrike_bomb.png"));
        airstrike.add(entity.getPart(ExplosionTexturePart.class));


        return airstrike;
    }
}
