package dk.grp1.tanks.common.data.parts;

import org.junit.Test;

import static org.junit.Assert.*;

public class CollisionPartTest {

    private boolean canCollide = true;
    private float minTimeBetweenCollision = 1;
    private CollisionPart cp;

    @Test
    public void processPart() {
    }

    @Test
    public void isHitGameMap() {
        cp = new CollisionPart(canCollide, minTimeBetweenCollision);
        assertEquals(false, cp.isHitGameMap());

        cp = new CollisionPart(canCollide, minTimeBetweenCollision);
        cp.setHitGameMap(true);
        assertEquals(true, cp.isHitGameMap());
    }

    @Test
    public void setHitGameMap() {
        cp = new CollisionPart(canCollide, minTimeBetweenCollision);
        assertEquals(false, cp.isHitGameMap());
        cp.setHitGameMap(true);
        assertEquals(true, cp.isHitGameMap());
    }

    @Test
    public void canCollide() {
        cp = new CollisionPart(canCollide, minTimeBetweenCollision);
        assertEquals(true, cp.canCollide());

        cp = new CollisionPart(canCollide, minTimeBetweenCollision);
        cp.setCanCollide(false);
        assertEquals(false, cp.canCollide());
    }

    @Test
    public void setCanCollide() {
        cp = new CollisionPart(canCollide, minTimeBetweenCollision);
        cp.setCanCollide(false);
        assertEquals(false, cp.canCollide());


        cp = new CollisionPart(canCollide, minTimeBetweenCollision);
        cp.setCanCollide(true);
        assertEquals(true, cp.canCollide());
    }

    @Test
    public void isHitEntity() {
        cp = new CollisionPart(canCollide, minTimeBetweenCollision);
        assertEquals(false, cp.isHitEntity());

        cp = new CollisionPart(canCollide, minTimeBetweenCollision);
        cp.setHitEntity(true);
        assertEquals(true, cp.isHitEntity());
    }

    @Test
    public void setHitEntity() {
        cp = new CollisionPart(canCollide, minTimeBetweenCollision);
        assertEquals(false, cp.isHitEntity());
        cp.setHitEntity(true);
        assertEquals(true, cp.isHitEntity());
    }
}