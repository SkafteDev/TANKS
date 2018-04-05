package dk.grp1.tanks.gamemap.internal;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameMap;
import dk.grp1.tanks.common.data.IGameMapFunction;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IGamePluginService;

import java.util.Random;

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
        IGameMapFunction gameMapFunction = new GameMapSin(100f, (1 / 66f), 0, gameData.getGameHeight() / 3f, 0, gameData.getGameWidth() / 2 + 30);
        IGameMapFunction gameMapFunction2 = new GameMapLinear(0f,gameData.getGameWidth()/2+30,gameData.getGameWidth()/2+130,gameMapFunction);
        IGameMapFunction gameMapFunction3 = new GameMapLinear(1f,gameData.getGameWidth()/2+130,gameData.getGameWidth()/2+250,gameMapFunction2);
        IGameMapFunction gameMapFunction4 = new GameMapLinear(0f,gameMapFunction3.getEndX(),gameData.getGameWidth(),gameMapFunction3);
        //generateRandomMap(map, gameData);
        map.addGameMapFunction(gameMapFunction);
        map.addGameMapFunction(gameMapFunction3);
        map.addGameMapFunction(gameMapFunction2);
        map.addGameMapFunction(gameMapFunction4);

        //IGameMapFunction function = new GameMapLinear(0,100,0,gameData.getGameWidth());
        //map.addGameMapFunction(function);
        return map;
    }

    private void generateRandomMap(GameMap map, GameData gameData) {
        int amountOfFunctions = (int) (Math.random()*10);
        Random random = new Random();

        IGameMapFunction predecessor = new GameMapLinear((random.nextFloat()),150,0,gameData.getGameWidth()/amountOfFunctions);
        map.addGameMapFunction(predecessor);
        for (int i = 0; i <= amountOfFunctions; i++) {
            IGameMapFunction mapFunction = new GameMapLinear((random.nextFloat()),predecessor.getEndX(),(predecessor.getEndX() + gameData.getGameWidth()/amountOfFunctions),predecessor);
            map.addGameMapFunction(mapFunction);
            predecessor = mapFunction;
        }
    }


    @Override
    public void stop(World world, GameData gameData) {
        System.out.println("Map stopped");
        world.setGameMap(null);
    }
}
