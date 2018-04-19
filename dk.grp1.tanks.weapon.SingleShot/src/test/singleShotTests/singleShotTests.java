package singleShotTests;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.weapon.Projectile;
import dk.grp1.tanks.weapon.SingleShot.internal.SingleShot;
import dk.grp1.tanks.weapon.SingleShot.internal.SingleShotPlugin;
import org.junit.Assert;
import org.junit.Test;

public class singleShotTests {

    @Test
    public void testStartMethod(){
        GameData gd = new GameData();
        World world = new World();
        SingleShotPlugin ss = new SingleShotPlugin();
        ss.start(world, gd);
        Assert.assertEquals(1, gd.getWeapons().size());
    }

    @Test
    public void testStopMethod(){
        GameData gd = new GameData();
        World world = new World();
        SingleShotPlugin ss = new SingleShotPlugin();
        ss.start(world, gd);
        ss.stop(world, gd);
        Assert.assertEquals(0, gd.getWeapons().size());
    }
}
