package dk.grp1.tanks.gamemap.internal;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameMap;
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
        List<Vector2D> vertices = new ArrayList<>();
        vertices.addAll(generateRandomMap(gameData));
        GameMap map = new GameMap();
        map.setVertices(vertices);
        return map;
    }

    private Collection<? extends Vector2D> generateRandomMap(GameData gameData) {
        List<Vector2D> vertices = new ArrayList<>();

        for (int x = 0; x <= gameData.getDisplayWidth(); x += gameData.getDisplayWidth() / 1) {
            vertices.add(new Vector2D(x, (float) (0.95 * (gameData.getDisplayHeight() / 2))));
//            vertices.add(new Vector2D(x, (float) (Math.random() * (gameData.getDisplayHeight() / 2))));

        }
        vertices.add(new Vector2D(gameData.getDisplayWidth(), 0));
        vertices.add(new Vector2D(0, 0));
        return vertices;
    }

    @Override
    public void stop(World world, GameData gameData) {
        System.out.println("Map stopped");
        world.setGameMap(null);
    }
}
