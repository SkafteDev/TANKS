package dk.grp1.tanks.common.services;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;

public interface IPostEntityProcessingService {
    /**
     * Post Process Entity Service. Used after all Entities have been processed
     * @param world
     * @param gameData
     */
    void postProcess(World world, GameData gameData);
}
