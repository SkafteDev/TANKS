package dk.grp1.tanks.common.data.parts;

import org.junit.Test;

import static org.junit.Assert.*;

public class DamagePartTest {

    private float explosionRadius = 30;
    private float damage = 5;
    private DamagePart dp;

    @Test
    public void getExplosionRadius() {
        dp = new DamagePart(explosionRadius, damage);
        assertEquals(explosionRadius, dp.getExplosionRadius(), 0.001);
    }

    @Test
    public void setExplosionRadius() {
        dp = new DamagePart(explosionRadius, damage);
        assertEquals(explosionRadius, dp.getExplosionRadius(), 0.001);
        float er = 400;
        dp.setExplosionRadius(er);
        assertEquals(er, dp.getExplosionRadius(), 0.001);
    }

    @Test
    public void getDamage() {
        dp = new DamagePart(explosionRadius, damage);
        assertEquals(damage, dp.getDamage(), 0.001);
    }

    @Test
    public void setDamage() {
        dp = new DamagePart(explosionRadius, damage);
        assertEquals(damage, dp.getDamage(), 0.001);
        float dmg = 50;
        dp.setDamage(dmg);
        assertEquals(dmg, dp.getDamage(), 0.001);
    }

    @Test
    public void processPart() {
        //method not in use
    }
}