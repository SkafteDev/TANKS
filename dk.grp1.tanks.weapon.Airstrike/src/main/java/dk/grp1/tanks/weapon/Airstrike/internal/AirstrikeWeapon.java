package dk.grp1.tanks.weapon.Airstrike.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.common.utils.Vector2D;

public class AirstrikeWeapon implements IWeapon {
    private final String name = "Airstrike";
    private final String description = "Drops an airstrike";
    private final String iconPath = "airstrike_bomb.png";
    private final String texturePath = "airstrike_flare.png";
    private final String explosionTexturePath = "explosion.png";
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
        Airstrike airstrike = new Airstrike();

        CannonPart cannonPart = actor.getPart(CannonPart.class);
        Vector2D cannonCentre = cannonPart.getMuzzleFaceCentre();
        airstrike.add(new PositionPart(cannonCentre.getX(),cannonCentre.getY(), cannonPart.getDirection()));
        Vector2D accelerationVector = cannonPart.getDirectionVector();
        accelerationVector.multiplyWithConstant(firePower);
        airstrike.add(new AirstrikeMovementPart(accelerationVector, 10000));
        airstrike.add(new ShapePart());
        airstrike.add(new AirstrikeExpirationPart(4));
        airstrike.add(new CirclePart(cannonCentre.getX(),cannonCentre.getY(),5));
        airstrike.add(new PhysicsPart(30, -90.82f));
        airstrike.add(new AirstrikeCollisionPart(true,0));
        airstrike.add(new DamagePart(7,25));
        airstrike.add(new TexturePart(this.texturePath));
        airstrike.add(new ExplosionTexturePart(explosionTextureFrameCols,explosionTextureFrameRows,explosionTexturePath));
        world.addEntity(airstrike);
    }
}
