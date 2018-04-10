package dk.grp1.tanks.weapon.BouncyBall.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.events.SoundEvent;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.common.utils.Vector2D;
import dk.grp1.tanks.weapon.WeaponSoundPart;

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
        BouncyBall BouncyBall = new BouncyBall();

        CannonPart cannonPart = actor.getPart(CannonPart.class);
        Vector2D cannonCentre = cannonPart.getMuzzleFaceCentre();
        BouncyBall.add(new PositionPart(cannonCentre.getX(),cannonCentre.getY(), cannonPart.getDirection()));
        Vector2D accelerationVector = cannonPart.getDirectionVector();
        accelerationVector.multiplyWithConstant(firePower);
        BouncyBall.add(new BouncyBallMovementPart(accelerationVector, 10000));
        BouncyBall.add(new BouncyBallExpirationPart(10));
        BouncyBall.add(new ShapePart());
        BouncyBall.add(new CirclePart(cannonCentre.getX(),cannonCentre.getY(),4));
        BouncyBall.add(new PhysicsPart(30, -90.82f));
        BouncyBall.add(new BouncyBallCollisionPart(true,0));
        BouncyBall.add(new DamagePart(5,10));
        BouncyBall.add(new TexturePart(this.texturePath));
        BouncyBall.add(new ExplosionTexturePart(explosionTextureFrameCols,explosionTextureFrameRows,explosionTexturePath));
        WeaponSoundPart sounds = new  WeaponSoundPart("boom.mp3","boing.mp3");
        BouncyBall.add(sounds);
        gameData.getEventManager().addEvent(new SoundEvent(BouncyBall,sounds.getShootSoundPath()));


        world.addEntity(BouncyBall);
    }
}
