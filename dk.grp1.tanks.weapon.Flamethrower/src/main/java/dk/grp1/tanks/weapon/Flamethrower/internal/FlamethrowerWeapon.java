package dk.grp1.tanks.weapon.Flamethrower.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.eventManager.events.SoundEvent;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.common.utils.Vector2D;

public class FlamethrowerWeapon implements IWeapon {
    private final String name = "Flamethrower";
    private final String description = "Giant flamethrower";
    private final String iconPath = "flame.png";
    private final String shootSoundPath= "flamethrower.mp3";
    private final String texturePath = "flame.png";
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
        Flamethrower flamethrower = new Flamethrower();

        CannonPart cannonPart = actor.getPart(CannonPart.class);
        Vector2D cannonCentre = cannonPart.getMuzzleFaceCentre();
        flamethrower.add(new PositionPart(cannonCentre.getX(),cannonCentre.getY(), cannonPart.getDirection()));
        //flamethrower.add(new CirclePart(cannonCentre.getX(), cannonCentre.getY(), 2));

        Vector2D accelerationVector = cannonPart.getDirectionVector();
        accelerationVector.multiplyWithConstant(firePower);

        flamethrower.add(new FlamethrowerExpirationPart(3,firePower, accelerationVector));
        flamethrower.add(new FlamethrowerMovementPart(new Vector2D(10,10), 1100));
        world.addEntity(flamethrower);
    }
}
