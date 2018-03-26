package dk.grp1.tanks.core.internal.GUI;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.LifePart;
import dk.grp1.tanks.common.services.IPostEntityProcessingService;

public class HealthBarGUI implements IGuiProcessingService {
    private Texture healthBarTexture;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    @Override
    public void draw(OrthographicCamera camera, World world, GameData gameData) {
        this.camera = camera;
        for (Entity entity : world.getEntities()) {
            LifePart lifePart = entity.getPart(LifePart.class);
            if (lifePart != null) {
                CirclePart circlePart = entity.getPart(CirclePart.class);
                makeHealthBar(circlePart.getCentreX(), circlePart.getCentreY(),
                        circlePart.getRadius(), lifePart.getHealthRatio());
            }
        }

    }


    /**
     * Draw and colour a health bar based on entity position and remaining health
     *
     * @param x           centre
     * @param y           centre
     * @param radius      of entity
     * @param healthValue current/max ratio
     */
    private void makeHealthBar(float x, float y, float radius, float healthValue) {

        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        //Colour health bar based on health value
        if (healthValue > 0.75) {
            pix.setColor(Color.GREEN);
        } else if (healthValue > 0.5) {
            pix.setColor(Color.YELLOW);
        } else if (healthValue > 0.25) {
            pix.setColor(Color.ORANGE);
        } else {
            pix.setColor(Color.RED);
        }
        pix.fill();
        batch = new SpriteBatch();
        healthBarTexture = new Texture(pix);
        pix.dispose();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(healthBarTexture, x - 2 * radius, y + 2 * radius, radius * 4 * healthValue, 2);
        batch.end();
    }

}
