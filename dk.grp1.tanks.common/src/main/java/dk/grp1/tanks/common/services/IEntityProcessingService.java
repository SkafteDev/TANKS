package dk.grp1.tanks.common.services;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;

/**
 * Created by danie on 12-03-2018.
 */
public interface IEntityProcessingService {
    void process(World world, GameData gameData);
}
