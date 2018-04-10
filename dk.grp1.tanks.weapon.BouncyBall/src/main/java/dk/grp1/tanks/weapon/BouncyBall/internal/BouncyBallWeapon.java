package dk.grp1.tanks.weapon.BouncyBall.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.events.SoundEvent;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.common.utils.Vector2D;
import dk.grp1.tanks.common.data.parts.SoundPart;

public class BouncyBallWeapon implements IWeapon{

    private final String name = "BouncyBall";
    private final String description = "Shoots a bouncing ball";
    private final String iconPath = "bouncy_ball.png";
    private final String texturePath = "bouncy_ball.png";
    private final String explosionTexturePath = "explosionWhite.png";
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
        BouncyBall bouncyBall = new BouncyBall();

        CannonPart cannonPart = actor.getPart(CannonPart.class);
        Vector2D cannonCentre = cannonPart.getMuzzleFaceCentre();
        bouncyBall.add(new PositionPart(cannonCentre.getX(),cannonCentre.getY(), cannonPart.getDirection()));
        Vector2D accelerationVector = cannonPart.getDirectionVector();
        accelerationVector.multiplyWithConstant(firePower);
        bouncyBall.add(new BouncyBallMovementPart(accelerationVector, 10000));
        bouncyBall.add(new BouncyBallExpirationPart(10));
        bouncyBall.add(new ShapePart());
        bouncyBall.add(new CirclePart(cannonCentre.getX(),cannonCentre.getY(),4));
        bouncyBall.add(new PhysicsPart(30, -90.82f));
        bouncyBall.add(new BouncyBallCollisionPart(true,0));
        bouncyBall.add(new DamagePart(5,10));
        bouncyBall.add(new TexturePart(this.texturePath));
        bouncyBall.add(new ExplosionTexturePart(explosionTextureFrameCols,explosionTextureFrameRows,explosionTexturePath));
        SoundPart sounds = new SoundPart("boom.mp3","boing.mp3");
        bouncyBall.add(sounds);
        gameData.getEventManager().addEvent(new SoundEvent(bouncyBall,sounds.getShootSoundPath()));


        world.addEntity(bouncyBall);
    }
}
