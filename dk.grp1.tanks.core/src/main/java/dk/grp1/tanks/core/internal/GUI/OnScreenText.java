package dk.grp1.tanks.core.internal.GUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CannonPart;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.InventoryPart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import javafx.geometry.Pos;

public class OnScreenText implements IGuiProcessingService{

    private SpriteBatch batch = new SpriteBatch();
    private BitmapFont font = new BitmapFont();
    private OrthographicCamera camera;

    /**
     * Draws various text values on the screen
     * @param world
     * @param gameData
     */
    @Override
    public void draw(World world, GameData gameData) {
        turnText(gameData);
        for (Entity entity: world.getEntities()) {
            InventoryPart inventoryPart = entity.getPart(InventoryPart.class);
            if (inventoryPart != null){
                weaponText(entity, inventoryPart);
                angleText(entity);
                firepowerText(entity);
            }
        }
    }

    private void firepowerText(Entity entity) {
        CannonPart cannonPart = entity.getPart(CannonPart.class);
        //batch = new SpriteBatch();
        float firepower = cannonPart.getPreviousFirepower();
        //font = new BitmapFont();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        String fire = ""+firepower;
        font.draw(batch, fire, cannonPart.getJointX(), cannonPart.getJointY()-75);
        batch.end();
        //font.dispose();
    }

    @Override
    public void setCam(OrthographicCamera camera) {
        this.camera = camera;
    }

    private void angleText(Entity entity){
        CannonPart cannonPart = entity.getPart(CannonPart.class);
        //batch = new SpriteBatch();
        double angle = cannonPart.getPreviousAngle() * 180 / 3.1415f;
        angle = Math.ceil(angle);
        //font = new BitmapFont();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        String angleString = ""+angle;
        font.draw(batch, angleString, cannonPart.getJointX(), cannonPart.getJointY()-25);
        batch.end();
        //font.dispose();
    }

    private void weaponText(Entity entity, InventoryPart inventoryPart) {
        //batch = new SpriteBatch();
        String weaponText;
        try {
            weaponText = inventoryPart.getCurrentWeapon().getName();
        } catch (NullPointerException e){
            weaponText = "None";
        }
        //font = new BitmapFont();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        CirclePart circlePart = entity.getPart(CirclePart.class);
        font.draw(batch, weaponText, circlePart.getCentreX()-30, circlePart.getCentreY()-10);
        batch.end();
        //font.dispose();
    }


    private void turnText(GameData gameData) {
        //batch = new SpriteBatch();
        String turnText = "Turn: ";
        //font = new BitmapFont();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, turnText, 10, gameData.getGameHeight()-10);
        batch.end();
        //font.dispose();
    }
}
