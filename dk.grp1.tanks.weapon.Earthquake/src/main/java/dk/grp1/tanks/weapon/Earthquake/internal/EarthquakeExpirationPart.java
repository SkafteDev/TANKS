package dk.grp1.tanks.weapon.Earthquake.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.ExpirationPart;

public class EarthquakeExpirationPart extends ExpirationPart {

    public EarthquakeExpirationPart() {
        this.setRemainingLifeTime(1f);
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {
        super.processPart(entity, gameData, world);
        if(this.getRemainingLifeTime() <= 0){
            world.removeEntity(entity);
        }
    }
}
