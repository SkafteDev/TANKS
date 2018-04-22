package dk.grp1.tanks.weapon.Teleporter.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.eventManager.events.SoundEvent;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.common.utils.Vector2D;

public class TeleporterWeapon implements IWeapon {

    private final String name = "Teleporter";
    private final String description = "Fires a small projectile to teleport to";
    private final String iconPath = "teleIcon.png";
    private final String texturePath = "teleport.png";
    private final String explosionTexturePath = "explosionRainbow.png";
    private final int explosionTextureFrameRows = 3;
    private final int explosionTextureFrameCols = 7;
    private final String shootSound = "shoot.mp3";
    private final String teleSound = "tele.mp3";



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
        Teleporter wep = new Teleporter();

        CannonPart cannonPart = actor.getPart(CannonPart.class);
        Vector2D cannonCentre = cannonPart.getMuzzleFaceCentre();
        wep.add(new PositionPart(cannonCentre.getX(),cannonCentre.getY(), cannonPart.getDirection()));
        Vector2D accelerationVector = cannonPart.getDirectionVector();
        accelerationVector.multiplyWithConstant(firePower);
        wep.add(new MovementPart(accelerationVector, 10000));
        wep.add(new ShapePart());
        wep.add(new CirclePart(cannonCentre.getX(),cannonCentre.getY(),2));
        wep.add(new DamagePart(0,0));
        wep.add(new PhysicsPart(30, -90.82f));
        wep.add(new TeleportCollisionPart(true,0, actor));
        wep.add(new TexturePart(this.texturePath));
        wep.add(new ExplosionTexturePart(explosionTextureFrameCols,explosionTextureFrameRows,explosionTexturePath));
        SoundPart sounds = new SoundPart(shootSound,teleSound);
        wep.add(sounds);
        gameData.getEventManager().addEvent(new SoundEvent(wep,sounds.getShootSoundPath()));

        world.addEntity(wep);
    }
}
