package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;

/**
 * Created by danie on 12-03-2018.
 */
public class TexturePart implements IEntityPart {

    private String srcPath;

    public TexturePart(String srcPath) {
        this.srcPath = srcPath;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public void processPart(Entity entity, GameData gameData) {

    }
}
