package singleShotTests;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CannonPart;
import dk.grp1.tanks.weapon.SingleShot.internal.SingleShot;
import dk.grp1.tanks.weapon.SingleShot.internal.SingleShotWeapon;
import org.junit.Assert;
import org.junit.Test;

public class singleShotWeaponTest {

    @Test
    public void testShoot(){
        World world = new World();
        GameData gd = new GameData();
        SingleShotWeapon ssw = new SingleShotWeapon();
        Entity entity = new Entity() {};
        CannonPart cannonPart = new CannonPart(1,1,1,1,1, "path");
        entity.add(cannonPart);
        cannonPart.processPart(entity, gd, world);
        ssw.shoot(entity, gd, 10, world);
        Assert.assertEquals(1, world.getEntities(SingleShot.class).size());
    }
}
