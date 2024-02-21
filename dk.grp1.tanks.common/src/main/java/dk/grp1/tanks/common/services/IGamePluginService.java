package dk.grp1.tanks.common.services;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;


public interface IGamePluginService {
    /**
     * Start the given Plugin
     * @param world
     * @param gameData
     */
    void start(World world, GameData gameData);

    /**
     * Stop the given Plugin
     * @param world
     * @param gameData
     */
    void stop(World world,GameData gameData);
}
