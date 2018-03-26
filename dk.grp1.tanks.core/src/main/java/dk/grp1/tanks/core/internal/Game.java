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
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.services.IEntityProcessingService;
import dk.grp1.tanks.common.services.IPostEntityProcessingService;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.common.utils.Vector2D;
import dk.grp1.tanks.core.internal.GUI.HealthBarGUI;
import dk.grp1.tanks.core.internal.GUI.IGuiProcessingService;
import dk.grp1.tanks.core.internal.GUI.OnScreenText;
import dk.grp1.tanks.core.internal.GUI.WeaponGUI;
import dk.grp1.tanks.core.internal.managers.GameInputProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class Game implements ApplicationListener {
    private ServiceLoader serviceLoader;
    private World world;
    private GameData gameData;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private Map<String, Texture> textureMap;
    private List<IGuiProcessingService> drawImplementations;


    //Variables for drawing the game map
    private Texture gameMapTexture;
    private PolygonSprite gameMapPolySprite;
    private PolygonSpriteBatch polySpriteBatch;
    private TextureRegion textureRegion;

    public Game(ServiceLoader serviceLoader, GameData gameData) {
        this.serviceLoader = serviceLoader;
        this.world = new World();
        this.gameData = gameData;
        this.textureMap = new HashMap<>();
        this.drawImplementations = new ArrayList<>();
    }


    public void create() {
        setupGame();
    }

    private void setupGame() {
        setupMapDrawingConfig();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        camera = new OrthographicCamera(gameData.getGameWidth(), gameData.getGameHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        this.shapeRenderer = new ShapeRenderer();

        drawImplementations.add(new HealthBarGUI());
        drawImplementations.add(new OnScreenText());
        drawImplementations.add(new WeaponGUI());

        for (IGuiProcessingService gui: drawImplementations) {
            gui.setCam(camera);
        }

    }

    private void setupMapDrawingConfig() {
        polySpriteBatch = new PolygonSpriteBatch();

        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(Color.BLUE);
        pix.fill();

        gameMapTexture = new Texture(pix);

        textureRegion = new TextureRegion(gameMapTexture);
        pix.dispose(); //TODO Might need to dispose?
    }

    public void resize(int i, int i1) {

    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());


        update();
        drawBackGround();
        draw();
    }



    private void drawBackGround() {
        String path = "background.png";

        if (!textureMap.containsKey(path)) {

            InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
            try {
                Gdx2DPixmap gmp = new Gdx2DPixmap(is, Gdx2DPixmap.GDX2D_FORMAT_RGBA8888);
                Pixmap pix = new Pixmap(gmp);
                textureMap.put(path, new Texture(pix));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        SpriteBatch spriteBatch = new SpriteBatch();
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(camera.combined);
        Texture t = textureMap.get(path);

        spriteBatch.draw(t, 0, 0, gameData.getGameWidth(), gameData.getGameHeight());
        spriteBatch.end();
    }

    private void update() {
        for (IEntityProcessingService processingService : serviceLoader.getEntityProcessingServices()) {
            processingService.process(world, gameData);
        }
        for (IPostEntityProcessingService postEntityProcessingService : serviceLoader.getPostEntityProcessingServices()) {

            postEntityProcessingService.postProcess(world, gameData);
        }

        gameData.getKeys().update();
    }

    private void renderGameMap() {
        shapeRenderer.setColor(Color.RED);

        GameMap gameMap = world.getGameMap();
        if (gameMap == null) {

            return;
        }


        gameMapPolySprite = new PolygonSprite(convertGameMapToPolyRegion(gameMap));
        polySpriteBatch.begin();
        polySpriteBatch.setProjectionMatrix(camera.combined);
        gameMapPolySprite.draw(polySpriteBatch);
        polySpriteBatch.end();

    }

    private PolygonRegion convertGameMapToPolyRegion(GameMap gameMap) {
        FloatArray vertices = new FloatArray(gameMap.getVerticesAsFloats(0, gameData.getGameWidth(), 256));
        EarClippingTriangulator triangulator = new EarClippingTriangulator();
        ShortArray triangleIndices = triangulator.computeTriangles(vertices);
        PolygonRegion polygonRegion = new PolygonRegion(textureRegion, vertices.toArray(), triangleIndices.toArray());
        return polygonRegion;
    }


    private void draw() {
        renderGameMap();
        //drawShapes();
        drawTextures();
        drawUI();
    }

    private void drawUI(){
        for (IGuiProcessingService gui: drawImplementations){
            gui.draw(camera, world, gameData);
        }
    }

    private void drawShapes(){
        for (Entity entity : world.getEntities()) {
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.setColor(1, 1, 1, 1);

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

    private void drawTextures() {
        SpriteBatch spriteBatch = new SpriteBatch();
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(camera.combined);


        for (Entity e : world.getEntities()) {
            TexturePart tp = e.getPart(TexturePart.class);
            PositionPart pp = e.getPart(PositionPart.class);
            CirclePart cp = e.getPart(CirclePart.class);

            CannonPart cannonPart = e.getPart(CannonPart.class);
            if (cannonPart != null && pp != null && cp != null) {
                TextureRegion textureRegion = new TextureRegion(checkGetTexture(e, cannonPart.getTexturePath()));
                drawCannon(spriteBatch, textureRegion, cannonPart);
            }

            if (tp != null && pp != null && cp != null) {
                Texture texture = checkGetTexture(e, tp.getSrcPath());
                //Sprite sprite = new Sprite(texture);
                spriteBatch.draw(texture, pp.getX() - cp.getRadius(), pp.getY() - cp.getRadius(), cp.getRadius() * 2, cp.getRadius() * 2);
            }


        }
        spriteBatch.end();
    }

    private void drawCannon(SpriteBatch spriteBatch, TextureRegion textureRegion, CannonPart cannonPart) {
        float x = cannonPart.getVertices()[1].getX();
        float y = cannonPart.getVertices()[1].getY();
        float originX = 0; //(cannonPart.getVertices()[0].getX()-cannonPart.getVertices()[1].getX())/2;
        float originY = 0; //(cannonPart.getVertices()[0].getY()-cannonPart.getVertices()[1].getY())/2;
        float width = cannonPart.getWidth();
        float height = cannonPart.getLength();
        float scaleX = 1; // scale is 100%. 50% is equal to 0.5
        float scaleY = 1; // scale is 100%
        float rotation = (float) Math.toDegrees(cannonPart.getDirection()) - 90;
        spriteBatch.draw(textureRegion, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
    }

    private Texture checkGetTexture(Entity e, String path) {
        Texture texture = textureMap.get(path);

        if (texture == null) {
            InputStream is = e.getClass().getClassLoader().getResourceAsStream(
                    path
            );
            try {
                Gdx2DPixmap gmp = new Gdx2DPixmap(is, Gdx2DPixmap.GDX2D_FORMAT_RGBA8888);
                Pixmap pix = new Pixmap(gmp);
                texture = new Texture(pix);
                textureMap.put(path, texture);
                pix.dispose();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        return texture;
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
