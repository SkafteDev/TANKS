package dk.grp1.tanks.core.internal.GUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.InventoryPart;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class WeaponGUI implements IGuiProcessingService {
    private Map<String, Texture> textureMap;
    private OrthographicCamera camera;



    @Override
    public void draw(World world, GameData gameData) {
        for (Entity entity: world.getEntities()) {
            //TODO check for inventory part and call drawWeaponIcon
        }
    }

    @Override
    public void setCam(OrthographicCamera camera) {
        this.camera = camera;
    }

    private void drawWeaponIcon(Entity entity, InventoryPart inventoryPart){
        String path = inventoryPart.getCurrentWeapon().getIconPath();

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
}
