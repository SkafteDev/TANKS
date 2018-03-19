package partstests;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.parts.CollisionPart;
import dk.grp1.tanks.common.data.parts.MovementPart;
import dk.grp1.tanks.common.data.parts.PhysicsPart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.utils.Vector2D;
import org.junit.Assert;

public class MovementPartTest {

    @org.junit.Test
    public void processPart() {
        Entity entity = new Entity() {
            public String getID() {
                return super.getID();
            }
        };
        MovementPart mov = new MovementPart(42);
        entity.add(mov);

        mov.setVelocity(3,4);

        GameData gameData = new GameData();
        gameData.setDelta(1/60f);

        mov.processPart(entity,gameData);

        // Assert that given no position, nothing changes
        Assert.assertEquals(3, mov.getVelocity().getX(),0.0005);
        Assert.assertEquals(4, mov.getVelocity().getY(),0.0005);

        PositionPart pos = new PositionPart();
        pos.setX(100);
        pos.setY(200);
        entity.add(pos);

        float expectedX = 100 + gameData.getDelta()* mov.getVelocity().getX();
        float expectedY = 200 + gameData.getDelta()* mov.getVelocity().getY();

        mov.processPart(entity,gameData);

        // Assert that given a position and velocity, the position changes correctly
        Assert.assertEquals(expectedX, pos.getX(),0.0005);
        Assert.assertEquals(expectedY, pos.getY(),0.0005);


        // Assert that given a velocity and a gravity, the velocity and position changes correctly
        PhysicsPart phys = new PhysicsPart(999, -10);
        entity.add(phys);
        phys.processPart(entity,gameData);

        float expectedVelX = mov.getVelocity().getX() + phys.getGravityVector().getX();
        float expectedVelY = mov.getVelocity().getY() + phys.getGravityVector().getY();


        expectedX = pos.getX()+ gameData.getDelta() * mov.getVelocity().getX() + gameData.getDelta() * phys.getGravityVector().getX();
        expectedY = pos.getY()+ gameData.getDelta() * mov.getVelocity().getY() + gameData.getDelta() * phys.getGravityVector().getY();

        mov.processPart(entity,gameData);

        Assert.assertEquals(expectedX, pos.getX(),0.0005);
        Assert.assertEquals(expectedY, pos.getY(),0.0005);
        Assert.assertEquals(expectedVelX, mov.getVelocity().getX(),0.0005);
        Assert.assertEquals(expectedVelY, mov.getVelocity().getY(),0.0005);



        // Assert that everything behaves correctly in case of collision
        entity.remove(PhysicsPart.class);
        CollisionPart col = new CollisionPart(true,0.5f);
        col.setHitGameMap(true);
        entity.add(col);

        expectedVelX = mov.getVelocity().getX();
        expectedVelY = 0;

        expectedX = pos.getX() + gameData.getDelta() * mov.getVelocity().getX();
        expectedY = pos.getY();

        mov.processPart(entity,gameData);

        Assert.assertEquals(expectedVelX, mov.getVelocity().getX(),0.0005);
        Assert.assertEquals(expectedVelY, mov.getVelocity().getY(),0.0005);
        Assert.assertEquals(expectedX, pos.getX(),0.0005);
        Assert.assertEquals(expectedY, pos.getY(),0.0005);


    }

    @org.junit.Test
    public void getCurrentSpeed() {
        Vector2D velocityWithSpeed5 = new Vector2D(3,4);
        MovementPart mov = new MovementPart(velocityWithSpeed5, Float.MAX_VALUE);
        Assert.assertEquals(5,mov.getCurrentSpeed(),0.0000005);


    }

    @org.junit.Test
    public void getVelocity() {
        Vector2D velocity = new Vector2D(2,5);
        MovementPart mov = new MovementPart(velocity, Float.MAX_VALUE);
        Assert.assertEquals(velocity, mov.getVelocity());
    }

    @org.junit.Test
    public void setVelocity() {
        // Test that set velocity sets velocity
        MovementPart mov = new MovementPart(Float.MAX_VALUE);
        Vector2D velocity = new Vector2D(3,4);
        mov.setVelocity(velocity);
        Assert.assertEquals(velocity,mov.getVelocity());

        //Test that set velocity limits velocity to max speed
        float maxSpeed = 10;
        mov = new MovementPart(maxSpeed);
        Vector2D velocityWithSpeed5 = new Vector2D(3,4);
        mov.setVelocity(velocityWithSpeed5);
        Assert.assertEquals(5,mov.getCurrentSpeed(),0.0000005);


        Vector2D velocityWithSpeed10 = new Vector2D(6,8);
        mov.setVelocity(velocityWithSpeed10);
        Assert.assertEquals(10,mov.getCurrentSpeed(),0.0000005);

        Vector2D velocityWithSpeed15 = new Vector2D(9,12);
        mov.setVelocity(velocityWithSpeed15);
        Assert.assertEquals(10,mov.getCurrentSpeed(),0.0000005);

    }

    @org.junit.Test
    public void setVelocity1() {

        // Test that set velocity sets velocity
        MovementPart mov = new MovementPart(Float.MAX_VALUE);
        float x = 3;
        float y = 4;
        mov.setVelocity(x,y);
        Assert.assertEquals(x,mov.getVelocity().getX(),0.0000005);
        Assert.assertEquals(y,mov.getVelocity().getY(),0.0000005);


        //Test that set velocity limits velocity to max speed
        float maxSpeed = 10;
        mov = new MovementPart(maxSpeed);
        x = 3;
        y = 4;
        mov.setVelocity(3,4);
        Assert.assertEquals(5,mov.getCurrentSpeed(),0.0000005);

        x = 6;
        y = 8;
        mov.setVelocity(x,y);
        Assert.assertEquals(10,mov.getCurrentSpeed(),0.0000005);

        x = 9;
        y = 12;
        mov.setVelocity(x,y);
        Assert.assertEquals(10,mov.getCurrentSpeed(),0.0000005);
    }

    @org.junit.Test
    public void getMaxSpeed() {
        float maxSpeed = 48;
        MovementPart mov = new MovementPart(maxSpeed);
        Assert.assertEquals(maxSpeed, mov.getMaxSpeed(), 0.00005);
    }

    @org.junit.Test
    public void setMaxSpeed() {
        MovementPart mov = new MovementPart(123);
        Assert.assertEquals(123, mov.getMaxSpeed(), 0.000005);
        mov.setMaxSpeed(987.664f);
        Assert.assertEquals(987.664f,mov.getMaxSpeed(),0.000005);
    }
}
