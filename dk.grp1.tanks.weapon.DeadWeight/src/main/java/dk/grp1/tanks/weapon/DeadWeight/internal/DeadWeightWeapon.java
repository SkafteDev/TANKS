package dk.grp1.tanks.DeadWeight.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.common.utils.Vector2D;

import javax.naming.ldap.Control;
import java.util.ArrayList;

public class DeadWeightWeapon implements IWeapon {

    private final String name = "Dead Weight";
    private final String iconPath = "dead_weight.png";
    private final String description = "Stops all horizontal movement when above a tank";
    private final String texturePath = "dead_weight.png";


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getIconPath() {
        return this.iconPath;
    }

    @Override
    public void shoot(Entity actor, float firePower, World world) {
        DeadWeight dw = new DeadWeight();

        CannonPart cannonPart = actor.getPart(CannonPart.class);
        Vector2D cannonCentre = cannonPart.getMuzzleFaceCentre();
        dw.add(new PositionPart(cannonCentre.getX(),cannonCentre.getY(), cannonPart.getDirection()));
        Vector2D accelerationVector = cannonPart.getDirectionVector();
        accelerationVector.multiplyWithConstant(firePower);


        ArrayList<Entity> entities = new ArrayList<>();
        for (Entity e : world.getEntities()) {
            ControlPart controlPart = e.getPart(ControlPart.class);
            if (controlPart != null){
                entities.add(e);
            }
        }
        entities.remove(actor);


        dw.add(new DeadWeightMovementPart(accelerationVector, 10000, entities));




        dw.add(new ShapePart());
        dw.add(new CirclePart(cannonCentre.getX(),cannonCentre.getY(),4));
        dw.add(new PhysicsPart(30, -90.82f));
        dw.add(new CollisionPart(true,0));
        dw.add(new DamagePart(5,1));
        dw.add(new TexturePart(this.texturePath));

        world.addEntity(dw);
    }

}
