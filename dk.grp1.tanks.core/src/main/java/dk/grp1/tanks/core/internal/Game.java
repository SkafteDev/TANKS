package dk.grp1.tanks.core.internal;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameMap;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.services.IEntityProcessingService;
import dk.grp1.tanks.common.utils.Vector2D;
import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.core.internal.managers.GameInputProcessor;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;


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
       setupGame();
    }

    private void setupGame(){

        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );

        gameData.setDisplayHeight(Gdx.graphics.getHeight());
        gameData.setDisplayWidth(Gdx.graphics.getWidth());

        camera = new OrthographicCamera(gameData.getDisplayWidth(),gameData.getDisplayHeight());
        camera.translate(gameData.getDisplayWidth()/2,gameData.getDisplayHeight()/2);
        camera.update();

        this.shapeRenderer = new ShapeRenderer();
    }

    public void resize(int i, int i1) {

    }

    public void render() {
        Gdx.gl.glClearColor(0, 0,0 ,1 );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        renderGameMap();

        update();



        draw();
    }

    private void update() {
        for (IEntityProcessingService processingService: serviceLoader.getEntityProcessingServices()) {
            processingService.process(world,gameData);
        }
    }

    private void renderGameMap(){
        shapeRenderer.setColor(Color.RED);

        GameMap gameMap = world.getGameMap();
        if(gameMap == null){
            return;
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0, j = gameMap.getVertices().size()-1;
             i < gameMap.getVertices().size(); j = i++) {
                Vector2D vector1 = gameMap.getVertices().get(i);
                Vector2D vector2 = gameMap.getVertices().get(j);





                    shapeRenderer.line(vector1.getX(),vector1.getY(),vector2.getX(),vector2.getY());
                }
                shapeRenderer.end();
            }


    private void draw() {
        for (Entity entity: world.getEntities()) {
            shapeRenderer.setColor(1,1,1,1);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            CirclePart cp = entity.getPart(CirclePart.class);
            PositionPart pos = entity.getPart(PositionPart.class);
            if (cp != null) {
              //  shapeRenderer.circle(cp.getCentreX(), cp.getCentreY(), cp.getRadius());
                shapeRenderer.circle(pos.getX(), pos.getY(), cp.getRadius());
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
