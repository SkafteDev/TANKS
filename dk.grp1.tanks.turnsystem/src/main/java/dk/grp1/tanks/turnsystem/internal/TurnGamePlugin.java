package dk.grp1.tanks.turnsystem.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.TurnPart;
import dk.grp1.tanks.common.services.IGamePluginService;

public class TurnGamePlugin implements IGamePluginService {
    @Override
    public void start(World world, GameData gameData) {


    }

    @Override
    public void stop(World world, GameData gameData) {
        System.out.println("TURNS STOPPED!");
        TurnPart.resetPart();
    }
}
