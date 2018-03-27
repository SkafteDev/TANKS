package dk.grp1.tanks.gamemap.internal;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameMap;
import dk.grp1.tanks.common.data.IGameMapFunction;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IGamePluginService;

public class GameMapPlugin implements IGamePluginService {
    @Override
    public void start(World world, GameData gameData) {
        world.setGameMap(createNewGameMap(gameData));
        System.out.println("Created a new game map");
    }


    /**
     * Creates a simple square map
     *
     * @param gameData
     * @return
     */
    private GameMap createNewGameMap(GameData gameData) {
        GameMap map = new GameMap(gameData.getGameWidth(), gameData.getGameHeight());
        //Generate map functions
        //TODO: Generate random map, based on given list of random functions.
       IGameMapFunction gameMapFunction = new GameMapSin(100f, (1 / 66f), 0, gameData.getGameHeight() / 2, 0, gameData.getGameWidth() / 2 + 30);
        IGameMapFunction gameMapFunction2 = new GameMapLinear(0f,gameData.getGameWidth()/2+30,gameData.getGameWidth()/2+130,gameMapFunction);
        IGameMapFunction gameMapFunction3 = new GameMapLinear(2f,gameData.getGameWidth()/2+130,gameData.getGameWidth(),gameMapFunction2);

        map.addGameMapFunction(gameMapFunction);
        map.addGameMapFunction(gameMapFunction3);
        map.addGameMapFunction(gameMapFunction2);

        //IGameMapFunction function = new GameMapLinear(0,100,0,gameData.getGameWidth());
        //map.addGameMapFunction(function);
        return map;
    }


    @Override
    public void stop(World world, GameData gameData) {
        System.out.println("Map stopped");
        world.setGameMap(null);
    }
}
