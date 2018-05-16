package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;

public class ExplosionTexturePart implements IEntityPart {

    private int frameCols;
    private int frameRows;
    private String srcPath;

    public ExplosionTexturePart(){

    }
    /**
     *
     * @param frameCols
     * @param frameRows
     * @param srcPath
     */
    public ExplosionTexturePart(int frameCols, int frameRows, String srcPath) {
        this.frameCols = frameCols;
        this.frameRows = frameRows;
        this.srcPath = srcPath;
    }

    /**
     * Returns the amount of frame columns in the animation sprite sheet.
     * @return
     */
    public int getFrameCols() {
        return frameCols;
    }

    /**
     * Returns the amount of frame rows in the animation sprite sheet.
     * @return
     */
    public int getFrameRows() {
        return frameRows;
    }

    /**
     * returns the path to the resources containing the animation sprite sheet.
     * @return
     */
    public String getSrcPath() {
        return srcPath;
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {

    }


}
