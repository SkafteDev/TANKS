package dk.grp1.tanks.common.services;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;

public interface ITurnManager {

    void register(Entity entity);
    void unRegister(Entity entity);
    void setGameData(GameData gameData);
    float getTimeRemaining();

}
