package dk.grp1.tanks.core.internal.GUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;

public class OnScreenText implements IGuiProcessingService{

    private SpriteBatch batch;
    private BitmapFont font;

    @Override
    public void draw(OrthographicCamera camera, World world, GameData gameData) {
        renderText(gameData);
    }



    private void renderText(GameData gameData) {
        batch = new SpriteBatch();
        String text = "Turn: ";
        font = new BitmapFont();
        batch.begin();
        font.draw(batch, text, 10, gameData.getGameHeight()*2-10);
        batch.end();
    }
}
