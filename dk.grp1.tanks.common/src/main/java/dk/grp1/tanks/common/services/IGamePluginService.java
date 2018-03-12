package dk.grp1.tanks.common.services;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;

/**
 * Created by danie on 12-03-2018.
 */
public interface IGamePluginService {
    void start(World world, GameData gameData);

    void stop(World world,GameData gameData);
}
