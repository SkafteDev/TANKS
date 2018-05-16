package dk.grp1.tanks.core.internal.GUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;

public interface IGuiProcessingService {

    /**
     * Draws data on the screen
     * @param world current game world and entities
     * @param gameData
     * @param spriteBatch spriteBatch with camera set
     */
    void draw(World world, GameData gameData, SpriteBatch spriteBatch);

    /**
     * Dispose any active textures, fonts, etc.
     */
    void dispose();
}
