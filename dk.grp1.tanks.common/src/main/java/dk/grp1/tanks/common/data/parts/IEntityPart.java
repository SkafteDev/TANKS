package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;

/**
 * Created by danie on 12-03-2018.
 */
public interface IEntityPart {

    void processPart(Entity entity, GameData gameData);
}
