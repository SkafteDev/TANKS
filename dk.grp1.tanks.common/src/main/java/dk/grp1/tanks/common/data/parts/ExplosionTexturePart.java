package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;

public class ExplosionTexturePart implements IEntityPart {

    private int frameCols;
    private int frameRows;
    private String srcPath;

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
     *
     * @return
     */
    public int getFrameCols() {
        return frameCols;
    }

    /**
     *
     * @return
     */
    public int getFrameRows() {
        return frameRows;
    }

    /**
     *
     * @return
     */
    public String getSrcPath() {
        return srcPath;
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {

    }


}
