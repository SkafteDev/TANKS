package dk.grp1.tanks.weapon.ElVicto.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.eventManager.events.SoundEvent;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.common.utils.Vector2D;

public class ElVictoWeapon implements IWeapon {

    private final String name = "El Victo";
    private final String description = "Fires a mexican victor";
    private final String iconPath = "elvicto.png";
    private final String shootSoundPath= "aiaiai.mp3";
    private final String texturePath = "elvicto.png";
    private final String explosionTexturePath = "explosionWhite.png";
    private final int explosionTextureFrameRows = 3;
    private final int explosionTextureFrameCols = 7;

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
        ElVicto wep = new ElVicto();

        CannonPart cannonPart = actor.getPart(CannonPart.class);
        Vector2D cannonCentre = cannonPart.getMuzzleFaceCentre();
        wep.add(new PositionPart(cannonCentre.getX(),cannonCentre.getY(), cannonPart.getDirection()));
        Vector2D accelerationVector = cannonPart.getDirectionVector();
        accelerationVector.multiplyWithConstant(firePower);
        wep.add(new MovementPart(accelerationVector, 10000));
        wep.add(new ShapePart());
        wep.add(new CirclePart(cannonCentre.getX(),cannonCentre.getY(),5));
        wep.add(new PhysicsPart(30, -90.82f));
        wep.add(new CollisionPart(true,0));
        wep.add(new DamagePart(40,10));
        wep.add(new TexturePart(this.texturePath));
        wep.add(new ExplosionTexturePart(explosionTextureFrameCols,explosionTextureFrameRows,explosionTexturePath));
        SoundPart sounds = new SoundPart(shootSoundPath,shootSoundPath);
        wep.add(sounds);
        gameData.getEventManager().addEvent(new SoundEvent(wep,sounds.getShootSoundPath()));

        world.addEntity(wep);
    }
}
