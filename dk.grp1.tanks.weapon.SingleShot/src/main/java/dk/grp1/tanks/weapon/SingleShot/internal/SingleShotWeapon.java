package dk.grp1.tanks.weapon.SingleShot.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.common.utils.Vector2D;

public class SingleShotWeapon implements IWeapon {

    private final String name = "Single Shot";
    private final String description = "Fires a single shot";
    private final String iconPath = "singleshot.png";
    private final String texturePath = "singleshot.png";
    private final String explosionTexturePath = "explosion.png";
    private final String shootSoundPath = "boom.mp3";
    private final int explosionTextureFrameRows = 6;
    private final int explosionTextureFrameCols = 8;

    @Override
    public String getName() {
        return this.name;
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
        SingleShot ss = new SingleShot();

        CannonPart cannonPart = actor.getPart(CannonPart.class);
        Vector2D cannonCentre = cannonPart.getMuzzleFaceCentre();
        ss.add(new PositionPart(cannonCentre.getX(),cannonCentre.getY(), cannonPart.getDirection()));
        Vector2D accelerationVector = cannonPart.getDirectionVector();
        accelerationVector.multiplyWithConstant(firePower);
        ss.add(new MovementPart(accelerationVector, 10000));
        ss.add(new ShapePart());
        ss.add(new CirclePart(cannonCentre.getX(),cannonCentre.getY(),2));
        ss.add(new PhysicsPart(30, -90.82f));
        ss.add(new CollisionPart(true,0));
        ss.add(new DamagePart(4,10));
        ss.add(new TexturePart(this.texturePath));
        ss.add(new ExplosionTexturePart(explosionTextureFrameCols,explosionTextureFrameRows,explosionTexturePath));

        world.addEntity(ss);
    }
}
