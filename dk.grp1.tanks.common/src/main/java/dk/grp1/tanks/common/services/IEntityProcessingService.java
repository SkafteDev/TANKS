package dk.grp1.tanks.common.services;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;

public interface IEntityProcessingService {
    /**
     * Process Entity Service
     * @param world
     * @param gameData
     */
    void process(World world, GameData gameData);
}
