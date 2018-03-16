package dk.grp1.tanks.gamemap.internal;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameMap;
import dk.grp1.tanks.common.data.IGameMapFunction;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        //List<Vector2D> vertices = new ArrayList<>();
        //vertices.addAll(generateRandomMapFromFunction(gameData));
        GameMap map = new GameMap(gameData.getGameWidth(),gameData.getGameHeight());
        IGameMapFunction gameMapFunction = new GameMapSin(150f,(1/66f),0,gameData.getGameHeight()/2,0,gameData.getGameWidth());
        map.addGameMapFunction(gameMapFunction);
        //map.setVertices(vertices);
        return map;
    }

    private Collection<? extends Vector2D> generateRandomMap(GameData gameData) {
        List<Vector2D> vertices = new ArrayList<>();

        for (int x = 0; x <= gameData.getGameWidth(); x += gameData.getGameWidth() / 1) {
            vertices.add(new Vector2D(x, (float) (0.8f *(gameData.getGameHeight() / 2))));
//            vertices.add(new Vector2D(x, (float) (Math.random() * (gameData.getDisplayHeight() / 2))));

        }
        vertices.add(new Vector2D(gameData.getGameWidth(), 0));
        vertices.add(new Vector2D(0, 0));
        return vertices;
    }

    private Collection<? extends Vector2D> generateRandomMapFromFunction(GameData gameData) {
        List<Vector2D> vertices = new ArrayList<>();



        for (float x = 0; x <= gameData.getGameWidth(); x += gameData.getGameWidth() / 256) {
            vertices.add(new Vector2D(x,(gameData.getGameHeight()*0.5f)*(float)Math.sin(((double)x)/128)));
//            vertices.add(new Vector2D(x, (float) (Math.random() * (gameData.getDisplayHeight() / 2))));

        }
        vertices.add(new Vector2D(gameData.getGameWidth(), 0));
        vertices.add(new Vector2D(0, 0));
        return vertices;
    }

    @Override
    public void stop(World world, GameData gameData) {
        System.out.println("Map stopped");
        world.setGameMap(null);
    }
}
