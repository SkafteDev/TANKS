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
import dk.grp1.tanks.common.data.parts.PositionPart;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class WeaponGUI implements IGuiProcessingService {
    private Map<String, Texture> textureMap = new TreeMap<>();
    private OrthographicCamera camera;


    @Override
    public void draw(World world, GameData gameData) {
        for (Entity entity : world.getEntities()) {
            //TODO check for inventory part and call drawWeaponIcon
            InventoryPart inventoryPart = entity.getPart(InventoryPart.class);
            if (inventoryPart != null){
                drawWeaponIcon(entity, inventoryPart);
            }
        }
    }

    @Override
    public void setCam(OrthographicCamera camera) {
        this.camera = camera;
    }


    private void drawWeaponIcon(Entity entity, InventoryPart inventoryPart){

            String path = inventoryPart.getCurrentWeapon().getIconPath();
            if (!textureMap.containsKey(path)) {

                InputStream input = inventoryPart.getCurrentWeapon().getClass().getClassLoader().getResourceAsStream(path);
                try {
                    Gdx2DPixmap gmp = new Gdx2DPixmap(input, Gdx2DPixmap.GDX2D_FORMAT_RGBA8888);
                    Pixmap pixmap = new Pixmap(gmp);
                    textureMap.put(path, new Texture(pixmap));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            SpriteBatch spriteBatch = new SpriteBatch();
            spriteBatch.begin();
            spriteBatch.setProjectionMatrix(camera.combined);
            Texture t = textureMap.get(path);

            PositionPart positionPart = entity.getPart(PositionPart.class);
            spriteBatch.draw(t, positionPart.getX(), 10, 15, 15);
            spriteBatch.end();
            //t.dispose();

    }

}
