package dk.grp1.tanks.core.internal.GUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.UI.IGUIAngleText;
import dk.grp1.tanks.common.data.UI.IGUIFirepowerText;
import dk.grp1.tanks.common.data.UI.IGUIWeaponText;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.services.IWeapon;
import javafx.geometry.Pos;

public class OnScreenText implements IGuiProcessingService {
    private BitmapFont font = new BitmapFont();
    private OrthographicCamera camera;
    private BitmapFont.TextBounds textBounds;

    /**
     * Draws various text values on the screen
     *
     * @param world
     * @param gameData
     */
    @Override
    public void draw(World world, GameData gameData, SpriteBatch batch) {
        turnText(world, gameData, batch);
        for (Entity entity : world.getEntities()) {
            InventoryPart inventoryPart = entity.getPart(InventoryPart.class);
            if (inventoryPart != null) {

                if (entity instanceof IGUIWeaponText) {
                    weaponText(entity, inventoryPart, batch);
                }
                if (entity instanceof IGUIAngleText) {
                    angleText(entity, batch);
                }
                if (entity instanceof IGUIFirepowerText) {
                    firepowerText(entity, batch);
                }
            }
        }
    }

    private void firepowerText(Entity entity, SpriteBatch batch) {
        font.getData().scaleX = 0.5f;
        font.getData().scaleY = 0.5f;
        CannonPart cannonPart = entity.getPart(CannonPart.class);
        int firepower = Math.round(cannonPart.getPreviousFirepower());
        batch.begin();
        String fire = "Firepower: " + firepower;
        textBounds = font.getBounds(fire);
        font.draw(batch, fire, cannonPart.getJointX() - textBounds.width / 2,
                cannonPart.getJointY() - 30);
        batch.end();

    }

//    @Override
//    public void setCam(OrthographicCamera camera) {
//        this.camera = camera;
//    }

    private void angleText(Entity entity, SpriteBatch batch) {
        font.getData().scaleX = 0.5f;
        font.getData().scaleY = 0.5f;
        CannonPart cannonPart = entity.getPart(CannonPart.class);
        double angle = cannonPart.getPreviousAngle() * 180 / 3.1415f;
        angle = Math.ceil(angle);
        batch.begin();
        String angleString = "Angle: " + angle;
        textBounds = font.getBounds(angleString);
        font.draw(batch, angleString, cannonPart.getJointX() - textBounds.width / 2,
                cannonPart.getJointY() - 20);
        batch.end();

    }


    private void turnText(World world, GameData gameData, SpriteBatch batch) {
        font.getData().scaleX = 0.5f;
        font.getData().scaleY = 0.5f;
        String turnText = "Turn: ";
        String timeText = "Remaining time of turn: ";

        for (Entity e : world.getEntities()
                ) {
            TurnPart turn = e.getPart(TurnPart.class);

            if (turn != null && turn.isMyTurn()) {
                turnText += turn.getMyTurnNumber();
                timeText += Math.floor(turn.getTurnTimeRemaining() * 10) / 10f;
            }

        }

        batch.begin();
        font.draw(batch, turnText, 10, gameData.getGameHeight() - 10);
        font.draw(batch, timeText, 10, gameData.getGameHeight() - 20);

        batch.end();
    }
}
