package dk.grp1.tanks.core.internal.GUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.UI.IGUIWeapon;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.InventoryPart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.services.IWeapon;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

public class WeaponGUI implements IGuiProcessingService {
    private BitmapFont font = new BitmapFont();
    private Map<String, Texture> textureMap = new TreeMap<>();
    private OrthographicCamera camera;
    private BitmapFont.TextBounds textBounds;
    private float textYvalue = 10;
    private float weaponIconWidth = 15;
    private float weaponIconHeight = 15;


    @Override
    public void draw(World world, GameData gameData, SpriteBatch batch) {
        for (Entity entity : world.getEntities()) {

            if (entity instanceof IGUIWeapon) {
                InventoryPart inventoryPart = entity.getPart(InventoryPart.class);
                if (inventoryPart != null){
                    drawWeaponIcon(entity, inventoryPart, batch);
                }
                weaponText(entity, inventoryPart, batch);
            }
        }
    }

    private void weaponText(Entity entity, InventoryPart inventoryPart, SpriteBatch batch) {
        String weaponText;

        IWeapon weapon = inventoryPart.getCurrentWeapon();
        if (weapon != null) {
            weaponText = weapon.getName() + ": ";
        } else {
            weaponText = "None";
        }
        font.getData().scaleX = 0.5f;
        font.getData().scaleY = 0.5f;
        batch.begin();
        CirclePart circlePart = entity.getPart(CirclePart.class);
        textBounds = font.getBounds(weaponText);
        font.draw(batch, weaponText, (circlePart.getCentreX() - textBounds.width / 2 - weaponIconWidth), textYvalue+weaponIconHeight/2);
        batch.end();
    }


    private void drawWeaponIcon(Entity entity, InventoryPart inventoryPart, SpriteBatch spriteBatch){

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
            spriteBatch.draw(t, positionPart.getX() + weaponIconWidth/2, textYvalue-weaponIconHeight/8,
                    weaponIconWidth, weaponIconHeight);
            spriteBatch.end();
            //t.dispose();

    }

}
