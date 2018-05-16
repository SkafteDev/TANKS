package dk.grp1.tanks.core.internal.GUI;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;

public interface IGUIEntityProcessingService {

     /**
      * Used for drawing the name of the winner on the screen
      * @param entity
      * @param gameData
      * @param batch
      */
     void drawEntity(Entity entity, GameData gameData, SpriteBatch batch);

     /**
      * Dispose any textures, fonts, etc.
      */
     void dispose();
}
