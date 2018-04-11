package dk.grp1.tanks.weapon.nuke.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.eventManager.events.SoundEvent;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.common.utils.Vector2D;
import dk.grp1.tanks.weapon.Projectile;

public class NukeWeapon implements IWeapon {
    private final String name = "Nuke";
    private final String description = "Nukes the map or enemy";
    private final String iconPath = "nuke.png";
    private final String texturePath = "nuke.png";
    private final String explosionTexturePath = "explosionWhite.png";
    private final String shootSoundPath = "Nuclear.mp3";
    private final String explosionSoundPath = "nukeExplode.mp3";
    private final int explosionTextureFrameRows = 3;
    private final int explosionTextureFrameCols = 7;

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
        Projectile nuke = new Nuke();

        CannonPart cannonPart = actor.getPart(CannonPart.class);
        Vector2D cannonCentre = cannonPart.getMuzzleFaceCentre();
        nuke.add(new PositionPart(cannonCentre.getX(),cannonCentre.getY(), cannonPart.getDirection()));
        Vector2D accelerationVector = cannonPart.getDirectionVector();
        accelerationVector.multiplyWithConstant(firePower);
        nuke.add(new MovementPart(accelerationVector, 10000));
        nuke.add(new ShapePart());
        nuke.add(new CirclePart(cannonCentre.getX(),cannonCentre.getY(),4));
        nuke.add(new PhysicsPart(30, -90.82f));
        nuke.add(new CollisionPart(true,0));
        nuke.add(new DamagePart(50,40));
        nuke.add(new TexturePart(this.texturePath));
        nuke.add(new ExplosionTexturePart(explosionTextureFrameCols,explosionTextureFrameRows,explosionTexturePath));
        SoundPart sounds = new SoundPart(shootSoundPath, explosionSoundPath);
        nuke.add(sounds);
        gameData.getEventManager().addEvent(new SoundEvent(nuke, sounds.getShootSoundPath()));
        world.addEntity(nuke);
    }
}
