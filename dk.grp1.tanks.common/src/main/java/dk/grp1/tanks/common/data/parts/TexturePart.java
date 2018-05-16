package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;


public class TexturePart implements IEntityPart {

    private String srcPath;

    public TexturePart(){

    }
    /**
     * Creates a new texture part with a given source path
     * @param srcPath String
     */
    public TexturePart(String srcPath) {
        this.srcPath = srcPath;
    }

    /**
     * gets path to texture source
     * @return String
     */
    public String getSrcPath() {
        return srcPath;
    }

    /**
     * Sets the path to the texture source
     * @param srcPath String
     */
    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public void processPart(Entity entity, GameData gameData,World world) {

    }
}
