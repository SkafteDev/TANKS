package dk.grp1.tanks.weapon.MadCat.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.common.utils.Vector2D;

public class MadCatWeapon implements IWeapon {

    private final String name = "Mad Cat";
    private final String description = "Fires a mad cat";
    private final String iconPath = "mad_cat.png";
    private final String texturePath = "mad_cat.png";
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
    public void shoot(Entity actor, float firePower, World world) {
        MadCat cat = new MadCat();

        CannonPart cannonPart = actor.getPart(CannonPart.class);
        Vector2D cannonCentre = cannonPart.getMuzzleFaceCentre();
        cat.add(new PositionPart(cannonCentre.getX(),cannonCentre.getY(), cannonPart.getDirection()));
        Vector2D accelerationVector = cannonPart.getDirectionVector();
        accelerationVector.multiplyWithConstant(firePower);
        cat.add(new MovementPart(accelerationVector, 10000));
        cat.add(new ShapePart());
        cat.add(new CirclePart(cannonCentre.getX(),cannonCentre.getY(),5));
        cat.add(new PhysicsPart(30, -90.82f));
        cat.add(new MadCatCollisionPart(true,0));
        cat.add(new DamagePart(10,20));
        cat.add(new TexturePart(this.texturePath));
        cat.add(new ExplosionTexturePart(explosionTextureFrameCols,explosionTextureFrameRows,explosionTexturePath));

        world.addEntity(cat);
    }
}
