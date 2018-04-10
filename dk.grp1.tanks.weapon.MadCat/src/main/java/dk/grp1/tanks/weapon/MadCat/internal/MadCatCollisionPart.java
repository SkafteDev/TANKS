package dk.grp1.tanks.weapon.MadCat.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.eventManager.events.Event;
import dk.grp1.tanks.common.events.SoundEvent;
import dk.grp1.tanks.common.utils.Vector2D;
import javafx.geometry.Pos;

import java.util.ArrayList;

public class MadCatCollisionPart extends CollisionPart {

    /**
     * Creates a collision part for an entity
     *
     * @param canCollide
     * @param minTimeBetweenCollision
     */
    public MadCatCollisionPart(boolean canCollide, float minTimeBetweenCollision) {
        super(canCollide, minTimeBetweenCollision);
    }


    @Override
    public void processPart(Entity entity, GameData gameData, World world) {
        if (isHitGameMap() || isHitEntity()){
            world.removeEntity(entity);
            for (Entity cat : createCats(entity)) {
                world.addEntity(cat);
            }
        }
    }


    private ArrayList<MadCat> createCats(Entity parent) {
        ArrayList<MadCat> cats = new ArrayList<>();
        float i = -1.5f;

        for (int j = 0; j < 7; j++) {

            MadCat cat = new MadCat();

            PositionPart parrentPos = parent.getPart(PositionPart.class);
            cat.add(new PositionPart(parrentPos.getX(), parrentPos.getY()+5, parrentPos.getDirectionInRadians()));
            Vector2D acc = new Vector2D(i,6);
            acc.multiplyWithConstant(20);
            cat.add(new MovementPart(acc, 10000));
            cat.add(new ShapePart());
            cat.add(new CirclePart(parrentPos.getX(), parrentPos.getY(), 3));
            cat.add(new PhysicsPart(30, -90.82f));
            cat.add(new CollisionPart(true, 0));
            cat.add(new DamagePart(5, 20));
            cat.add(parent.getPart(TexturePart.class));
            cat.add(parent.getPart(ExplosionTexturePart.class));
            SoundPart soundPart = parent.getPart(SoundPart.class);
            cat.add(soundPart);
            cats.add(cat);
            i += 0.5f;
        }

        return cats;
    }


}
