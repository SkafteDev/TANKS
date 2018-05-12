package dk.grp1.tanks.core.internal.GUI;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;

public class WinScreenGUI implements IGUIEntityProcessingService  {

    private BitmapFont font = new BitmapFont();
    private BitmapFont.TextBounds textBounds;


    @Override
    public void drawEntity(Entity entity,GameData gameData, SpriteBatch batch) {
        font.getData().scaleX = 2f;
        font.getData().scaleY = 2f;

        batch.begin();
        String winner;
        if(entity != null) {
            winner = "Winner: " + entity.toString();
        } else {
            winner = "No one won";
        }
        textBounds = font.getBounds(winner);
        font.draw(batch, winner, gameData.getGameWidth()/2 - textBounds.width / 2,
                gameData.getGameHeight()/2-textBounds.height/2);
        batch.end();

    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
