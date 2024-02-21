package weaponplugintests;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.weapon.Projectile;
import dk.grp1.tanks.weapon.internal.WeaponPlugin;
import org.junit.Assert;
import org.junit.Test;

public class WeaponLuginTest {

    @Test
    public void testStartMethod(){
        GameData gd = new GameData();
        World world = new World();
        WeaponPlugin weaponPlugin = new WeaponPlugin();
        weaponPlugin.start(world, gd);
    }

    @Test
    public void testStopMethod(){
        GameData gd = new GameData();
        World world = new World();
        WeaponPlugin weaponPlugin = new WeaponPlugin();
        weaponPlugin.start(world, gd);
        world.addEntity(new Projectile() {
        });
        weaponPlugin.stop(world, gd);
        Assert.assertEquals(0, world.getEntities().size());
    }
}
