package dk.grp1.tanks.core.internal.GUI;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;

public interface IGUIEntityProcessingService {
     /**
      * Draws the GUI dependending on the entity
      * @param entity
      * @param gameData
      * @param batch
      */
     void drawEntity(Entity entity, GameData gameData, SpriteBatch batch);
     /**
      * Dispose should be called when the class should never be used again
      */
     void dispose();
}
