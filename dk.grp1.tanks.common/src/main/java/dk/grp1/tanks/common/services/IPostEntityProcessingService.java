package dk.grp1.tanks.common.services;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;

public interface IPostEntityProcessingService {
    void postProcess(World world, GameData gameData);
}
