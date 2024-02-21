package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;

public interface IEntityPart {
    /**
     * Processes the part.
     * @param entity
     * @param gameData
     * @param world
     */
    void processPart(Entity entity, GameData gameData, World world);
}
