package dk.grp1.tanks.weapon.holysheep.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.eventManager.events.SoundEvent;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.common.utils.Vector2D;

public class HolySheepWeapon implements IWeapon {


    private final String iconPath = "holysheep.png";
    private final String texturePath = "holysheep.png";
    private final String shootSoundPath = "sheep.mp3";
    private final String explosionSoundPath = "hallelujah.mp3";
    private final String explosionTexturePath = "explosion.png";
    private final int explosionTextureFrameRows = 6;
    private final int explosionTextureFrameCols = 8;

    @Override
    public String getName() {
        return "HolySheep";
    }

    @Override
    public String getDescription() {
        return "Holy sheep";
    }

    @Override
    public String getIconPath() {
        return iconPath;
    }

    @Override
    public void shoot(Entity actor, GameData gameData, float firePower, World world) {
        HolySheep holysheep = new HolySheep();

        CannonPart cannonPart = actor.getPart(CannonPart.class);
        Vector2D cannonCentre = cannonPart.getMuzzleFaceCentre();
        holysheep.add(new PositionPart(cannonCentre.getX(),cannonCentre.getY(), cannonPart.getDirection()));
        Vector2D accelerationVector = cannonPart.getDirectionVector();
        accelerationVector.multiplyWithConstant(6);

        ControlPart controlPart = new ControlPart(75);
        float direction = cannonPart.getDirection();
        if(direction > Math.PI/2 && direction < Math.PI*1.5 || direction < -Math.PI/2 && direction > -Math.PI*1.5){
            controlPart.setLeft(true);
            controlPart.setRight(false);
        } else {
            controlPart.setRight(true);
            controlPart.setLeft(false);
        }
        controlPart.setRotation(world.getGameMap().getDirectionVector(new Vector2D(cannonCentre.getX(), cannonCentre.getY())));
        holysheep.add(controlPart);
        holysheep.add(new MovementPart(new Vector2D(0,1),500));
        holysheep.add(new HolySheepExpirationPart(5 / 200.0f * firePower));
        holysheep.add(new ShapePart());
        holysheep.add(new CirclePart(cannonCentre.getX(),cannonCentre.getY(),5));
        holysheep.add(new PhysicsPart(30, -90.82f));
        holysheep.add(new HolySheepCollisionPart(true,0));
        holysheep.add(new DamagePart(50,25));
        holysheep.add(new TexturePart(this.texturePath));
        holysheep.add(new ExplosionTexturePart(explosionTextureFrameCols,explosionTextureFrameRows,explosionTexturePath));
        SoundPart sounds = new SoundPart(shootSoundPath, explosionSoundPath);



        holysheep.add(sounds);
        gameData.getEventManager().addEvent(new SoundEvent(holysheep, sounds.getShootSoundPath()));
        world.addEntity(holysheep);
    }
}
