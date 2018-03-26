package dk.grp1.tanks.BigShot.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.common.utils.Vector2D;

public class BigShotWeapon implements IWeapon {

    private final String name = "Big Shot";
    private final String description = "Fires a single shot";
    private final String iconPath = "bigshot.png";
    private final String texturePath = "bigshot.png";

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
    public void shoot(Entity actor, float firePower, World world) {
        BigShot bs = new BigShot();

        CannonPart cannonPart = actor.getPart(CannonPart.class);
        Vector2D cannonCentre = cannonPart.getMuzzleFaceCentre();
        bs.add(new PositionPart(cannonCentre.getX(),cannonCentre.getY(), cannonPart.getDirection()));
        Vector2D accelerationVector = cannonPart.getDirectionVector();
        accelerationVector.multiplyWithConstant(firePower);
        bs.add(new MovementPart(accelerationVector, 10000));
        bs.add(new ShapePart());
        bs.add(new CirclePart(30,30,4));
        bs.add(new PhysicsPart(30, -90.82f));
        bs.add(new CollisionPart(true,0));
        bs.add(new DamagePart(10,1));
        bs.add(new TexturePart(this.texturePath));

        world.addEntity(bs);
    }
}
