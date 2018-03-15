package dk.grp1.tanks.gamemap.internal;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameMap;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class GameMapPlugin implements IGamePluginService {
    @Override
    public void start(World world, GameData gameData) {
        world.setGameMap(createNewGameMap(gameData));
    }


    /**
     * Creates a simple square map
     * @param gameData
     * @return
     */
    private GameMap createNewGameMap(GameData gameData) {
        List<Vector2D> vertices = new ArrayList<>();
        vertices.add(new Vector2D(0,0));
        vertices.add(new Vector2D(0,gameData.getDisplayHeight()/2));
        vertices.add(new Vector2D(gameData.getDisplayWidth(),gameData.getDisplayHeight()/2));
        vertices.add(new Vector2D(gameData.getDisplayWidth(),0));
        GameMap map = new GameMap();
        map.setVertices(vertices);
        return map;
    }

    @Override
    public void stop(World world, GameData gameData) {
        world.setGameMap(null);
    }
}
