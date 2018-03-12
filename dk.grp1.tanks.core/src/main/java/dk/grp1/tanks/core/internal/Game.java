package dk.grp1.tanks.core.internal;

import com.badlogic.gdx.ApplicationListener;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IEntityProcessingService;

/**
 * Created by danie on 05-03-2018.
 */
public class Game implements ApplicationListener {
    private ServiceLoader serviceLoader;
    private World world;
    private GameData gameData;

    public Game(ServiceLoader serviceLoader) {
        this.serviceLoader = serviceLoader;
        this.gameData = new GameData();
        this.world = new World();
    }


    public void create() {

    }

    public void resize(int i, int i1) {

    }

    public void render() {
        for (IEntityProcessingService processingService: serviceLoader.getEntityProcessingServices()) {
            processingService.process(world,gameData);
        }
    }


    public void pause() {

    }

    public void resume() {

    }

    public void dispose() {

    }
}
