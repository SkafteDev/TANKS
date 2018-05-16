package dk.grp1.tanks.weapon.Earthquake.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.eventManager.events.SoundEvent;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeWeapon implements IWeapon {

    private final String name = "Earthquake";
    private final String description = "Fires a enemy seeking missile";
    private final String iconPath = "homingmissile.png";
    private final String shootSoundPath= "boom.mp3";
    private final String texturePath = "homingmissile.png";
    private final String explosionTexturePath = "explosion.png";
    private final int explosionTextureFrameRows = 6;
    private final int explosionTextureFrameCols = 8;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getIconPath() {
        return iconPath;
    }

    @Override
    public void shoot(Entity actor, GameData gameData, float firePower, World world) {
        List<Entity> test = new ArrayList<>();
        for (Entity entity : world.getEntities()) {
            test.add(entity);
        }
        // spawn a entity on top of every player and enemy on the map
        for (Entity entity : test) {
            CannonPart cannonPart = actor.getPart(CannonPart.class);
            Earthquake wep = new Earthquake();
            PositionPart positionPartTest = entity.getPart(PositionPart.class);
            wep.add(new PositionPart(positionPartTest.getX(),positionPartTest.getY(), cannonPart.getDirection()));


            Vector2D accelerationVector = cannonPart.getDirectionVector();
            accelerationVector.multiplyWithConstant(firePower);
            wep.add(new MovementPart(accelerationVector.unitVector(), 10000));
            wep.add(new ShapePart());
            wep.add(new CirclePart(positionPartTest.getX(),positionPartTest.getY(),2));
            wep.add(new PhysicsPart(30, -90.82f));
            wep.add(new CollisionPart(true,0));
            wep.add(new DamagePart(40,5));
            wep.add(new TexturePart(this.texturePath));
            wep.add(new EarthquakeExpirationPart());
            //wep.add(new ExplosionTexturePart(explosionTextureFrameCols,explosionTextureFrameRows,explosionTexturePath));
            SoundPart sounds = new SoundPart("boom.mp3","boom.mp3");
            wep.add(sounds);
            gameData.getEventManager().addEvent(new SoundEvent(wep,sounds.getShootSoundPath()));

            world.addEntity(wep);
        }
    }
}
