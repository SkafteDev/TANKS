package dk.grp1.tanks.common.services;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;


public interface INonEntityProcessingService {
    /**
     * Process non-entity Service
     * @param world
     * @param gameData
     */
    void process(World world, GameData gameData);
}
