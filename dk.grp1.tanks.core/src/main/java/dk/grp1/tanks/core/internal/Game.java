package dk.grp1.tanks.core.internal;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;
import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.services.IEntityProcessingService;
import dk.grp1.tanks.common.services.IGamePluginService;

/**
 * Created by danie on 05-03-2018.
 */
public class Game implements ApplicationListener {
    private ServiceLoader serviceLoader;
    private World world;
    private GameData gameData;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;

    public Game(ServiceLoader serviceLoader) {
        this.serviceLoader = serviceLoader;
        this.gameData = new GameData();
        this.world = new World();
    }


    public void create() {
        gameData.setDisplayWidth(800);
        gameData.setDisplayHeight(600);

        camera = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        camera.translate(gameData.getDisplayWidth()/2, gameData.getDisplayHeight()/2);
        camera.update();

        shapeRenderer = new ShapeRenderer();

/*        for (IGamePluginService pluginService: serviceLoader.getGamePluginServices()){
            System.out.println("SERVICE: " + pluginService);
            pluginService.start(world,gameData);
        }*/

    }

    public void resize(int i, int i1) {

    }

    public void render() {
        Gdx.gl.glClearColor(0, 0,0 ,1 );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (IEntityProcessingService processingService: serviceLoader.getEntityProcessingServices()) {
            processingService.process(world,gameData);
        }



        draw();
    }

    private void draw() {
        for (Entity entity: world.getEntities()) {
            shapeRenderer.setColor(1,1,1,1);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            CirclePart cp = entity.getPart(CirclePart.class);
            if (cp != null) {
                shapeRenderer.circle(cp.getCentreX(), cp.getCentreY(), cp.getRadius());
            }

            shapeRenderer.end();
        }
    }


    public void pause() {

    }

    public void resume() {

    }

    public void dispose() {

    }

    public World getWorld() {
        return world;
    }

    public GameData getGameData() {
        return gameData;
    }
}
