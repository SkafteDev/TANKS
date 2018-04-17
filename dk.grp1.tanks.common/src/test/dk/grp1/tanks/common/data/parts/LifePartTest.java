package dk.grp1.tanks.common.data.parts;

import org.junit.Test;

import static org.junit.Assert.*;

public class LifePartTest {

    private float maxHP = 100;
    private float currentHP = 10;
    private LifePart lp;


    @Test
    public void getHealthRatio() {
        lp = new LifePart();
        lp.setCurrentHP(currentHP);
        lp.setMaxHP(maxHP);
        assertEquals(currentHP/maxHP, lp.getHealthRatio(), 0.001);
    }

    @Test
    public void getMaxHP() {
        lp = new LifePart();
        lp.setCurrentHP(currentHP);
        lp.setMaxHP(maxHP);
        assertEquals(maxHP, lp.getMaxHP(), 0.001);
    }

    @Test
    public void setMaxHP() {
        lp = new LifePart();
        lp.setCurrentHP(currentHP);
        lp.setMaxHP(maxHP);
        assertEquals(maxHP, lp.getMaxHP(), 0.001);
    }

    @Test
    public void getCurrentHP() {
        lp = new LifePart();
        lp.setCurrentHP(currentHP);
        lp.setMaxHP(maxHP);
        assertEquals(currentHP, lp.getCurrentHP(), 0.001);
    }

    @Test
    public void setCurrentHP() {
        lp = new LifePart();
        lp.setCurrentHP(currentHP);
        lp.setMaxHP(maxHP);
        assertEquals(currentHP, lp.getCurrentHP(), 0.001);
    }

    @Test
    public void addHP() {
        lp = new LifePart();
        lp.setMaxHP(maxHP);
        lp.setCurrentHP(currentHP);
        assertEquals(currentHP, lp.getCurrentHP(), 0.001);

        lp.addHP(100000);
        assertEquals(maxHP, lp.getCurrentHP(), 0.001);
    }

    @Test
    public void removeHP() {
        lp = new LifePart();
        lp.setMaxHP(maxHP);
        lp.setCurrentHP(currentHP);
        assertEquals(currentHP, lp.getCurrentHP(), 0.001);

        lp.removeHP(100000);
        assertEquals(0, lp.getCurrentHP(), 0.001);
    }
}