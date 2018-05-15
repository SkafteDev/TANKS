package dk.grp1.tanks.core.internal.GUI;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;

public interface IGUIEntityProcessingService {

     void drawEntity(Entity entity, GameData gameData, SpriteBatch batch);
     void dispose();
}
