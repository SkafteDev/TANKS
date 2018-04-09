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
import dk.grp1.tanks.common.services.IWeapon;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

public class WeaponGUI implements IGuiProcessingService {
    private Map<String, Texture> textureMap = new TreeMap<>();
    private OrthographicCamera camera;


    @Override
    public void draw(World world, GameData gameData, SpriteBatch batch) {
        for (Entity entity : world.getEntities()) {
            InventoryPart inventoryPart = entity.getPart(InventoryPart.class);
            if (inventoryPart != null) {
                drawWeaponIcon(entity, inventoryPart, batch);
            }
        }
    }

    //   @Override
    //   public void setCam(OrthographicCamera camera) {
    //       this.camera = camera;
    //   }


    private void drawWeaponIcon(Entity entity, InventoryPart inventoryPart, SpriteBatch spriteBatch) {

        IWeapon weapon = inventoryPart.getCurrentWeapon();
        if (weapon == null) {
            return;
        }


        String path = weapon.getIconPath();
        if (!textureMap.containsKey(path)) {
            InputStream input = inventoryPart.getCurrentWeapon().getClass().getClassLoader().getResourceAsStream(path);
            try {
                Gdx2DPixmap gmp = new Gdx2DPixmap(input, Gdx2DPixmap.GDX2D_FORMAT_RGBA8888);
                Pixmap pixmap = new Pixmap(gmp);
                textureMap.put(path, new Texture(pixmap));
                pixmap.dispose();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        spriteBatch.begin();
        Texture t = textureMap.get(path);

        PositionPart positionPart = entity.getPart(PositionPart.class);
        spriteBatch.draw(t, positionPart.getX() - 7.5f, 10, 15, 15);
        spriteBatch.end();
        //t.dispose();

    }

}
