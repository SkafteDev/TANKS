package dk.grp1.tanks.core.internal;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.ShortArray;
import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameMap;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CannonPart;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.services.IEntityProcessingService;
import dk.grp1.tanks.common.services.IPostEntityProcessingService;
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


    //Variables for drawing the game map
    private Texture gameMapTexture;
    private PolygonSprite gameMapPolySprite;
    private PolygonSpriteBatch polySpriteBatch;
    private TextureRegion textureRegion;

    public Game(ServiceLoader serviceLoader, GameData gameData) {
        this.serviceLoader = serviceLoader;
        this.world = new World();
        this.gameData = gameData;

    }


    public void create() {
        setupGame();
    }

    private void setupGame() {
        setupMapDrawingConfig();

        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );


        //camera = new OrthographicCamera(gameData.getDisplayWidth(),gameData.getDisplayHeight());
        //camera = new OrthographicCamera(200,100);
        camera = new OrthographicCamera(gameData.getGameWidth(),gameData.getGameHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        this.shapeRenderer = new ShapeRenderer();
    }

    private void setupMapDrawingConfig() {
        polySpriteBatch = new PolygonSpriteBatch();

        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(Color.RED);
        pix.fill();

        gameMapTexture = new Texture(pix);

        textureRegion = new TextureRegion(gameMapTexture);
    }

    public void resize(int i, int i1) {

    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());


        update();


        draw();
    }

    private void update() {
        for (IEntityProcessingService processingService : serviceLoader.getEntityProcessingServices()) {
            processingService.process(world, gameData);
        }
        for(IPostEntityProcessingService postEntityProcessingService: serviceLoader.getPostEntityProcessingServices()){

            postEntityProcessingService.postProcess(world,gameData);
        }
    }

    private void renderGameMap() {
        shapeRenderer.setColor(Color.RED);

        GameMap gameMap = world.getGameMap();
        if (gameMap == null) {

            return;
        }


        gameMapPolySprite = new PolygonSprite(convertGameMapToPolyRegion(gameMap));
        polySpriteBatch.setProjectionMatrix(camera.combined);
        polySpriteBatch.begin();
        polySpriteBatch.setProjectionMatrix(camera.combined);
        gameMapPolySprite.draw(polySpriteBatch);
        polySpriteBatch.end();

    }

    private PolygonRegion convertGameMapToPolyRegion(GameMap gameMap) {
        FloatArray vertices = new FloatArray(gameMap.getVerticesAsFloats());
        EarClippingTriangulator triangulator = new EarClippingTriangulator();
        ShortArray triangleIndices = triangulator.computeTriangles(vertices);
        PolygonRegion polygonRegion = new PolygonRegion(textureRegion, vertices.toArray(), triangleIndices.toArray());
        return polygonRegion;
    }


    private void draw() {
        renderGameMap();
        for (Entity entity: world.getEntities()) {
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.setColor(1,1,1,1);

            CirclePart cp = entity.getPart(CirclePart.class);
            PositionPart pos = entity.getPart(PositionPart.class);
            if (cp != null) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.circle(pos.getX(), pos.getY(), cp.getRadius());
                shapeRenderer.end();
            }

            //Draws the cannon if it exists
            CannonPart cannonPart = entity.getPart(CannonPart.class);

            if (cannonPart != null) {

                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.polygon(Vector2D.getVerticesAsFloatArray(cannonPart.getVertices()));
                shapeRenderer.end();
            }

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
