package dk.grp1.tanks.weapon.boxingglove.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.eventManager.events.SoundEvent;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.common.utils.Vector2D;

public class BoxingWeapon implements IWeapon {
    private final String name = "Boxing Glove";
    private final String description = "Punches enemies away";
    private final String iconPath = "glove.png";
    private final String texturePath = "glove.png";


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
        BoxingGlove BoxingGlove = new BoxingGlove();

        CannonPart cannonPart = actor.getPart(CannonPart.class);
        Vector2D cannonCentre = cannonPart.getMuzzleFaceCentre();
        BoxingGlove.add(new PositionPart(cannonCentre.getX(),cannonCentre.getY(), cannonPart.getDirection()));
        Vector2D accelerationVector = cannonPart.getDirectionVector();
        accelerationVector.multiplyWithConstant(firePower);
        BoxingGlove.add(new MovementPart(accelerationVector, 10000));
        BoxingGlove.add(new ShapePart());
        BoxingGlove.add(new CirclePart(cannonCentre.getX(),cannonCentre.getY(),4));
        BoxingGlove.add(new PhysicsPart(30, -90.82f));
        BoxingGlove.add(new BoxingCollisionPart(true,0, 100, 500));
        BoxingGlove.add(new DamagePart(5,10));
        BoxingGlove.add(new TexturePart(this.texturePath));
        SoundPart sounds = new SoundPart("punch.mp3","punch.mp3");
        BoxingGlove.add(sounds);
        gameData.getEventManager().addEvent(new SoundEvent(BoxingGlove,sounds.getShootSoundPath()));


        world.addEntity(BoxingGlove);
    }
}
