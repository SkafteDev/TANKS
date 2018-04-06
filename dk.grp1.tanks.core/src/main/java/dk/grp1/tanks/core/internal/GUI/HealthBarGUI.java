package dk.grp1.tanks.core.internal.GUI;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.UI.IGUIHealthBar;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.LifePart;
import dk.grp1.tanks.common.services.IPostEntityProcessingService;

public class HealthBarGUI implements IGuiProcessingService {
    private Texture healthBarTexture;
    private Pixmap pix;
    private float barHeight = 2;
    private float barWidth = 4;
    private float xOffSet = 2;
    private float yOffSet = 2;

    @Override
    public void draw(World world, GameData gameData, SpriteBatch batch) {
        for (Entity entity : world.getEntities()) {

            if (entity instanceof IGUIHealthBar) {
                LifePart lifePart = entity.getPart(LifePart.class);
                if (lifePart != null) {
                    CirclePart circlePart = entity.getPart(CirclePart.class);
                    makeHealthBar(circlePart.getCentreX(), circlePart.getCentreY(),
                            circlePart.getRadius(), lifePart.getHealthRatio(), batch);
                }
            }
        }

    }

    //   @Override
    //  public void setCam(OrthographicCamera camera) {
    //      this.camera = camera;
    //  }


    /**
     * Draw and colour a health bar based on entity position and remaining health
     *
     * @param x           centre
     * @param y           centre
     * @param radius      of entity
     * @param healthValue current/max ratio
     */
    private void makeHealthBar(float x, float y, float radius, float healthValue, SpriteBatch batch) {
        pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

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
        healthBarTexture = new Texture(pix);
        batch.begin();
        batch.draw(healthBarTexture, x - xOffSet * radius, y + yOffSet * radius,
                radius * barWidth * healthValue, barHeight);
        batch.end();
        pix.dispose();
        healthBarTexture.dispose();
    }

}
