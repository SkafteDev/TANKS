package partstests;

import dk.grp1.tanks.common.data.parts.MovementPart;
import dk.grp1.tanks.common.utils.Vector2D;
import org.junit.Assert;

public class MovementPartTest {

    @org.junit.Test
    public void processPart() {
    }

    @org.junit.Test
    public void getCurrentSpeed() {
        Assert.assertEquals(3,5);
        System.out.println("TEEEEEEEEST");
    }

    @org.junit.Test
    public void getVelocity() {
        Vector2D velocity = new Vector2D(2,5);
        MovementPart mov = new MovementPart(velocity, Float.MAX_VALUE);
        Assert.assertEquals(velocity, mov.getVelocity());
    }

    @org.junit.Test
    public void setVelocity() {
        MovementPart mov = new MovementPart(Float.MAX_VALUE);
        Vector2D velocity = new Vector2D(3,4);
        mov.setVelocity(velocity);
        Assert.assertEquals(velocity,mov.getVelocity());
    }

    @org.junit.Test
    public void setVelocity1() {

    }

    @org.junit.Test
    public void getMaxSpeed() {
    }

    @org.junit.Test
    public void setMaxSpeed() {
    }
}
