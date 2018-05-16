package dk.grp1.tanks.core.internal.GUI;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.LifePart;

public class HealthBarGUI implements IGuiProcessingService {
    private Texture healthBarTexture;
    private Pixmap pixCurrentHealth;
    private Pixmap pixLostHealth;
    private float barHeight = 2;
    private float barWidth = 4;
    private float xOffSet = 2;
    private float yOffSet = 2f;

    /**
     * Draws a Health bar for every entity that has a LifePart
     * @param world current game world and entities
     * @param gameData
     * @param batch
     */
    @Override
    public void draw(World world, GameData gameData, SpriteBatch batch) {
        for (Entity entity : world.getEntities()) {
            LifePart lifePart = entity.getPart(LifePart.class);
            if (lifePart != null) {
                CirclePart circlePart = entity.getPart(CirclePart.class);
                makeHealthBar(circlePart.getCentreX(), circlePart.getCentreY(),
                        circlePart.getRadius(), lifePart.getHealthRatio(), batch);
            }
        }

    }

    @Override
    public void dispose() {

    }


    /**
     * Draw and colour a health bar based on entity position and remaining health
     *
     * @param x           centre
     * @param y           centre
     * @param radius      of entity
     * @param healthValue current/max ratio
     */
    private void makeHealthBar(float x, float y, float radius, float healthValue, SpriteBatch batch) {
        pixCurrentHealth = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

        //Colour health bar based on health value
        pixLostHealth = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixLostHealth.setColor(Color.RED);
        pixCurrentHealth.setColor(Color.GREEN);

        pixCurrentHealth.fill();
        pixLostHealth.fill();

        healthBarTexture = new Texture(pixCurrentHealth);
        Texture healthBarLostTexture = new Texture(pixLostHealth);

        batch.begin();

        batch.draw(healthBarTexture, x - xOffSet * radius, y + yOffSet * radius,
                radius * barWidth * healthValue, barHeight);
        batch.draw(healthBarLostTexture, (x - xOffSet * radius)+ (radius * barWidth * healthValue), y + yOffSet * radius,
                radius * barWidth * (1-healthValue), barHeight);

        batch.end();
        pixLostHealth.dispose();
        pixCurrentHealth.dispose();
        healthBarTexture.dispose();
        healthBarLostTexture.dispose();
    }

}
