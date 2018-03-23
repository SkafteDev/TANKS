package dk.grp1.tanks.common.services;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;

/**
 * Created by danie on 19-03-2018.
 */
public interface INonEntityProcessingService {
    void process(World world, GameData gameData);
}
