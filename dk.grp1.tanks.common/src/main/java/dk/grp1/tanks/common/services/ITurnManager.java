package dk.grp1.tanks.common.services;

import dk.grp1.tanks.common.data.Entity;

public interface ITurnManager {

    void register(Entity entity);
    void unRegister(Entity entity);

}
