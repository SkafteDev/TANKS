package dk.grp1.tanks.weapon.grenade.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.events.SoundEvent;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.common.utils.Vector2D;

public class GrenadeWeapon implements IWeapon {
    private final String name = "Grenade";
    private final String description = "Throws a grenade";
    private final String iconPath = "grenade.png";
    private final String texturePath = "grenade.png";
    private final String explosionTexturePath = "explosion.png";
    private final String shootSoundPath = "grenadeSound.mp3";
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
        Grenade grenade = new Grenade();

        CannonPart cannonPart = actor.getPart(CannonPart.class);
        Vector2D cannonCentre = cannonPart.getMuzzleFaceCentre();
        grenade.add(new PositionPart(cannonCentre.getX(),cannonCentre.getY(), cannonPart.getDirection()));
        Vector2D accelerationVector = cannonPart.getDirectionVector();
        accelerationVector.multiplyWithConstant(firePower);
        grenade.add(new GrenadeMovementPart(accelerationVector, 10000));
        grenade.add(new GrenadeExpirationPart(4));
        grenade.add(new ShapePart());
        grenade.add(new CirclePart(cannonCentre.getX(),cannonCentre.getY(),5));
        grenade.add(new PhysicsPart(30, -90.82f));
        grenade.add(new GrenadeCollisionPart(true,0));
        grenade.add(new DamagePart(7,25));
        grenade.add(new TexturePart(this.texturePath));
        grenade.add(new ExplosionTexturePart(explosionTextureFrameCols,explosionTextureFrameRows,explosionTexturePath));
        world.addEntity(grenade);
        gameData.getEventManager().addEvent(new SoundEvent(grenade, shootSoundPath));
    }
}
