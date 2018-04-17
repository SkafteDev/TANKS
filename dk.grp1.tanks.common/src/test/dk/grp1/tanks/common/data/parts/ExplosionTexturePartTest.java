package dk.grp1.tanks.common.data.parts;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExplosionTexturePartTest {


    private int frameCols = 4;
    private int frameRows = 4;
    private String srcPath = "path";
    private ExplosionTexturePart ep;


    @Test
    public void getFrameCols() {
        ep = new ExplosionTexturePart(frameCols, frameRows, srcPath);
        assertEquals(frameCols, ep.getFrameCols());
    }

    @Test
    public void getFrameRows() {
        ep = new ExplosionTexturePart(frameCols, frameRows, srcPath);
        assertEquals(frameRows, ep.getFrameRows());
    }

    @Test
    public void getSrcPath() {
        ep = new ExplosionTexturePart(frameCols, frameRows, srcPath);
        assertEquals(srcPath, ep.getSrcPath());
    }

    @Test
    public void processPart() {
        // not implemented
    }
}