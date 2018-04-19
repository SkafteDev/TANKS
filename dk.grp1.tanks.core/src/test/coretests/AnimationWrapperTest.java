package coretests;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.utils.Vector2D;
import dk.grp1.tanks.core.internal.AnimationWrapper;
import org.junit.Assert;
import org.junit.Test;

public class AnimationWrapperTest {


    @Test
    public void testAnimationWrapperConstructor(){
        int frameCols = 5;
        int frameRows = 5;
        float explosionRadius = 5;
        Entity testEntity = new TestEntity();
        String path = "test";
        Vector2D position = new Vector2D(5,5);
        AnimationWrapper animationWrapper = new AnimationWrapper(position,path,testEntity,frameCols,frameRows,explosionRadius);

        Assert.assertEquals(frameCols, animationWrapper.getFrameCols());
        Assert.assertEquals(frameRows, animationWrapper.getFrameRows());
        Assert.assertEquals(explosionRadius, animationWrapper.getExplosionRadius(),0.5f);
        Assert.assertEquals(path, animationWrapper.getPath());
        Assert.assertEquals(testEntity, animationWrapper.getOrigin());
        Assert.assertEquals(position,animationWrapper.getPosition());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAnimationWrapperConstructorNullInputEntity(){
        int frameCols = 5;
        int frameRows = 5;
        float explosionRadius = 5;
        Entity testEntity = null;
        String path = "test";
        Vector2D position = new Vector2D(5,5);
        AnimationWrapper animationWrapper = new AnimationWrapper(position,path,testEntity,frameCols,frameRows,explosionRadius);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAnimationWrapperConstructorNullInputPath(){
        int frameCols = 5;
        int frameRows = 5;
        float explosionRadius = 5;
        Entity testEntity = new TestEntity();
        String path = null;
        Vector2D position = new Vector2D(5,5);
        AnimationWrapper animationWrapper = new AnimationWrapper(position,path,testEntity,frameCols,frameRows,explosionRadius);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAnimationWrapperConstructorNullInputPosition(){
        int frameCols = 5;
        int frameRows = 5;
        float explosionRadius = 5;
        Entity testEntity = new TestEntity();
        String path = "test";
        Vector2D position = null;
        AnimationWrapper animationWrapper = new AnimationWrapper(position,path,testEntity,frameCols,frameRows,explosionRadius);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAnimationWrapperConstructorNegativeFrameCols(){
        int frameCols = -1;
        int frameRows = 5;
        float explosionRadius = 5;
        Entity testEntity = new TestEntity();
        String path = "test";
        Vector2D position = new Vector2D(5,5);
        AnimationWrapper animationWrapper = new AnimationWrapper(position,path,testEntity,frameCols,frameRows,explosionRadius);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAnimationWrapperConstructorNegativeFrameRows(){
        int frameCols = 5;
        int frameRows = -1;
        float explosionRadius = 5;
        Entity testEntity = new TestEntity();
        String path = "test";
        Vector2D position = new Vector2D(5,5);
        AnimationWrapper animationWrapper = new AnimationWrapper(position,path,testEntity,frameCols,frameRows,explosionRadius);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAnimationWrapperConstructorNegativeExplosionRadius(){
        int frameCols = 5;
        int frameRows = 5;
        float explosionRadius = -1;
        Entity testEntity = new TestEntity();
        String path = "test";
        Vector2D position = new Vector2D(5,5);
        AnimationWrapper animationWrapper = new AnimationWrapper(position,path,testEntity,frameCols,frameRows,explosionRadius);
    }
    @Test
    public void testAnimationWrapperUpdateStateTime() {
        int frameCols = 5;
        int frameRows = 5;
        float explosionRadius = 5;
        Entity testEntity = new TestEntity();
        String path = "test";
        Vector2D position = new Vector2D(5, 5);
        AnimationWrapper animationWrapper = new AnimationWrapper(position, path, testEntity, frameCols, frameRows, explosionRadius);

        float delta = 50f;
        float stateTime = animationWrapper.getStateTime();
        animationWrapper.updateStateTime(delta);
        Assert.assertEquals(stateTime+delta,animationWrapper.getStateTime(),0.2f);
    }
        private class TestEntity extends Entity {
    }
}
